package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static edu.project2.TestUtils.find;
import static edu.project2.TestUtils.findEnd;
import static edu.project2.TestUtils.findStart;
import static edu.project2.TestUtils.parseMaze;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

interface MazeSolverTest<T extends MazeSolver> {
    private void testMaze(String mazeString) {
        testMaze(mazeString, createMazeSolver());
    }

    private void testMaze(String mazeString, MazeSolver solver) {
        Maze maze = parseMaze(mazeString);

        List<Coordinate> path = solver.solve(maze);

        checkPath(path, maze, maze.getStart(), maze.getEnd());
    }

    private void checkPath(List<Coordinate> path, Maze maze, Coordinate start, Coordinate end) {
        assertThat(path.getFirst()).isEqualTo(start);
        assertThat(path.getLast()).isEqualTo(end);
        assertThat(path)
                .allMatch(coordinate -> 0 <= coordinate.row() && coordinate.row() < maze.getRows())
                .allMatch(coordinate -> 0 <= coordinate.col() && coordinate.col() < maze.getCols())
                .allMatch(coordinate -> maze.cellAt(coordinate).type() == Cell.Type.PASSAGE);

        Coordinate prev = path.getFirst();
        for (Coordinate next : path.subList(1, path.size())) {
            List<Coordinate> neighbors = prev.neighbors(maze.getRows(), maze.getCols()).toList();
            assertThat(next).isIn(neighbors);
            prev = next;
        }
    }

    T createMazeSolver();

    @Test
    @DisplayName("Straight path")
    default void straightPath() {
        testMaze("""
                #s##
                # ##
                # ##
                # ##
                # ##
                #e##
                """
        );
    }

    @Test
    @DisplayName("Twisted path")
    default void twistedPath() {
        testMaze("""
                ##s######
                #  ##   e
                # ### ###
                # ###  ##
                # #### ##
                #       #
                #########
                """);
    }

    @Test
    @DisplayName("Holes")
    default void holes() {
        testMaze("""
                #s#e#
                # # #
                    #
                # # #
                """);
    }

    @Test
    @DisplayName("Dead ends")
    default void deadEnds() {
        testMaze("""
                #######s#
                #  ## # #
                ##      #
                ## ######
                ##    ###
                ### #e###
                """);
    }

    @Test
    @DisplayName("Cycles")
    default void cycles() {
        testMaze("""
                #########
                s       #
                # ## ## #
                #    ## #
                #### ## #
                e       #
                #########
                """);
    }

    @Test
    @DisplayName("No walls")
    default void noWalls() {
        testMaze("""
                \s\s\ss e\s
                \s\s\s\s\s\s\s
                \s\s\s\s\s\s\s
                \s\s\s\s\s\s\s
                """);
    }

    @Test
    @DisplayName("Start = End")
    default void startIsTheSameAsEnd() {
        MazeSolver solver = createMazeSolver();
        String mazeString =
                """
                        #*####
                        #    #
                        ######
                        """;

        Coordinate startAndEnd = find('*', mazeString);
        List<Coordinate> path = solver.solve(
                parseMaze(mazeString),
                startAndEnd, startAndEnd
        );

        assertThat(path).isEqualTo(List.of(startAndEnd));
    }

    @Test
    @DisplayName("1x1 maze")
    default void oneByOneMaze() {
        MazeSolver solver = createMazeSolver();

        Coordinate startAndEnd = new Coordinate(0, 0);
        List<Coordinate> path = solver.solve(new Maze(
                1, 1,
                new Cell[][] {new Cell[] {new Cell(startAndEnd, Cell.Type.PASSAGE)}},
                startAndEnd, startAndEnd
        ));

        assertThat(path).isEqualTo(List.of(startAndEnd));
    }

    @Test
    @DisplayName("No solution")
    default void noSolution() {
        MazeSolver solver = createMazeSolver();
        String mazeString = """
                #s####
                #    #
                #### #
                e  ###
                ##  ##
                """;
        Coordinate start = findStart(mazeString);
        Coordinate end = findEnd(mazeString);
        Maze maze = parseMaze(mazeString);

        List<Coordinate> path = solver.solve(maze, start, end);

        assertThat(path).isNull();
    }

    @Test
    @DisplayName("Start inside")
    default void startInside() {
        testMaze("""
                ########
                ##    ##
                e  ## ##
                ###   ##
                ###s####
                ########
                """);
    }

    @Test
    @DisplayName("End inside")
    default void endInside() {
        testMaze("""
                ########
                ##   e##
                #  ## ##
                ###   ##
                ###s####
                ########
                """);
    }

    @Test
    @DisplayName("Solving multiple mazes")
    default void multipleMazes() {
        MazeSolver solver = createMazeSolver();

        testMaze("""
                ###s#########
                ### ###    ##
                ###     ## ##
                ### ###### ##
                ###     ## ##
                #######   e##
                #############
                """, solver);
        testMaze("""
                #############
                s       #   e
                ###### ## ###
                #      ##   #
                ## ###### ###
                #           #
                #############
                """, solver);
    }

    @Test
    @DisplayName("Null maze")
    default void nullMaze() {
        assertThrows(NullPointerException.class, () -> createMazeSolver().solve(null));
    }

    @Test
    @DisplayName("Null start")
    default void nullStart() {
        Coordinate coordinate = new Coordinate(1, 1);
        Maze maze = new Maze(
                1, 1,
                new Cell[][] {new Cell[] {new Cell(coordinate, Cell.Type.PASSAGE)}},
                coordinate, coordinate
        );
        assertThrows(NullPointerException.class, () -> createMazeSolver().solve(maze, null, coordinate));
    }

    @Test
    @DisplayName("Null end")
    default void nullEnd() {
        Coordinate coordinate = new Coordinate(0, 0);
        Maze maze = new Maze(
                1, 1,
                new Cell[][] {new Cell[] {new Cell(coordinate, Cell.Type.PASSAGE)}},
                coordinate, coordinate
        );
        assertThrows(NullPointerException.class, () -> createMazeSolver().solve(maze, coordinate, null));
    }

    @Test
    @DisplayName("Wall start")
    default void wallStart() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(0, 1);
        Maze maze = new Maze(
                1, 2,
                new Cell[][] {new Cell[] {new Cell(start, Cell.Type.WALL), new Cell(end, Cell.Type.PASSAGE)}},
                start, end
        );
        assertThrows(IllegalArgumentException.class, () -> createMazeSolver().solve(maze, start, end));
    }

    @Test
    @DisplayName("Wall end")
    default void wallEnd() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(0, 1);
        Maze maze = new Maze(
                1, 2,
                new Cell[][] {new Cell[] {new Cell(start, Cell.Type.PASSAGE), new Cell(end, Cell.Type.WALL)}},
                start, end
        );
        assertThrows(IllegalArgumentException.class, () -> createMazeSolver().solve(maze, start, end));
    }
}
