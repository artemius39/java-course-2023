package edu.project2;

public class DFSSolverTest implements MazeSolverTest<DFSSolver> {
    @Override
    public DFSSolver createMazeSolver() {
        return new DFSSolver();
    }
}
