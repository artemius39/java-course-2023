package edu.project2;

class ParallelDFSSolverTest implements MazeSolverTest<ParallelDFSSolver> {
    @Override
    public ParallelDFSSolver createMazeSolver() {
        return new ParallelDFSSolver();
    }
}
