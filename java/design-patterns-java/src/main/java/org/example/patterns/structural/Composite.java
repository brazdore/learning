package org.example.patterns.structural;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Composite {
    public static void main(String[] args) {
        GraphicObject drawing = new GraphicObject();
        drawing.setName("My Drawing");
        drawing.children.add(new Square("Red"));
        drawing.children.add(new Circle("Yellow"));

        GraphicObject group = new GraphicObject();
        group.children.add(new Circle("Blue"));
        group.children.add(new Square("Blue"));
        drawing.children.add(group);

        System.out.println(drawing);

        System.out.println("---");

        Neuron neuron = new Neuron();
        Neuron neuron2 = new Neuron();

        NeuronLayer layer = new NeuronLayer();
        layer.addAll(List.of(new Neuron(), new Neuron(), new Neuron()));
        NeuronLayer layer2 = new NeuronLayer();

        neuron.connectTo(neuron2);
        neuron.connectTo(layer);
        layer.connectTo(neuron);
        layer.connectTo(layer2);
        System.out.println(neuron); // 3 in, 4 out

        System.out.println("---");

        SingleValue two = new SingleValue(2);
        SingleValue three = new SingleValue(3);
        SingleValue four = new SingleValue(4);

        ManyValues eleven = new ManyValues();
        eleven.addAll(List.of(5, 6));

        MyList valueContainers = new MyList(List.of(two, three, four, eleven)); // 20
        int sum = valueContainers.sum();
        System.out.println(sum);

    }

    static class GraphicObject {
        protected String name = "Group";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public GraphicObject() {
        }

        public String color;
        public List<GraphicObject> children = new ArrayList<>(); // Recursive object. GraphicObject can either be a single object or a container of many objects.

        private void print(StringBuilder stringBuilder, int depth) {
            stringBuilder.append(String.join("", Collections.nCopies(depth, "*")))
                    .append(depth > 0 ? " " : "")
                    .append((color == null || color.isEmpty()) ? "" : color + " ")
                    .append(getName())
                    .append(System.lineSeparator());
            for (GraphicObject child : children) { // Loops through the list and treats each GraphicObject equally, being it a singular or a composition.
                child.print(stringBuilder, depth + 1);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            print(sb, 0);
            return sb.toString();
        }
    }

    static class Circle extends GraphicObject {
        public Circle(String color) {
            name = "Circle";
            this.color = color;
        }
    }

    static class Square extends GraphicObject {
        public Square(String color) {
            name = "Square";
            this.color = color;
        }
    }

    // Neural Network

    interface SomeNeurons extends Iterable<Neuron> {
        default void connectTo(SomeNeurons other) {
            if (this == other) {
                return;
            }

            for (Neuron from : this) {
                for (Neuron to : other) {
                    from.out.add(to);
                    to.in.add(from);
                }
            }
        }
    }

    static class Neuron implements SomeNeurons {
        public final List<Neuron> in = new ArrayList<>();
        public final List<Neuron> out = new ArrayList<>();


        @Override
        public Iterator<Neuron> iterator() {
            return Collections.singleton(this).iterator();
        }

        @Override
        public void forEach(Consumer<? super Neuron> action) {
            action.accept(this);
        }

        @Override
        public Spliterator<Neuron> spliterator() {
            return Collections.singleton(this).spliterator();
        }

        @Override
        public String toString() {
            return "Neuron{" +
                    "in=" + in.size() +
                    ", out=" + out.size() +
                    '}';
        }
    }

    static class NeuronLayer extends ArrayList<Neuron>
            implements SomeNeurons {
    }

    // Exercise

    interface ValueContainer extends Iterable<Integer> {
    }

    static class SingleValue implements ValueContainer {
        public int value;

        public SingleValue(int value) {
            this.value = value;
        }

        @Override
        public Iterator<Integer> iterator() {
            return Collections.singletonList(value).iterator();
        }

        @Override
        public void forEach(Consumer<? super Integer> action) {
            action.accept(value);
        }

        @Override
        public Spliterator<Integer> spliterator() {
            return Collections.singletonList(value).spliterator();
        }
    }

    static class ManyValues extends ArrayList<Integer> implements ValueContainer {
    }

    static class MyList extends ArrayList<ValueContainer> {
        public MyList(Collection<? extends ValueContainer> c) {
            super(c);
        }

        public int sum() {
            AtomicInteger result = new AtomicInteger();
            this.forEach(v -> v.forEach(result::addAndGet));
            return result.get();
        }
    }
}
