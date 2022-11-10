package org.example.solid;

public class LSP { // Liskov Substitution Principle

    public static void main(String[] args) {
        Rectangle r = new Rectangle(2, 3);
        useIt(r);

        Square s = new Square(4);
        useIt(s);
    }

    static void useIt(Rectangle r) { // Bad! Works for superclass but not for subclass.
        int width = r.getWidth();
        r.setHeight(10); // area = width * 10
        System.out.println("Expected: " + width * 10 + " | Got: " + r.getArea());
    }

    static class RectangleFactory {

        public Rectangle newRectangle(int width, int height) {
            return new Rectangle(width, height);
        }

        public Rectangle newSquare(int side) { // Better way to enforce a square to be square.
            return new Rectangle(side, side);
        }
    }

    static class Rectangle {
        protected int width, height;

        public Rectangle() {
        }

        public Rectangle(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getArea() {
            return width * height;
        }

        public boolean isSquare() {
            return width == height;
        }

        @Override
        public String toString() {
            return "Rectangle{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    static class Square extends Rectangle {

        public Square() {
        }

        public Square(int size) {
            width = height = size;
        }

        @Override
        public void setWidth(int width) { // Bad way to enforce a square to be a square.
            super.setWidth(width);
            super.setHeight(width);
        }

        @Override
        public void setHeight(int height) {
            super.setHeight(height);
            super.setWidth(height);
        }
    }
}
