package edu.project2;

public class BFSSolverTest implements MazeSolverTest<BFSSolver> {
    @Override
    public BFSSolver createMazeSolver() {
        return new BFSSolver();
    }
}
