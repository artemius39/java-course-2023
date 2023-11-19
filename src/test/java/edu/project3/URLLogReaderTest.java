package edu.project3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class URLLogReaderTest {
    @Test
    @DisplayName("Valid url")
    void validUrl() {
        LogReader reader = new URLLogReader();
        String source =
                "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

        Optional<Stream<String>> logs = reader.readLogs(source);

        assertThat(logs).isPresent();
        assertThat(logs.get().limit(5).toList()).containsExactlyInAnyOrder(
                "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
                "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
                "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
                "217.168.17.5 - - [17/May/2015:08:05:34 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
                "217.168.17.5 - - [17/May/2015:08:05:09 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\""
        );
    }

    @ParameterizedTest
    @DisplayName("Invalid url")
    @ValueSource(strings = {"", "src/test/resourses/project3/file1"})
    void invalidUrl(String url) {
        LogReader reader = new URLLogReader();

        Optional<Stream<String>> logs = reader.readLogs(url);

        assertThat(logs).isEmpty();
    }
}
