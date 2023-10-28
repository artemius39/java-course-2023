package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
        String expected =
                "#".repeat(maze.getCols() + 2) + "\n"
                + addWalls(mazeString.replaceAll("\n*$", "")) + "\n"
                + "#".repeat(maze.getCols() + 2);

        String render = renderer.render(maze);

        assertThat(render).isEqualTo(expected);
    }

    private String addWalls(String mazeString) {
        return mazeString.replaceAll("(?m)^(.*)(?m)$", "#$1#");
    }

    @Test
    @DisplayName("Basic maze")
    void bibaboba() {
        test("""
                ####
                # ##
                #  #
                ####
                """);
    }

    @Test
    @DisplayName("No walls")
    void noWalls() {
        test("""
                \s\s\s\s
                \s\s\s\s
                \s\s\s\s
                """);
    }

    @Test
    @DisplayName("No passages")
    void noPassages() {
        test("""
                #####
                #####
                #####
                #####
                """);
    }

    @Test
    @DisplayName("One row")
    void oneRow() {
        test("""
                ###  ##  #
                """);
    }

    @Test
    @DisplayName("One column")
    void oneCol() {
        test("""
                #
                #
                #
                \s
                #
                \s
                \s
                \s
                #
                """);
    }

    @ParameterizedTest
    @ValueSource(strings = { "#", " " })
    @DisplayName("1x1 maze")
    void oneByOneMaze(String mazeString) {
        test(mazeString);
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
}
