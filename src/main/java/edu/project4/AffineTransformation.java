package edu.project4;

import edu.project4.domain.Point;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class AffineTransformation implements Transformation {
    private static final int MAX_COLOR = 256;
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;
    private final Color color;

    public AffineTransformation(double a, double b, double c, double d, double e, double f, Color color) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.color = color;
    }

    private static double sq(double x) {
        return x * x;
    }

    public static AffineTransformation generate() {
        int red = ThreadLocalRandom.current().nextInt(MAX_COLOR);
        int green = ThreadLocalRandom.current().nextInt(MAX_COLOR);
        int blue = ThreadLocalRandom.current().nextInt(MAX_COLOR);
        Color color = new Color(red, green, blue);

        while (true) {
            double a = ThreadLocalRandom.current().nextDouble(-1, 1);
            double b = ThreadLocalRandom.current().nextDouble(-1, 1);
            double c = ThreadLocalRandom.current().nextDouble(-1, 1);
            double d = ThreadLocalRandom.current().nextDouble(-1, 1);
            double e = ThreadLocalRandom.current().nextDouble(-1, 1);
            double f = ThreadLocalRandom.current().nextDouble(-1, 1);

            if (sq(a) + sq(d) < 1 && sq(b) + sq(e) < 1 && sq(a) + sq(b) + sq(d) + sq(e) < 1 + sq(a * e - b * d)) {
                return new AffineTransformation(a, b, c, d, e, f, color);
            }
        }
    }

    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        return new Point(a * x + b * y + c, d * x + e * y + f);
    }

    public Color getColor() {
        return color;
    }
}
