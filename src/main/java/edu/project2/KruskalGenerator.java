package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalGenerator extends GraphTraversingGenerator {
    private List<Edge> makeEdgeList(int rows, int cols) {
        List<Edge> edges = new ArrayList<>();
        for (int row = 0; row < rows; row += 2) {
            for (int col = 0; col < cols; col += 2) {
                Coordinate cell = new Coordinate(row, col);
                if (row + 2 < rows) {
                    edges.add(new Edge(cell, new Coordinate(row + 2, col)));
                }
                if (col + 2 < cols) {
                    edges.add(new Edge(cell, new Coordinate(row, col + 2)));
                }
            }
        }
        Collections.shuffle(edges);
        return edges;
    }

    @Override
    public Cell[][] traverse(int rows, int cols) {
        DisjointSetUnion dsu = new DisjointSetUnion(rows, cols);
        List<Edge> edges = makeEdgeList(rows, cols);

        Cell[][] cells = new Cell[rows][cols];
        for (int row = 0; row < rows; row += 2) {
            for (int col = 0; col < cols; col += 2) {
                cells[row][col] = new Cell(new Coordinate(row, col), Cell.Type.PASSAGE);
            }
        }

        for (Edge edge : edges) {
            Coordinate cell = edge.cell();
            Coordinate neighbor = edge.neighbor();
            Coordinate between = new Coordinate(
                    (neighbor.row() + cell.row()) / 2,
                    (neighbor.col() + cell.col()) / 2
            );

            if (dsu.areInTheSameSet(cell, neighbor)) {
                cells[between.row()][between.col()] = new Cell(between, Cell.Type.WALL);
            } else {
                cells[between.row()][between.col()] = new Cell(between, Cell.Type.PASSAGE);
                dsu.join(cell, neighbor);
            }
        }

        return cells;
    }

    private record Edge(Coordinate cell, Coordinate neighbor) {
    }

    private static class DisjointSetUnion {
        private final Coordinate[][] parents;
        private final int[][] rank;

        DisjointSetUnion(int rows, int cols) {
            rank = new int[rows][cols];

            parents = new Coordinate[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    parents[row][col] = new Coordinate(row, col);
                }
            }
        }

        public Coordinate getRoot(Coordinate cell) {
            Coordinate parent = parents[cell.row()][cell.col()];
            if (parent.equals(cell)) {
                return parent;
            } else {
                Coordinate root = getRoot(parent);
                setParent(cell, root);
                return root;
            }
        }

        public boolean areInTheSameSet(Coordinate cell, Coordinate neighbor) {
            return getRoot(cell).equals(getRoot(neighbor));
        }

        public void join(Coordinate cell, Coordinate neighbor) {
            if (areInTheSameSet(cell, neighbor)) {
                return;
            }

            Coordinate cellRoot = getRoot(cell);
            Coordinate neighborRoot = getRoot(neighbor);
            int cellSetRank = rank[cellRoot.row()][cellRoot.col()];
            int neighborSetRank = rank[neighborRoot.row()][neighborRoot.col()];

            if (cellSetRank == neighborSetRank) {
                rank[cellRoot.row()][cellRoot.col()]++;
                setParent(neighborRoot, cellRoot);
            } else if (cellSetRank < neighborSetRank) {
                setParent(cellRoot, neighborRoot);
            } else /*if (cellSetRank > neighborSetRank)*/ {
                setParent(neighborRoot, cellRoot);
            }
        }

        private void setParent(Coordinate cell, Coordinate parent) {
            parents[cell.row()][cell.col()] = parent;
        }
    }
}
