package edu.project2;

public class KruskalGeneratorTest implements MazeGeneratorTest<KruskalGenerator> {
    @Override
    public KruskalGenerator createGenerator() {
        return new KruskalGenerator();
    }
}
