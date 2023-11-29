package edu.hw7.task4;

@SuppressWarnings({"RegexpSingleLineJava", "MagicNumber", "UncommentedMain"})
public final class PerformanceMeasurement {
    public static void main(String[] args) {
        int iterationCount = 1_000_000_000;
        System.out.println("Running " + iterationCount + " iterations");
        System.out.println();

        double singleThreadedTime = measureSpeed(() -> new SingleThreadedComputer().compute(iterationCount));
        System.out.println("Single threaded solution: " + singleThreadedTime + " s");

        double minTime = singleThreadedTime;
        int bestThreadCount = -1;
        double bestSpeedIncrease = 1;
        for (int threadCount : new int[] {1, 2, 3, 4, 5, 6, 7, 8, 16, 32, 100, 1000}) {
            double multiThreadedSolution =
                    measureSpeed(() -> new MultiThreadedComputer(threadCount).compute(iterationCount));
            double speedIncrease = singleThreadedTime / multiThreadedSolution;

            System.out.println();
            System.out.println(threadCount + " thread(s): " + multiThreadedSolution + " s");
            System.out.println("Speed increase: " + speedIncrease + " times");

            if (multiThreadedSolution < minTime) {
                minTime = multiThreadedSolution;
                bestThreadCount = threadCount;
                bestSpeedIncrease = speedIncrease;
            }
        }

        System.out.println();
        System.out.println(
                "Best speed of " + minTime + " s with speed increase of " + bestSpeedIncrease + " times achieved at "
                + (bestThreadCount == -1 ? "single threaded solution" : bestThreadCount + " threads"));
    }

    private static double measureSpeed(Runnable code) {
        long startTime = System.nanoTime();
        code.run();
        return (double) (System.nanoTime() - startTime) / 1_000_000_000;
    }

    private PerformanceMeasurement() {
    }
}
