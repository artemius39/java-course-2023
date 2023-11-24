package edu.project3;

public final class Application {
    public static void main(String[] args) {
        try {
            new LogAnalyzer().analyze(args);
        } catch (IllegalCLIArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
        } catch (InvalidLogFormatException e) {
            System.err.println("Invalid logs: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Something went wrong during execution");
        }
    }

    private Application() {
    }
}
