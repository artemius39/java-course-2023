package edu.project3;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ApacheCommonsCLIParser implements CLIParser {
    private static final CommandLineParser PARSER = new DefaultParser();

    private static final Option PATH = Option.builder()
            .longOpt("path")
            .desc("path to logs")
            .required()
            .hasArg()
            .build();
    private static final Option FROM = Option.builder()
            .longOpt("from")
            .desc("date from which logs should be analyzed")
            .required(false)
            .hasArg()
            .build();
    private static final Option TO = Option.builder()
            .longOpt("to")
            .desc("date up to which logs should be analyzed")
            .required(false)
            .hasArg()
            .build();
    private static final Option FORMAT = Option.builder()
            .longOpt("format")
            .desc("output format")
            .required(false)
            .hasArg()
            .build();
    private static final Option OUTPUT_FILE = Option.builder()
            .longOpt("output-file")
            .desc("output file name")
            .required()
            .hasArg()
            .build();
    private static final Options OPTIONS = new Options()
            .addOption(PATH)
            .addOption(FROM)
            .addOption(TO)
            .addOption(FORMAT)
            .addOption(OUTPUT_FILE);

    @Override
    public CLIArguments parse(String[] args) {
        CommandLine parser;
        try {
            parser = PARSER.parse(OPTIONS, args);
        } catch (ParseException e) {
            throw new IllegalCLIArgumentException(e.getMessage());
        }

        return new CLIArguments(
                parser.getOptionValue(PATH),
                parser.getOptionValue(FROM),
                parser.getOptionValue(TO),
                parser.getOptionValue(FORMAT),
                parser.getOptionValue(OUTPUT_FILE)
        );
    }
}
