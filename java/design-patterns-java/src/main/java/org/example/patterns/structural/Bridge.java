package org.example.patterns.structural;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class Bridge {
    public static void main(String[] args) {
        VectorRenderer vectorRenderer = new VectorRenderer();
        RasterRenderer rasterRenderer = new RasterRenderer();
        Circle circle = new Circle(vectorRenderer, 2);
        circle.draw();
        circle.resize(2);
        circle.draw();

        System.out.println("---");

        Injector injector = Guice.createInjector(new ShapeModule());
        Circle instance = injector.getInstance(Circle.class);
        instance.radius = 2;
        instance.draw();
        instance.resize(2);
        instance.draw();

        System.out.println("---");

        String tp = new Triangle(new RasterRendererEx()).toString();
        System.out.println(tp); // "Drawing Triangle as pixels"
        String tl = new Triangle(new VectorRendererEx()).toString();
        System.out.println(tl); // "Drawing Triangle as lines"
        String sp = new Square(new RasterRendererEx()).toString();
        System.out.println(sp); // "Drawing Square as pixels"
        String sl = new Square(new VectorRendererEx()).toString();
        System.out.println(sl); // "Drawing Square as lines"

    }

    interface Renderer {
        void renderCircle(float radius);
    }

    static class VectorRenderer implements Renderer {
        @Override
        public void renderCircle(float radius) {
            System.out.println("VectorRender: " + radius);
        }
    }

    static class RasterRenderer implements Renderer {
        @Override
        public void renderCircle(float radius) {
            System.out.println("RasterRenderer: " + radius);
        }
    }

    abstract static class Shape {
        protected Renderer renderer;

        public Shape(Renderer renderer) {
            this.renderer = renderer;
        }

        public abstract void draw();

        public abstract void resize(float factor);
    }

    static class Circle extends Shape {
        protected float radius;

        @Inject
        public Circle(Renderer renderer) {
            super(renderer);
        }

        public Circle(Renderer renderer, float radius) {
            super(renderer);
            this.radius = radius;
        }

        @Override
        public void draw() {
            this.renderer.renderCircle(radius);
        }

        @Override
        public void resize(float factor) {
            this.radius *= factor;
        }
    }

    static class ShapeModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Renderer.class).to(VectorRenderer.class); // Every Renderer dependency will be injected with an instance of VectorRenderer.
        }
    }

    // Exercise

    interface RendererEx {
        String whatToRenderAs();
    }

    static abstract class ShapeEx {
        protected RendererEx renderer;

        public ShapeEx(RendererEx renderer) {
            this.renderer = renderer;
        }

        public abstract String getName();
    }

    static class Triangle extends ShapeEx {
        public Triangle(RendererEx renderer) {
            super(renderer);
        }

        @Override
        public String getName() {
            return "Triangle";
        }

        @Override
        public String toString() {
            return "Drawing " + getName() + " as " + renderer.whatToRenderAs();
        }
    }

    static class Square extends ShapeEx {
        public Square(RendererEx renderer) {
            super(renderer);
        }

        @Override
        public String getName() {
            return "Square";
        }

        @Override
        public String toString() {
            return "Drawing " + getName() + " as " + renderer.whatToRenderAs();
        }
    }

    static class VectorRendererEx implements RendererEx {
        @Override
        public String whatToRenderAs() {
            return "lines";
        }
    }

    static class RasterRendererEx implements RendererEx {
        @Override
        public String whatToRenderAs() {
            return "pixels";
        }
    }
}
