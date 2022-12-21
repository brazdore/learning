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
            this.characters = new char[lineHeight * lineWidth];
            this.lineWidth = lineWidth;
        }

        public char charAt(int x, int y) {
            return characters[y * lineWidth + x];
        }
    }

    static class Viewport {

        private final Buffer buffer;
        private final int height, width, offsetX, offsetY;

        public Viewport(Buffer buffer, int height, int width, int offsetX, int offsetY) {
            this.buffer = buffer;
            this.height = height;
            this.width = width;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }

        public char charAt(int x, int y) {
            return buffer.charAt(x + offsetX, y + offsetY);
        }
    }

    static class Console {
        private final List<Viewport> viewports = new ArrayList<>();
        private final int height, width;

        public Console(int height, int width) {
            this.height = height;
            this.width = width;
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

        public static Console newConsole(int height, int width) {
            Buffer buffer = new Buffer(height, width);
            Viewport viewport = new Viewport(buffer, height, width, 0, 0);
            Console console = new Console(height, width);
            console.addViewport(viewport);
            return console;
        }
    }
}
