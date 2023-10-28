package edu.project2;

@SuppressWarnings({"MagicNumber", "RegexpSingleLineJava"})
public final class Main {
    public static void main(String[] args) {
        // a little playground to see everything in action
        // before using it, it's best to change line height in terminal to 0.9 in IDE settings
        // (Editor -> Color Scheme -> Console Font -> Line height)
        demo(new KruskalGenerator(), new BFSSolver(), new SimpleRenderer(), 15, 15);
    }

    private static void demo(MazeGenerator generator, MazeSolver solver, MazeRenderer renderer, int rows, int cols) {
        Maze maze = generator.generate(rows, cols);

        System.out.println("Maze:");
        System.out.println(renderer.render(maze));
        System.out.println();
        System.out.println("Solution:");
        System.out.println(renderer.render(maze, solver.solve(maze)));
    }

    private Main() {
    }
}
