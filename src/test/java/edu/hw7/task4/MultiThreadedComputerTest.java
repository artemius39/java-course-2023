package edu.hw7.task4;

public class MultiThreadedComputerTest implements PiComputerTest {
    @Override
    public PiComputer getComputer() {
        return new MultiThreadedComputer();
    }
}
