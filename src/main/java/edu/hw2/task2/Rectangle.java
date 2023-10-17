package edu.hw2.task2;

public class Rectangle {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Rectangle dimensions must be positive");
        }

        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long area() {
        return (long) width * height;
    }

    public Rectangle setWidth(int width) {
        return new Rectangle(width, this.height);
    }

    public Rectangle setHeight(int height) {
        return new Rectangle(this.width, height);
    }
}
