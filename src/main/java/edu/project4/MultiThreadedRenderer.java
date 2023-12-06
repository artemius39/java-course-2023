package edu.project4;

import edu.project4.domain.FractalImage;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedRenderer extends BaseRenderer {
    private final int threads;

    public MultiThreadedRenderer() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public MultiThreadedRenderer(int threads) {
        this.threads = threads;
    }

    @Override
    public FractalImage render(List<Transformation> variations,
                               int samples, int iterationsPerSample,
                               int symmetry,
                               int width, int height) {
        validate(variations, samples, iterationsPerSample, symmetry, width, height);
        PixelBuilder[][] pixels = makeBuilders(width, height);
        List<AffineTransformation> transformations = generateTransformations();

        try (ExecutorService executorService = Executors.newFixedThreadPool(threads)) {
            for (int thread = 0; thread < threads; thread++) {
                int localSamples = Math.ceilDiv(samples, threads);
                executorService.submit(() -> {
                    for (int sampleNo = 0; sampleNo < localSamples; sampleNo++) {
                        sample(pixels, variations, transformations, iterationsPerSample, width, height, symmetry);
                    }
                });
            }
        }

        return new FractalImage(buildAll(pixels, width, height), width, height);
    }

    @Override
    protected PixelBuilder[][] makeBuilders(int width, int height) {
        PixelBuilder[][] pixels = new PixelBuilder[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new MultiThreadedPixelBuilder();
            }
        }
        return pixels;
    }

    private static class MultiThreadedPixelBuilder extends PixelBuilder {
        @Override
        public synchronized void plot(Color color) {
            super.plot(color);
        }
    }
}
