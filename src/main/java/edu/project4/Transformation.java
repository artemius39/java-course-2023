package edu.project4;

import edu.project4.domain.Point;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Function;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.cosh;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sinh;
import static java.lang.Math.sqrt;

@SuppressWarnings("unused")
public interface Transformation extends Function<Point, Point> {
    Transformation SINUSOIDAL = coordinateWise((x, y) -> new Point(sin(x), sin(y)));
    Transformation SWIRL = coordinateWise((x, y) -> {
        double r = r2(x, y);
        double sin = sin(r);
        double cos = cos(r);
        return new Point(x * sin - y * cos, x * cos + y * sin);
    });
    Transformation SPHERICAL = coordinateWise((x, y) -> {
        double r = r2(x, y);
        return new Point(x / r, y / r);
    });
    Transformation HORSESHOE = coordinateWise((x, y) -> {
        double r = r(x, y);
        return new Point((x - y) * (x + y) / r, 2 * x * y / r);
    });
    Transformation IDENTITY = point -> point;
    Transformation HEART = coordinateWise((x, y) -> {
        double r = r(x, y);
        double theta = theta(x, y);
        return new Point(r * sin(theta * r), -r * cos(theta * r));
    });

    Transformation CYLINDER = coordinateWise((x, y) -> new Point(sin(x), y));

    Transformation BUBBLE = coordinateWise((x, y) -> {
        double r2 = r2(x, y);
        double k = 4 / (r2 + 4);
        return new Point(k * x, k * y);
    });
    Transformation EYEFISH = coordinateWise((x, y) -> {
        double r = r(x, y);
        double k = 2 / (r + 1);
        return new Point(k * x, k * y);
    });
    Transformation COSINE = coordinateWise((x, y) -> new Point(cos(PI * x) * cosh(y), -sin(PI * x) * sinh(y)));
    Transformation POWER = coordinateWise((x, y) -> {
        double r = r(x, y);
        double theta = theta(x, y);
        double k = pow(r, sin(theta));
        return new Point(k * cos(theta), k * sin(theta));
    });
    Transformation EXPONENTIAL = coordinateWise((x, y) -> {
        double k = exp(x - 1);
        return new Point(k * cos(PI * y), k * sin(PI * y));
    });
    Transformation FISHEYE = coordinateWise((x, y) -> {
        double r = r(x, y);
        double k = 2 / (r + 1);
        return new Point(k * y, k * x);
    });
    Transformation JULIA = coordinateWise((x, y) -> {
        double r = r(x, y);
        double theta = theta(x, y);
        double omega = omega();
        double k = sqrt(r);
        return new Point(k * cos(theta / 2 + omega), k * sin(theta / 2 + omega));
    });

    Transformation EX = coordinateWise((x, y) -> {
        double r = r(x, y);
        double theta = theta(x, y);
        double p0 = sin(theta + r);
        double p1 = cos(theta - r);
        double p0cubed = p0 * p0 * p0;
        double p1cubed = p1 * p1 * p1;
        return new Point(r * (p0cubed + p1cubed), r * (p0cubed - p1cubed));
    });

    Transformation DIAMOND = coordinateWise((x, y) -> {
        double theta = theta(x, y);
        double r = r(x, y);
        return new Point(sin(theta) * cos(r), cos(theta) * sin(r));
    });
    Transformation HYPERBOLIC = coordinateWise((x, y) -> {
        double theta = theta(x, y);
        double r = r(x, y);
        return new Point(sin(theta) / r, r * cos(theta));
    });
    Transformation SPIRAL = coordinateWise((x, y) -> {
        double theta = theta(x, y);
        double r = r(x, y);
        return new Point((cos(theta) + sin(r)) / r, (sin(theta) - cos(r)) / r);
    });
    Transformation DISC = coordinateWise((x, y) -> {
        double theta = theta(x, y);
        double r = r(x, y);
        double k = theta / PI;
        return new Point(k * sin(PI * r), k * cos(PI * r));
    });
    Transformation HANDKERCHIEF = coordinateWise((x, y) -> {
        double r = r(x, y);
        double theta = theta(x, y);
        return new Point(r * sin(theta + r), r * cos(theta - r));
    });
    private static double sq(double a) {
        return a * a;
    }

    private static double r2(double x, double y) {
        return sq(x) + sq(y);
    }

    private static double r(double x, double y) {
        return sqrt(r2(x, y));
    }

    private static double theta(Double x, Double y) {
        return atan2(x, y);
    }

    private static double omega() {
        return ThreadLocalRandom.current().nextBoolean() ? 0 : PI;
    }

    static Transformation coordinateWise(BiFunction<Double, Double, Point> function) {
        return point -> function.apply(point.x(), point.y());
    }
}
