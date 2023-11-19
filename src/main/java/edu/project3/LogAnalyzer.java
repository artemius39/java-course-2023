package edu.project3;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class LogAnalyzer {
    private static final LogReportRenderer DEFAULT_RENDERER = new MarkdownRenderer();
    private static final Map<String, LogReportRenderer> RENDERERS = Map.of(
            "markdown",  new MarkdownRenderer(),
            "adoc", new AdocRenderer()
    );

    private static final List<LogReader> SOURCE_PARSERS = List.of(
            new URLLogReader(),
            new PathLogReader()
    );

    private final CLIParser cliParser;
    private final LogParser logParser;
    private final LogReporter logReporter;

    public LogAnalyzer() {
        cliParser = new ApacheCommonsCLIParser();
        logParser = new NginxLogParser();
        logReporter = new LogReporter();
    }

    public void analyze(String[] args) {
        CLIArguments arguments = cliParser.parse(args);

        LogReportRenderer renderer = parseRenderer(arguments.format());
        OffsetDateTime from = parseFromDate(arguments.from());
        OffsetDateTime to = parseToDate(arguments.to());

        Stream<LogRecord> parsedLogs = readLogs(arguments.path()).map(logParser::parseLog);
        LogReport report = logReporter.makeReport(parsedLogs, from, to, arguments.path());

        String render = renderer.render(report);
        writeToFile(arguments.outputFileName(), render);
    }

    private Stream<String> readLogs(String path) {
        for (LogReader logReader : SOURCE_PARSERS) {
            Optional<Stream<String>> logs = logReader.readLogs(path);
            if (logs.isPresent()) {
                return logs.get();
            }
        }
        throw new IllegalCLIArgumentException("Could not find logs at " + path);
    }

    private LogReportRenderer parseRenderer(String format) {
        if (format == null) {
            return DEFAULT_RENDERER;
        }
        LogReportRenderer renderer = RENDERERS.get(format);
        if (renderer == null) {
            throw new IllegalCLIArgumentException("Unsupported output format: '" + format + "'. "
                                                  + "Supported formats are "
                                                  + String.join(", ", RENDERERS.keySet()));
        }
        return renderer;
    }

    private void writeToFile(String outputFileName, String render) {
        try {
            Path outputFile = Path.of(outputFileName);
            try {
                Files.createFile(outputFile);
            } catch (FileAlreadyExistsException ignored) {
                // If file already exists, it gets overwritten
            }
            Files.writeString(outputFile, render);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private OffsetDateTime parseFromDate(String from) {
        if (from == null) {
            return null;
        }
        ZoneOffset defaultOffset = OffsetDateTime.now().getOffset();
        try {
            return LocalDate.parse(from).atStartOfDay().atOffset(defaultOffset);
        } catch (DateTimeParseException e) {
            throw new IllegalCLIArgumentException("Invalid from date: '" + from + "'");
        }
    }

    private OffsetDateTime parseToDate(String to) {
        if (to == null) {
            return null;
        }
        ZoneOffset defaultOffset = OffsetDateTime.now().getOffset();
        LocalTime endOfDay = LocalTime.MAX;
        try {
            return LocalDate.parse(to).atTime(endOfDay).atOffset(defaultOffset);
        } catch (DateTimeParseException e) {
            throw new IllegalCLIArgumentException("Invalid to date: '" + to + "'");
        }
    }
}
