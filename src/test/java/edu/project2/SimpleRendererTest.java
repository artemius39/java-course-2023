package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static edu.project2.TestUtils.parseMaze;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleRendererTest {
    private SimpleRenderer testRenderer() {
        return new SimpleRenderer('#', ' ', '*', 'x');
    }

    private void test(String mazeString) {
        MazeRenderer renderer = testRenderer();
        Maze maze = parseMaze(mazeString);
        int cols = maze.getCols();
        String expected = addWallsOnTheSides(mazeString);
        expected = addWallAbove(expected, cols);
        expected = addWallBelow(expected, cols);
        expected = addStart(expected);
        expected = addEnd(expected);

        String render = renderer.render(maze);

        assertThat(render).isEqualTo(expected);
    }

    private String addWallsOnTheSides(String maze) {
        return maze.replaceAll("(?m)^(.*)(?m)$", "#$1#");
    }

    private String addWallAbove(String maze, int cols) {
        return "#".repeat(cols + 2) + "\n" + maze;
    }

    private String addWallBelow(String maze, int cols) {
        return maze + "\n" + "#".repeat(cols + 2);
    }

    private String addStart(String maze) {
        return maze.replace('s', 'x');
    }

    private String addEnd(String maze) {
        return maze.replace('e', 'x');
    }

    @Test
    @DisplayName("Basic maze")
    void bibaboba() {
        test("""
                #s##
                # ##
                #  e
                ####""");
    }

    @Test
    @DisplayName("No walls")
    void noWalls() {
        test("""
                s\s\s\s
                \s\s\s\s
                \s\s\se""");
    }

    @Test
    @DisplayName("No passages")
    void noPassages() {
        test("""
                #s###
                #####
                #####
                ###e#""");
    }

    @Test
    @DisplayName("One row")
    void oneRow() {
        test("###s ##e #");
    }

    @Test
    @DisplayName("One column")
    void oneCol() {
        test("""
                #
                s
                e
                \s
                #
                \s
                \s
                \s
                #""");
    }

    @Test
    @DisplayName("Empty path")
    void emptyPath() {
        MazeRenderer renderer = testRenderer();
        String mazeString = """
                #### ##
                # ## ##
                #    ##
                ###  ##
                #######""";
        Maze maze = parseMaze(mazeString);

        String mazeRendered = renderer.render(maze, List.of());

        assertThat(mazeRendered).isEqualTo("""
                #########
                ##### ###
                ## ## ###
                ##    ###
                ####  ###
                #########
                #########""");
    }

    @Test
    @DisplayName("Null path")
    void nullPath() {
        MazeRenderer renderer = testRenderer();
        String mazeString = """
                ####
                #  #
                ####""";
        Maze maze = parseMaze(mazeString);

        String mazeRendered = renderer.render(maze, null);

        assertThat(mazeRendered).isEqualTo("""
                ######
                ######
                ##  ##
                ######
                ######""");
    }

    @Test
    @DisplayName("Path render")
    void pathRender() {
        MazeRenderer renderer = testRenderer();
        String mazeString = """
                ##########
                ## # ## ##
                #       ##
                ## #### ##
                #  #    ##
                ##   #####
                ##########""";
        Maze maze = parseMaze(mazeString);

        String actual = renderer.render(maze, List.of(
                new Coordinate(1, 2),
                new Coordinate(2, 2),
                new Coordinate(2, 3),
                new Coordinate(2, 4),
                new Coordinate(2, 5),
                new Coordinate(2, 6),
                new Coordinate(2, 7),
                new Coordinate(3, 7),
                new Coordinate(4, 7),
                new Coordinate(4, 6),
                new Coordinate(4, 5),
                new Coordinate(4, 4),
                new Coordinate(5, 4),
                new Coordinate(5, 3),
                new Coordinate(5, 2),
                new Coordinate(4, 2),
                new Coordinate(4, 1)
        ));

        assertThat(actual).isEqualTo("""
                ############
                ############
                ###*# ## ###
                ## ******###
                ### ####*###
                ##**#****###
                ###***######
                ############
                ############""");
    }

    @Test
    @DisplayName("Start and end render")
    void startAndEndRender() {
        MazeRenderer renderer = testRenderer();
        String mazeString = """
                #s###
                # ###
                #   e
                #####""";

        String render = renderer.render(parseMaze(mazeString));

        assertThat(render).isEqualTo("""
                #######
                ##x####
                ## ####
                ##   x#
                #######
                #######""");
    }

    @Test
    @DisplayName("1x1 maze")
    void oneByOneMaze() {
        Coordinate coordinate = new Coordinate(0, 0);
        Maze maze = new Maze(
                1, 1,
                new Cell[][] {new Cell[]{new Cell(coordinate, Cell.Type.PASSAGE)}},
                coordinate, coordinate
        );
        MazeRenderer renderer = testRenderer();

        String render = renderer.render(maze);

        assertThat(render).isEqualTo("""
                ###
                #x#
                ###""");
    }
}
