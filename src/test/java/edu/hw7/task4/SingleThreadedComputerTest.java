package edu.hw7.task4;

public class SingleThreadedComputerTest implements PiComputerTest {
    @Override
    public PiComputer getComputer() {
        return new SingleThreadedComputer();
    }
}
