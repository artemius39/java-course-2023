package edu.project3;

import java.time.OffsetDateTime;

public record LogRecord(
        String remoteAddress,
        String remoteUser,
        OffsetDateTime localTime,
        Method method,
        String resourceURI,
        int statusCode,
        long bodyBytesSent,
        String httpReferer,
        String httpUserAgent
) {
    public enum Method {
        GET, POST, HEAD
    }
}
