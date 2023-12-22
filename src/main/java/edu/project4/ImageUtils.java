package edu.project4;

import edu.project4.domain.FractalImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    public enum ImageFormat {
        JPG, BMP, PNG
    }

    public static boolean save(FractalImage fractalImage, Path fileName, ImageFormat format) {
        int width = fractalImage.width();
        int height = fractalImage.height();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, fractalImage.pixels()[x][y].color().getRGB());
            }
        }

        try {
            return ImageIO.write(image, format.name(), fileName.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write image", e);
        }
    }

    private ImageUtils() {
    }
}
