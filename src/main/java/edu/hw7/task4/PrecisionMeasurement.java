package edu.hw7.task4;

@SuppressWarnings({"RegexpSingleLineJava", "MagicNumber", "UncommentedMain"})
public final class PrecisionMeasurement {
    public static void main(String[] args) {
        for (int iterationCount : new int[] {10_000_000, 100_000_000, 1_000_000_000}) {
            PiComputer computer = new MultiThreadedComputer();
            double estimate = computer.compute(iterationCount);

            System.out.println("Iteration Count: " + iterationCount);
            System.out.println("Pi Estimate: " + estimate);
            System.out.println("Offset From Actual: " + Math.abs(Math.PI - estimate));
            System.out.println();
        }
    }

    private PrecisionMeasurement() {
    }
}
