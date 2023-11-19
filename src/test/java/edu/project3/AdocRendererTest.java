package edu.project3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class AdocRendererTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

    @Test
    @DisplayName("Everything provided")
    void everythingProvided() {
        LogReport report = new LogReport(
                OffsetDateTime.parse("18/Oct/2013:10:20:12 +0000", FORMATTER),
                OffsetDateTime.parse("16/Jan/2019:10:20:12 +0000", FORMATTER),
                "nginx_logs", 13, 1234,
                Map.of(
                        "/index", 20,
                        "/users", 123,
                        "/messages", 1
                ),
                Map.of(
                        300, 12,
                        200, 120,
                        500, 3
                ),
                Map.of(
                        LogRecord.Method.GET, 10,
                        LogRecord.Method.POST, 20,
                        LogRecord.Method.HEAD, 100
                ),
                Map.of(
                        ZoneOffset.of("Z"), 100,
                        ZoneOffset.of("+0300"), 3
                )
        );
        LogReportRenderer renderer = new AdocRenderer();

        String render = renderer.render(report);

        assertThat(render).isEqualTo("""
                == General Info
                  
                [cols="^,^", options="header"]
                |===
                |Metric|Value
                |Sources|`nginx_logs`
                |From Date|18.10.2013
                |To Date|16.01.2019
                |Total Requests|13
                |Average Response Size|1234 bytes
                |===
                                
                == Resources Requested
                  
                [cols="^,^", options="header"]
                |===
                |Resource|Times Requested
                |`/users`|123
                |`/index`|20
                |`/messages`|1
                |===
                                
                == Status Codes
                  
                [cols="^,^,^", options="header"]
                |===
                |Status Code|Name|Times Returned
                |200|OK|120
                |300|-|12
                |500|Internal Server Error|3
                |===
                
                == Method Stats
                
                [cols="^,^", options="header"]
                |===
                |Method|Times Requested
                |HEAD|100
                |POST|20
                |GET|10
                |===
                
                == User Zone Offset Stats
                
                [cols="^,^", options="header"]
                |===
                |Offset|No. of Requests From That Offset
                |Z|100
                |+03:00|3
                |===
                """);
    }

    @Test
    @DisplayName("No dates")
    void noDates() {
        LogReport report = new LogReport(
                null, null, "nginx_logs", 13, 1234,
                Map.of(
                        "/index", 20,
                        "/users", 123,
                        "/messages", 1
                ),
                Map.of(
                        300, 12,
                        200, 120,
                        500, 3
                ),
                Map.of(
                        LogRecord.Method.GET, 10,
                        LogRecord.Method.POST, 20,
                        LogRecord.Method.HEAD, 100
                ),
                Map.of(
                        ZoneOffset.of("Z"), 100,
                        ZoneOffset.of("+0300"), 3
                )
        );
        LogReportRenderer renderer = new AdocRenderer();

        String render = renderer.render(report);

        assertThat(render).isEqualTo("""
                == General Info
                  
                [cols="^,^", options="header"]
                |===
                |Metric|Value
                |Sources|`nginx_logs`
                |From Date|-
                |To Date|-
                |Total Requests|13
                |Average Response Size|1234 bytes
                |===
                                
                == Resources Requested
                  
                [cols="^,^", options="header"]
                |===
                |Resource|Times Requested
                |`/users`|123
                |`/index`|20
                |`/messages`|1
                |===
                                
                == Status Codes
                  
                [cols="^,^,^", options="header"]
                |===
                |Status Code|Name|Times Returned
                |200|OK|120
                |300|-|12
                |500|Internal Server Error|3
                |===
                                
                == Method Stats
                
                [cols="^,^", options="header"]
                |===
                |Method|Times Requested
                |HEAD|100
                |POST|20
                |GET|10
                |===
                
                == User Zone Offset Stats
                
                [cols="^,^", options="header"]
                |===
                |Offset|No. of Requests From That Offset
                |Z|100
                |+03:00|3
                |===
                """);
    }
}
