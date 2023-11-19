package edu.project3;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

public record LogReport(
        OffsetDateTime from,
        OffsetDateTime to,
        String sources,
        long totalRequests,
        long averageResponseSize,
        Map<String, Integer> requestedResources,
        Map<Integer, Integer> statusCodes,
        Map<LogRecord.Method, Integer> methodFrequencies,
        Map<ZoneOffset, Integer> zoneOffsetFrequencies
) {
}
