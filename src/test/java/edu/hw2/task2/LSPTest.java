package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class LSPTest {
    static Arguments[] rectangleAndSquare() {
        return new Arguments[]{
                Arguments.of(new Rectangle(42, 42)),
                Arguments.of(new Square(42))
        };
    }

    @ParameterizedTest
    @MethodSource("rectangleAndSquare")
    @DisplayName("Width")
    void getWidth(Rectangle rectangle) {
        int width = rectangle.getWidth();

        assertThat(width).isEqualTo(42);
    }

    @ParameterizedTest
    @MethodSource("rectangleAndSquare")
    @DisplayName("Height")
    void getHeight(Rectangle rectangle) {
        int height = rectangle.getHeight();

        assertThat(height).isEqualTo(42);
    }

    @ParameterizedTest
    @MethodSource("rectangleAndSquare")
    @DisplayName("Area")
    void area(Rectangle rectangle) {
        long area = rectangle.area();

        assertThat(area).isEqualTo(42 * 42);
    }

    @ParameterizedTest
    @MethodSource("rectangleAndSquare")
    @DisplayName("Changing width")
    void setWidth(Rectangle rectangle) {
        Rectangle rect = rectangle.setWidth(18);

        int width = rect.getWidth();
        int height = rect.getHeight();
        long area = rect.area();

        assertThat(width).isEqualTo(18);
        assertThat(height).isEqualTo(42);
        assertThat(area).isEqualTo(18 * 42);
    }

    @ParameterizedTest
    @MethodSource("rectangleAndSquare")
    @DisplayName("Changing height")
    void setHeight(Rectangle rectangle) {
        Rectangle rect = rectangle.setHeight(18);

        int width = rect.getWidth();
        int height = rect.getHeight();
        long area = rect.area();

        assertThat(width).isEqualTo(42);
        assertThat(height).isEqualTo(18);
        assertThat(area).isEqualTo(42 * 18);
    }
}
