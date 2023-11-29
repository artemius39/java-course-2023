package edu.hw7.task4;

import java.util.random.RandomGenerator;

abstract class MonteCarloComputer implements PiComputer {
    protected int countPointsWithinCircle(int iterationCount, RandomGenerator random) {
        int pointsWithinCircle = 0;
        for (int iteration = 0; iteration < iterationCount; iteration++) {
            double x = random.nextDouble(1);
            double y = random.nextDouble(1);

            if (x * x + y * y < 1) {
                pointsWithinCircle++;
            }
        }
        return pointsWithinCircle;
    }

    @SuppressWarnings("MagicNumber")
    protected double getPi(int iterationCount, int pointsWithinCircle) {
        return 4 * (double) pointsWithinCircle / iterationCount;
    }
}
