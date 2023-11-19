package edu.project3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApacheCommonsCLIParserTest {
    @Test
    @DisplayName("All arguments provided")
    void allArgumentsProvided() {
        CLIParser parser = new ApacheCommonsCLIParser();
        String[] args = {
                "--path", "nginx_logs",
                "--from", "2015-01-23",
                "--to", "2023-11-19",
                "--format", "markdown",
                "--output-file", "report.md"
        };

        CLIArguments arguments = parser.parse(args);

        assertThat(arguments.path()).isEqualTo("nginx_logs");
        assertThat(arguments.from()).isEqualTo("2015-01-23");
        assertThat(arguments.to()).isEqualTo("2023-11-19");
        assertThat(arguments.format()).isEqualTo("markdown");
        assertThat(arguments.outputFileName()).isEqualTo("report.md");
    }

    static Stream<Arguments> missingPath() {
        return Stream.of(
                Arguments.of((Object) new String[] {
                        "--from", "2015-01-23",
                        "--to", "2023-11-19",
                        "--format", "markdown",
                        "--output-file", "report.md"
                }),
                Arguments.of((Object) new String[] {
                        "--path",
                        "--from", "2015-01-23",
                        "--to", "2023-11-19",
                        "--format", "markdown",
                        "--output-file", "report.md"
                })
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Missing path")
    void missingPath(String[] args) {
        CLIParser parser = new ApacheCommonsCLIParser();

        assertThrows(IllegalCLIArgumentException.class, () -> parser.parse(args));
    }

    @Test
    @DisplayName("Missing --to")
    void missingTo() {
        CLIParser parser = new ApacheCommonsCLIParser();
        String[] args = {
                "--path", "nginx_logs",
                "--to", "2023-11-19",
                "--format", "markdown",
                "--output-file", "report.md"
        };

        CLIArguments arguments = parser.parse(args);

        assertThat(arguments.from()).isNull();
    }

    @Test
    @DisplayName("Missing argument for --to")
    void missingArgumentForTo() {
        CLIParser parser = new ApacheCommonsCLIParser();
        String[] args = {
                "--path", "nginx_logs",
                "--to",
                "--format", "markdown",
                "--output-file", "report.md"
        };

        assertThrows(IllegalCLIArgumentException.class, () -> parser.parse(args));
    }

    @Test
    @DisplayName("Missing --from")
    void missingFrom() {
        CLIParser parser = new ApacheCommonsCLIParser();
        String[] args = {
                "--path", "nginx_logs",
                "--to", "2023-11-19",
                "--format", "markdown",
                "--output-file", "report.md"
        };

        CLIArguments arguments = parser.parse(args);

        assertThat(arguments.from()).isNull();
    }

    @Test
    @DisplayName("Missing argument for --from")
    void missingArgumentForFrom() {
        CLIParser parser = new ApacheCommonsCLIParser();
        String[] args = {
                "--path", "nginx_logs",
                "--from",
                "--to", "2023-11-19",
                "--format", "markdown",
                "--output-file", "report.md"
        };

        assertThrows(IllegalCLIArgumentException.class, () -> parser.parse(args));
    }

    @Test
    @DisplayName("Missing --format")
    void missingFormat() {
        CLIParser parser = new ApacheCommonsCLIParser();
        String[] args = {
                "--path", "nginx_logs",
                "--from", "2023-11-18",
                "--to", "2023-11-19",
                "--output-file", "report.md"
        };

        CLIArguments arguments = parser.parse(args);

        assertThat(arguments.format()).isNull();
    }

    @Test
    @DisplayName("Missing argument for --format")
    void missingArgumentForFormat() {
        CLIParser parser = new ApacheCommonsCLIParser();
        String[] args = {
                "--path", "nginx_logs",
                "--from", "2023-11-10",
                "--to", "2023-11-19",
                "--format",
                "--output-file", "report.md"
        };

        assertThrows(IllegalCLIArgumentException.class, () -> parser.parse(args));
    }

    static Stream<Arguments> missingOutputFile() {
        return Stream.of(
                Arguments.of((Object) new String[] {
                        "--path", "nginx_logs",
                        "--from", "2015-01-23",
                        "--to", "2023-11-19",
                        "--format", "markdown",
                }),
                Arguments.of((Object) new String[] {
                        "--path", "nginx_logs",
                        "--from", "2015-01-23",
                        "--to", "2023-11-19",
                        "--format", "markdown",
                        "--output-file"
                })
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Missing output file")
    void missingOutputFile(String[] args) {
        CLIParser parser = new ApacheCommonsCLIParser();

        assertThrows(IllegalCLIArgumentException.class, () -> parser.parse(args));
    }
}
