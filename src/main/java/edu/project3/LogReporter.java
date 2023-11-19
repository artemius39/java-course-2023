package edu.project3;

import edu.project3.metrics.AverageResponseSize;
import edu.project3.metrics.Counter;
import edu.project3.metrics.MethodCounter;
import edu.project3.metrics.ResourcesCounter;
import edu.project3.metrics.StatusCodeCounter;
import edu.project3.metrics.TimeOffsetCounter;
import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.stream.Stream;

public class LogReporter {
    public LogReport makeReport(Stream<LogRecord> logs, OffsetDateTime from, OffsetDateTime to, String sources) {
        Stream<LogRecord> logsFilteredByDate = logs;
        if (from != null) {
            logsFilteredByDate = logsFilteredByDate.filter(log -> log.localTime().isAfter(from));
        }
        if (to != null) {
            logsFilteredByDate = logsFilteredByDate.filter(log -> log.localTime().isBefore(to));
        }

        Counter counter = new Counter();
        AverageResponseSize averageResponseSize = new AverageResponseSize();
        ResourcesCounter resourcesCounter = new ResourcesCounter();
        StatusCodeCounter statusCodeCounter = new StatusCodeCounter();
        MethodCounter methodCounter = new MethodCounter();
        TimeOffsetCounter timeOffsetCounter = new TimeOffsetCounter();

        Iterator<LogRecord> iterator = logsFilteredByDate.iterator();
        while (iterator.hasNext()) {
            LogRecord log = iterator.next();

            counter.count(log);
            averageResponseSize.count(log);
            resourcesCounter.count(log);
            statusCodeCounter.count(log);
            methodCounter.count(log);
            timeOffsetCounter.count(log);
        }

        return new LogReport(
                from, to, sources,
                counter.get(),
                averageResponseSize.get(),
                resourcesCounter.get(),
                statusCodeCounter.get(),
                methodCounter.get(),
                timeOffsetCounter.get()
        );
    }

}
