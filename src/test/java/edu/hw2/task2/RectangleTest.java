package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RectangleTest {
    @Test
    @DisplayName("Width")
    void width() {
        Rectangle rectangle = new Rectangle(2, 3);

        int width = rectangle.getWidth();

        assertThat(width).isEqualTo(2);
    }

    @Test
    @DisplayName("Height")
    void height() {
        Rectangle rectangle = new Rectangle(2, 3);

        int height = rectangle.getHeight();

        assertThat(height).isEqualTo(3);
    }

    @Test
    @DisplayName("Area")
    void area() {
        Rectangle rectangle = new Rectangle(2, 3);

        long area = rectangle.area();

        assertThat(area).isEqualTo(2 * 3);
    }

    @Test
    @DisplayName("Long area")
    void longArea() {
        Rectangle rectangle = new Rectangle(Integer.MAX_VALUE, Integer.MAX_VALUE);

        long area = rectangle.area();

        assertThat(area).isEqualTo((long) Integer.MAX_VALUE * Integer.MAX_VALUE);
    }

    @ParameterizedTest
    @ValueSource(ints = {-42, 0})
    @DisplayName("Invalid dimensions")
    void invalidDimensions(int invalid) {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(invalid, 42));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(42, invalid));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(invalid, invalid));
    }
}
