package edu.hw6.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import static edu.hw6.task3.AbstractFilter.DIRECTORY;
import static edu.hw6.task3.AbstractFilter.EXECUTABLE;
import static edu.hw6.task3.AbstractFilter.READABLE;
import static edu.hw6.task3.AbstractFilter.REGULAR;
import static edu.hw6.task3.AbstractFilter.WRITABLE;
import static org.assertj.core.api.Assertions.assertThat;

class AbstractFilterTest {
    private static Path getResource(String name) {
        return Path.of("src", "test", "resources", name);
    }

    @Test
    @DisplayName("Readable")
    void readable() throws IOException {
        Path readableFile = getResource("readable");

        assertThat(READABLE.accept(readableFile)).isTrue();
    }
    
    @Test
    @DisplayName("Writable")
    void writable() throws IOException {
        Path writableFile = getResource("writable");

        assertThat(WRITABLE.accept(writableFile)).isTrue();
    }

    @Test
    @DisplayName("Executable")
    void executable() throws IOException {
        Path executableFile = getResource("executable");
        Path nonExecutableFile = getResource("non-executable");

        assertThat(EXECUTABLE.accept(executableFile)).isTrue();
        assertThat(EXECUTABLE.accept(nonExecutableFile)).isFalse();
    }

    @Test
    @DisplayName("Regular file")
    void regularFile() throws IOException {
        Path regularFile = getResource("regular");
        Path nonRegularFile = getResource("directory");

        assertThat(REGULAR.accept(regularFile)).isTrue();
        assertThat(REGULAR.accept(nonRegularFile)).isFalse();
    }

    @Test
    @DisplayName("Directory")
    void directory() throws IOException {
        Path directory = getResource("directory");
        Path regularFile = getResource("regular");

        assertThat(DIRECTORY.accept(directory)).isTrue();
        assertThat(DIRECTORY.accept(regularFile)).isFalse();
    }
    
    @Test
    @DisplayName("Larger than")
    void largerThan() throws IOException {
        Path largerThan10Bytes = getResource("larger-than-10-bytes");
        Path exactly10Bytes = getResource("exactly-10-bytes");
        Path smallerThan10Bytes = getResource("smaller-than-10-bytes");

        AbstractFilter filter = AbstractFilter.largerThan(10);

        assertThat(filter.accept(largerThan10Bytes)).isTrue();
        assertThat(filter.accept(exactly10Bytes)).isFalse();
        assertThat(filter.accept(smallerThan10Bytes)).isFalse();
    }

    @Test
    @DisplayName("Smaller than")
    void smallerThan() throws IOException {
        Path largerThan10Bytes = getResource("larger-than-10-bytes");
        Path exactly10Bytes = getResource("exactly-10-bytes");
        Path smallerThan10Bytes = getResource("smaller-than-10-bytes");

        AbstractFilter filter = AbstractFilter.smallerThan(10);

        assertThat(filter.accept(largerThan10Bytes)).isFalse();
        assertThat(filter.accept(exactly10Bytes)).isFalse();
        assertThat(filter.accept(smallerThan10Bytes)).isTrue();
    }

    @Test
    @DisplayName("Has size")
    void hasSize() throws IOException {
        Path largerThan10Bytes = getResource("larger-than-10-bytes");
        Path exactly10Bytes = getResource("exactly-10-bytes");
        Path smallerThan10Bytes = getResource("smaller-than-10-bytes");

        AbstractFilter filter = AbstractFilter.hasSize(10);

        assertThat(filter.accept(largerThan10Bytes)).isFalse();
        assertThat(filter.accept(exactly10Bytes)).isTrue();
        assertThat(filter.accept(smallerThan10Bytes)).isFalse();
    }

    @Test
    @DisplayName("Has extension")
    void hasExtension() throws IOException {
        Path txt = getResource("test.txt");
        Path nonTxt = getResource("non-txt");

        AbstractFilter filter = AbstractFilter.hasExtension("txt");

        assertThat(filter.accept(txt)).isTrue();
        assertThat(filter.accept(nonTxt)).isFalse();
    }

    @Test
    @DisplayName("Name matches regex")
    void nameMatchesRegex() throws IOException {
        Path matches = getResource("matches");
        Path doesntMatch = getResource("doesnt-match");

        AbstractFilter filter = AbstractFilter.nameMatchesRegex("m.*es");

        assertThat(filter.accept(matches)).isTrue();
        assertThat(filter.accept(doesntMatch)).isFalse();
    }

    @Test
    @DisplayName("Name contains regex")
    void nameContainsRegex() throws IOException {
        Path contains = getResource("contains");
        Path doesntContain = getResource("doesnt-contain");

        AbstractFilter filter = AbstractFilter.nameContainsRegex("i.s");

        assertThat(filter.accept(contains)).isTrue();
        assertThat(filter.accept(doesntContain)).isFalse();
    }

    @Test
    @DisplayName("Magic number")
    void magicNumber() throws IOException {
        Path png = getResource("image.png");
        Path nonPng = getResource("non-png.png");

        AbstractFilter filter = AbstractFilter.magicNumber((byte) 0x89, (byte) 'P', (byte) 'N', (byte) 'G');

        assertThat(filter.accept(png)).isTrue();
        assertThat(filter.accept(nonPng)).isFalse();
    }
    
    @Test
    @DisplayName("Glob matches")
    void globMatches() throws IOException {
        Path png = getResource("image.png");
        Path pseudoPng = getResource("non-png.png");
        Path noMatch = getResource("png");

        AbstractFilter filter = AbstractFilter.globMatches("**/*.png");

        assertThat(filter.accept(png)).isTrue();
        assertThat(filter.accept(pseudoPng)).isTrue();
        assertThat(filter.accept(noMatch)).isFalse();
    }

    @Test
    @DisplayName("Negate")
    void negate() throws IOException {
        Path png = getResource("image.png");
        Path nonPng = getResource("executable");

        AbstractFilter filter = AbstractFilter.globMatches("**/*.png").negate();

        assertThat(filter.accept(png)).isFalse();
        assertThat(filter.accept(nonPng)).isTrue();
    }

    @Test
    @DisplayName("And")
    void and() throws IOException {
        Path tooSmall = getResource("smaller-than-10-bytes");
        Path tooLarge = getResource("larger-than-10-bytes");
        Path perfect = getResource("exactly-10-bytes");

        AbstractFilter filter = AbstractFilter.largerThan(7).and(AbstractFilter.smallerThan(12));

        assertThat(filter.accept(tooSmall)).isFalse();
        assertThat(filter.accept(tooLarge)).isFalse();
        assertThat(filter.accept(perfect)).isTrue();
    }

    @Test
    @DisplayName("Or")
    void or() throws IOException {
        Path matches = getResource("matches");
        Path png = getResource("image.png");
        Path none = getResource("doesnt-match");

        AbstractFilter filter = AbstractFilter.nameMatchesRegex("m.*es").or(AbstractFilter.hasExtension("png"));

        assertThat(filter.accept(matches)).isTrue();
        assertThat(filter.accept(png)).isTrue();
        assertThat(filter.accept(none)).isFalse();
    }
}
