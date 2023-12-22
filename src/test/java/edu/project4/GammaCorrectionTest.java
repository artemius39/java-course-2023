package edu.project4;

import java.awt.Color;
import edu.project4.domain.FractalImage;
import edu.project4.domain.Pixel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GammaCorrectionTest {
    @Test
    void gammaCorrectionCorrectlyScalesColor() {
        ImageProcessor gammaCorrection = new GammaCorrection(2.2);
        FractalImage image = new FractalImage(
                new Pixel[][] {
                        new Pixel[] {
                                new Pixel(new Color(100, 100, 100), 100),
                                new Pixel(new Color(100, 50, 200), 10)
                        }
                },
                1, 2
        );

        gammaCorrection.process(image);
        double scaleFactor = Math.pow(Math.log(10) / Math.log(100), 1 / 2.2);
        Pixel pixel = image.pixels()[0][1];

        assertThat(pixel.color().getRed()).isEqualTo((int) (100 * scaleFactor));
        assertThat(pixel.color().getGreen()).isEqualTo((int) (50 * scaleFactor));
        assertThat(pixel.color().getBlue()).isEqualTo((int) (200 * scaleFactor));
    }
}
