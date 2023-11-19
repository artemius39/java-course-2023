package edu.project3;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class BaseRenderer implements LogReportRenderer {
    private static final Map<Integer, String> STATUS_CODE_NAMES = Map.of(
            200, "OK",
            301, "Moved Permanently",
            302, "Found",
            400, "Bad Request",
            401, "Unauthorized",
            403, "Forbidden",
            404, "Not Found",
            500, "Internal Server Error",
            502, "Bad Gateway",
            503, "Service Unavailable"
    );
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int COLUMNS_IN_STATUS_CODES = 3;
    private static final String TIMES_REQUESTED = "Times Requested";

    @Override
    public String render(LogReport report) {
        StringBuilder renderResult = new StringBuilder();

        renderGeneralInfo(report, renderResult);
        renderResult.append('\n');
        renderResourceStats(report, renderResult);
        renderResult.append('\n');
        renderStatusCodeStats(report, renderResult);
        renderResult.append('\n');
        renderMethodStats(report, renderResult);
        renderResult.append('\n');
        renderZoneOffsetStats(report, renderResult);

        return renderResult.toString();
    }

    private void renderZoneOffsetStats(LogReport report, StringBuilder renderResult) {
        TableBuilder builder = tableBuilder(renderResult, 2,
                "User Zone Offset Stats",
                "Offset", "No. of Requests From That Offset");
        report.zoneOffsetFrequencies()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<ZoneOffset, Integer>comparingByValue().reversed())
                .forEach(entry -> builder.addRow(entry.getKey(), entry.getValue()));
        builder.build();
    }

    private void renderMethodStats(LogReport report, StringBuilder renderResult) {
        TableBuilder builder = tableBuilder(renderResult, 2,
                "Method Stats",
                "Method", TIMES_REQUESTED);
        report.methodFrequencies()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<LogRecord.Method, Integer>comparingByValue().reversed())
                .forEach(entry -> builder.addRow(entry.getKey(), entry.getValue()));
        builder.build();
    }

    private void renderGeneralInfo(LogReport report, StringBuilder renderResult) {
        tableBuilder(renderResult, 2,
                "General Info",
                "Metric", "Value")
                .addRow("Sources", code(report.sources()))
                .addRow("From Date", renderDate(report.from()))
                .addRow("To Date", renderDate(report.to()))
                .addRow("Total Requests", report.totalRequests())
                .addRow("Average Response Size", report.averageResponseSize() + " bytes")
                .build();
    }

    private String renderDate(OffsetDateTime date) {
        return date == null ? "-" : DATE_FORMATTER.format(date);
    }

    private void renderResourceStats(LogReport report, StringBuilder renderResult) {
        TableBuilder resourcesRequested = tableBuilder(
                renderResult, 2,
                "Resources Requested",
                "Resource", TIMES_REQUESTED
        );
        report.requestedResources()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> resourcesRequested.addRow(code(entry.getKey()), entry.getValue()));
        resourcesRequested.build();
    }

    private void renderStatusCodeStats(LogReport report, StringBuilder renderResult) {
        TableBuilder statusCodes = tableBuilder(
                renderResult, COLUMNS_IN_STATUS_CODES,
                "Status Codes",
                "Status Code", "Name", "Times Returned"
        );
        report.statusCodes()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .forEach(entry -> statusCodes.addRow(
                        entry.getKey(),
                        STATUS_CODE_NAMES.getOrDefault(entry.getKey(), "-"),
                        entry.getValue()
                ));
        statusCodes.build();
    }

    protected abstract TableBuilder tableBuilder(StringBuilder sb, int columnCount,
                                                 String caption,
                                                 String... columnNames);

    protected abstract String code(String string);

    protected interface TableBuilder {
        void build();

        TableBuilder addRow(Object... cells);
    }
}
