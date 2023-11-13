package edu.project2;

public class DFSGeneratorTest implements MazeGeneratorTest<DFSGenerator> {
    @Override
    public DFSGenerator createGenerator() {
        return new DFSGenerator();
    }
}
