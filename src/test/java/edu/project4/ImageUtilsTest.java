package edu.project4;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import edu.project4.domain.FractalImage;
import edu.project4.domain.Pixel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

class ImageUtilsTest {
    private FractalImage readImage(Path imagePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(imagePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Could not read test image from disk", e);
        }

        int width = image.getWidth();
        int height = image.getHeight();
        Pixel[][] pixels = new Pixel[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new Pixel(new Color(image.getRGB(x, y)), 0);
            }
        }

        return new FractalImage(pixels, width, height);
    }

    private void testFormat(ImageUtils.ImageFormat imageFormat, String formatName, Path tempDir) throws IOException {
        Path imagePath = Path.of("src", "test", "resources", "project4", "output." + formatName);
        byte[] expected = Files.readAllBytes(imagePath);
        FractalImage image = readImage(imagePath);
        Path savedImage = tempDir.resolve("saved." + formatName);

        boolean success = ImageUtils.save(image, savedImage, imageFormat);
        if (!success) {
            return;
        }
        byte[] actual = Files.readAllBytes(savedImage);

        assertThat(Arrays.equals(expected, actual)).isTrue();
    }

    @Test
    @DisplayName("PNG")
    void png(@TempDir Path tempDir) throws IOException {
        testFormat(ImageUtils.ImageFormat.PNG, "png", tempDir);
    }

    @Test
    @DisplayName("BMP")
    void bmp(@TempDir Path tempDir) throws IOException {
        testFormat(ImageUtils.ImageFormat.BMP, "bmp", tempDir);
    }
    @Test
    @DisplayName("JPEG")
    void jpeg(@TempDir Path tempDir) throws IOException {
        testFormat(ImageUtils.ImageFormat.JPG, "jpg", tempDir);
    }
}
