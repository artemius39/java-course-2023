package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SquareTest {
    @Test
    @DisplayName("Width")
    void width() {
        Square square = new Square(5);

        int width = square.getWidth();

        assertThat(width).isEqualTo(5);
    }

    @Test
    @DisplayName("Height")
    void height() {
        Square square = new Square(5);

        int height = square.getHeight();

        assertThat(height).isEqualTo(5);
    }

    @Test
    @DisplayName("Area")
    void area() {
        Square square = new Square(5);

        long area = square.area();

        assertThat(area).isEqualTo(5 * 5);
    }

    @Test
    @DisplayName("Long area")
    void longArea() {
        Square square = new Square(Integer.MAX_VALUE);

        long area = square.area();

        assertThat(area).isEqualTo((long) Integer.MAX_VALUE * Integer.MAX_VALUE);
    }

    @ParameterizedTest
    @ValueSource(ints = {-42, 0})
    @DisplayName("Invalid dimensions")
    void invalidDimensions(int invalid) {
        assertThrows(IllegalArgumentException.class, () -> new Square(invalid));
    }
}
