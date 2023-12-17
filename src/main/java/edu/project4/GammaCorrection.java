package edu.project4;

import edu.project4.domain.FractalImage;
import edu.project4.domain.Pixel;
import java.awt.Color;

public class GammaCorrection implements ImageProcessor {
    private final double gamma;

    public GammaCorrection(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        Pixel[][] pixels = image.pixels();

        int maxHitCount = 0;
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                maxHitCount = Math.max(maxHitCount, pixels[x][y].hitCount());
            }
        }

        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel pixel = pixels[x][y];
                double scaleFactor = Math.pow(Math.log(pixel.hitCount()) / Math.log(maxHitCount), 1 / gamma);

                Color color = pixel.color();
                Color scaledColor = new Color(
                        (int) (color.getRed() * scaleFactor),
                        (int) (color.getGreen() * scaleFactor),
                        (int) (color.getBlue() * scaleFactor)
                );

                pixels[x][y] = new Pixel(scaledColor, pixel.hitCount());
            }
        }
    }
}
