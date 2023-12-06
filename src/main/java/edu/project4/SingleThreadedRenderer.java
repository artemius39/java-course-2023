package edu.project4;

import edu.project4.domain.FractalImage;
import java.util.List;

public class SingleThreadedRenderer extends BaseRenderer {
    @Override
    public FractalImage render(List<Transformation> variations,
                               int samples, int iterationsPerSample,
                               int symmetry,
                               int width, int height) {
        validate(variations, samples, iterationsPerSample, symmetry, width, height);
        PixelBuilder[][] pixels = makeBuilders(width, height);
        List<AffineTransformation> transformations = generateTransformations();

        for (int sample = 0; sample < samples; sample++) {
            sample(pixels, variations, transformations, iterationsPerSample, width, height, symmetry);
        }

        return new FractalImage(buildAll(pixels, width, height), width, height);
    }
}
