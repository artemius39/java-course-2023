package edu.project3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogAnalyzerTest {
    @Test
    @DisplayName("Success")
    void success() throws IOException {
        LogAnalyzer analyzer = new LogAnalyzer();
        Path output = Path.of("out.md");
        String source = Path.of("src", "test", "resources", "project3", "nginx_logs").toString();
        String[] args = new String[] {
                "--path", source,
                "--output-file", output.toString()
        };

        analyzer.analyze(args);

        assertThat(output).exists()
                .content(StandardCharsets.UTF_8)
                .isEqualTo("""
                        #### General Info
                                                
                        |Metric|Value|
                        |:-:|:-:|
                        |Sources|`%s`|
                        |From Date|-|
                        |To Date|-|
                        |Total Requests|5|
                        |Average Response Size|196 bytes|
                                                
                        #### Resources Requested

                        |Resource|Times Requested|
                        |:-:|:-:|
                        |`/downloads/product_1`|4|
                        |`/downloads/product_2`|1|

                        #### Status Codes

                        |Status Code|Name|Times Returned|
                        |:-:|:-:|:-:|
                        |304|-|3|
                        |200|OK|2|
                        
                        #### Method Stats
                        
                        |Method|Times Requested|
                        |:-:|:-:|
                        |GET|3|
                        |HEAD|2|
                        
                        #### User Zone Offset Stats
                        
                        |Offset|No. of Requests From That Offset|
                        |:-:|:-:|
                        |Z|3|
                        |+08:00|2|
                        """.formatted(source));
        Files.delete(output);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://adadadasdasdawacascsaqwadaccdscaeda.awdwdawdwdaw",
            "doesnt-exist",
            "https://youtu.be/PkT0PJwy8mI?si=JjAdcJATl20uDzr-",
            ".", ""
    })
    @DisplayName("Invalid source")
    void invalidSource(String source) {
        LogAnalyzer analyzer = new LogAnalyzer();
        String[] args = new String[] {
                "--path", source,
                "--output-file", "file"
        };

        assertThrows(IllegalCLIArgumentException.class, () -> analyzer.analyze(args));
    }

    @ParameterizedTest
    @DisplayName("Invalid date")
    @ValueSource(strings = {"2022", "2023-11", "2011.11.11", "yesterday"})
    void invalidDate(String date) {
        LogAnalyzer analyzer = new LogAnalyzer();
        String[] args = new String[] {
                "--path", "nginx_logs",
                "--from", date,
                "--output-file", "file"
        };

        assertThrows(IllegalCLIArgumentException.class, () -> analyzer.analyze(args));
    }

    @ParameterizedTest
    @DisplayName("Invalid output format")
    @ValueSource(strings = {"md", "asciidoc", "tex", ""})
    void invalidOutputFormat(String format) {
        LogAnalyzer analyzer = new LogAnalyzer();
        String[] args = new String[] {
                "--path", "nginx_logs",
                "--format", format,
                "--output-file", "file"
        };

        assertThrows(IllegalCLIArgumentException.class, () -> analyzer.analyze(args));
    }
}
