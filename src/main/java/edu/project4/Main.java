package edu.project4;

import edu.project4.domain.FractalImage;
import java.nio.file.Path;
import java.util.List;

@SuppressWarnings({"checkstyle:MagicNumber"})
public final class Main {
    private static final List<ImageProcessor> IMAGE_PROCESSORS = List.of(new GammaCorrection(2.2));

    public static void main(String[] args) {
        Renderer renderer = new MultiThreadedRenderer();
        FractalImage image = renderer.render(
                List.of(Transformation.SWIRL),
                30, 1500, 12,
                1000, 1000
        );
        for (ImageProcessor imageProcessor : IMAGE_PROCESSORS) {
            imageProcessor.process(image);
        }
        boolean result = ImageUtils.save(image, Path.of("output.png"), ImageUtils.ImageFormat.PNG);
        if (!result) {
            System.err.println("Failed to write image: no writer found for selected format");
        }
    }

    private Main() {
    }
}
