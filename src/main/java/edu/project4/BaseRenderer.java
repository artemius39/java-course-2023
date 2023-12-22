package edu.project4;

import edu.project4.domain.Pixel;
import edu.project4.domain.Point;
import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

abstract class BaseRenderer implements Renderer {
    private static final int ITERATIONS_WITHOUT_PLOTTING = 20;
    private static final int TRANSFORMATION_COUNT = 5;

    protected void sample(PixelBuilder[][] pixels, List<Transformation> variations,
                          List<AffineTransformation> transformations, int iterationsPerSample, int width, int height,
                          int symmetry) {
        Point point = getRandomPoint();
        Color color = null;
        for (int iteration = -ITERATIONS_WITHOUT_PLOTTING; iteration < iterationsPerSample; iteration++) {
            AffineTransformation affineTransformation = choose(transformations);
            Transformation variation = choose(variations);

            point = variation.compose(affineTransformation).apply(point);
            color = combineColors(color, affineTransformation.getColor());

            if (iteration < 0) {
                continue;
            }

            double theta = 0;
            for (int s = 0; s < symmetry; theta += 2 * Math.PI / symmetry, s++) {
                Point rotated = rotate(point, theta);
                int x = scale(rotated.x(), width);
                int y = scale(rotated.y(), height);
                if (0 <= x && x < width && 0 <= y && y < height) {
                    pixels[x][y].plot(color);
                }
            }
        }
    }

    protected void validate(List<Transformation> variations,
                            int samples, int iterationsPerSample, int symmetry,
                            int width, int height) {
        Objects.requireNonNull(variations, "variations cannot be null");
        if (samples <= 0) {
            throw new IllegalArgumentException("sample count must be positive");
        }
        if (iterationsPerSample <= 0) {
            throw new IllegalArgumentException("iteration count must be positive");
        }
        if (symmetry <= 0) {
            throw new IllegalArgumentException("symmetry number must be positive");
        }
        if (width <= 0) {
            throw new IllegalArgumentException("width must be positive");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("height must be positive");
        }
    }

    protected List<AffineTransformation> generateTransformations() {
        return Stream.generate(AffineTransformation::generate)
                .limit(TRANSFORMATION_COUNT)
                .toList();
    }

    protected PixelBuilder[][] makeBuilders(int width, int height) {
        PixelBuilder[][] pixels = new PixelBuilder[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new PixelBuilder();
            }
        }
        return pixels;
    }

    private <E> E choose(List<E> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    private Point getRandomPoint() {
        return new Point(
                ThreadLocalRandom.current().nextDouble(-1, 1),
                ThreadLocalRandom.current().nextDouble(-1, 1)
        );
    }

    private int scale(double coordinate, int dimension) {
        return (int) ((coordinate + 1) * dimension / 2);
    }

    private Color combineColors(Color color, Color transformationColor) {
        return color == null ? transformationColor : new Color(
                (color.getRed() + transformationColor.getRed()) / 2,
                (color.getGreen() + transformationColor.getGreen()) / 2,
                (color.getBlue() + transformationColor.getBlue()) / 2
        );
    }

    private Point rotate(Point point, double angle) {
        double x = point.x();
        double y = point.y();
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Point(x * cos - y * sin, x * sin + y * cos);
    }

    protected Pixel[][] buildAll(PixelBuilder[][] pixels, int width, int height) {
        Pixel[][] result = new Pixel[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = pixels[x][y].build();
            }
        }
        return result;
    }

    protected static class PixelBuilder {
        private Color color;
        private int hitCount;

        PixelBuilder() {
            color = Color.BLACK;
            hitCount = 0;
        }

        public Pixel build() {
            return new Pixel(color, hitCount);
        }

        public void plot(Color color) {
            this.color = color;
            hitCount++;
        }
    }
}
