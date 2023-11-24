package edu.hw7.task4;

import java.util.concurrent.ThreadLocalRandom;

public class SingleThreadedComputer extends MonteCarloComputer {
    @Override
    public double compute(int iterationCount) {
        int pointsWithinCircle = countPointsWithinCircle(iterationCount, ThreadLocalRandom.current());
        return getPi(iterationCount, pointsWithinCircle);
    }
}
