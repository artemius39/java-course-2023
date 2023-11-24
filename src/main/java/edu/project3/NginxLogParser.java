package edu.project3;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NginxLogParser implements LogParser {
    private static final Pattern NGINX_LOG_PATTERN = Pattern.compile(
            "(\\S+) - (\\S+) \\[([^]]+)] \"([A-Z]+) (\\S+)"
            + " HTTP/\\d+(\\.\\d+)*\" (\\d+) (\\d+) \"([^\"]+)\" \"([^\"]+)\"");
    private static final DateTimeFormatter NGINX_LOG_DATE_PATTERN =
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");
    private static final int REMOTE_ADRESS_POSITION = 1;
    private static final int REMOTE_USER_POSITION = 2;
    private static final int LOCAL_TIME_POSITION = 3;
    private static final int METHOD_POSITION = 4;
    private static final int RESOURCE_URI_POSITION = 5;
    private static final int STATUS_CODE_POSITION = 7;
    private static final int BODY_BYTES_POSITION = 8;
    private static final int HTTP_REFERER_POSITION = 9;
    private static final int HTTP_USER_AGENT_POSITION = 10;

    @Override
    public LogRecord parseLog(String log) {
        Objects.requireNonNull(log, "log cannot be null");
        Matcher matcher = NGINX_LOG_PATTERN.matcher(log);
        if (!matcher.find()) {
            throw new InvalidLogFormatException("Log does not match nginx log pattern");
        }

        return new LogRecord(
                matcher.group(REMOTE_ADRESS_POSITION),
                matcher.group(REMOTE_USER_POSITION),
                parseTimeLocal(matcher.group(LOCAL_TIME_POSITION)),
                parseMethod(matcher.group(METHOD_POSITION)),
                parseResourceURI(matcher.group(RESOURCE_URI_POSITION)),
                parseStatusCode(matcher.group(STATUS_CODE_POSITION)),
                parseBodyBytesSent(matcher.group(BODY_BYTES_POSITION)),
                matcher.group(HTTP_REFERER_POSITION),
                matcher.group(HTTP_USER_AGENT_POSITION)
        );
    }

    private OffsetDateTime parseTimeLocal(String time) {
        try {
            return OffsetDateTime.parse(time, NGINX_LOG_DATE_PATTERN);
        } catch (DateTimeParseException e) {
            throw new InvalidLogFormatException("Invalid time format: " + time, e);
        }
    }

    private LogRecord.Method parseMethod(String method) {
        try {
            return LogRecord.Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            throw new InvalidLogFormatException("Invalid HTTP method: " + method, e);
        }
    }

    private String parseResourceURI(String resourceURI) {
        try {
            return URLDecoder.decode(resourceURI, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new InvalidLogFormatException("Illegal resource URI: " + resourceURI, e);
        }
    }

    private int parseStatusCode(String statusCode) {
        try {
            return Integer.parseInt(statusCode);
        } catch (NumberFormatException e) {
            throw new InvalidLogFormatException("Invalid status code: " + statusCode, e);
        }
    }

    private long parseBodyBytesSent(String bodyBytesSentString) {
        try {
            return Long.parseLong(bodyBytesSentString);
        } catch (NumberFormatException e) {
            throw new InvalidLogFormatException("Invalid number of body bytes sent: " + bodyBytesSentString, e);
        }
    }
}
