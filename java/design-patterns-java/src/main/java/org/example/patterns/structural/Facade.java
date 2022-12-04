package org.example.patterns.structural;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    public static void main(String[] args) {
        Buffer buffer = new Buffer(30, 20);
        Viewport viewport = new Viewport(buffer, 30, 20, 0, 0);
        Console console = new Console(30, 20);
        console.addViewport(viewport);
        console.render(); // Verbose. As an user, I would like to be able to create and render a console more easily, without caring too much abouts its internal components.

        Console console1 = Console.newConsole(30, 20); // Much easier!
        console1.render();
    }

    static class Buffer {
        private final char[] characters;
        private final int lineWidth;

        public Buffer(int lineHeight, int lineWidth) {
            this.characters = new char[lineHeight * lineHeight];
            this.lineWidth = lineWidth;
        }

        public char charAt(int x, int y) {
            return characters[lineWidth * y + x];
        }
    }

    static class Viewport {

        private final Buffer buffer;
        private final int width, height, offsetX, offsetY;

        public Viewport(Buffer buffer, int width, int height, int offsetX, int offsetY) {
            this.buffer = buffer;
            this.width = width;
            this.height = height;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }

        public char charAt(int x, int y) {
            return buffer.charAt(x + offsetX, y + offsetY);
        }
    }

    static class Console {
        private final List<Viewport> viewports = new ArrayList<>();
        private final int width, height;

        public Console(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void addViewport(Viewport viewport) {
            viewports.add(viewport);
        }

        public void render() {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    for (Viewport viewport : viewports) {
                        System.out.println(viewport.charAt(x, y));
                    }
                }
            }
        }

        public static Console newConsole(int width, int height) {
            Buffer buffer = new Buffer(height, width);
            Viewport viewport = new Viewport(buffer, width, height, 0, 0);
            Console console = new Console(width, height);
            console.addViewport(viewport);
            return console;
        }
    }
}
