package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DFSGenerator extends GraphTraversingGenerator {
    @Override
    protected Cell[][] traverse(int rows, int cols) {
        Cell[][] cells = new Cell[rows][cols];
        dfs(new Coordinate(0, 0), cells, rows, cols);
        return cells;
    }

    private void dfs(Coordinate coordinate, Cell[][] cells, int rows, int cols) {
        cells[coordinate.row()][coordinate.col()] = new Cell(coordinate, Cell.Type.PASSAGE);

        List<Coordinate> neighbors = coordinate.neighbors(rows, cols, 2)
                .filter(neighbor -> cells[neighbor.row()][neighbor.col()] == null)
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(neighbors);

        for (Coordinate neighbor : neighbors) {
            Coordinate between = new Coordinate(
                    (neighbor.row() + coordinate.row()) / 2,
                    (neighbor.col() + coordinate.col()) / 2
            );
            if (cells[neighbor.row()][neighbor.col()] == null) {
                cells[between.row()][between.col()] = new Cell(between, Cell.Type.PASSAGE);
                dfs(neighbor, cells, rows, cols);
            } else if (cells[between.row()][between.col()] == null) {
                cells[between.row()][between.col()] = new Cell(between, Cell.Type.WALL);
            }
        }
    }
}
