package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;
import org.example.utils.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Iterator<N> {
    public static void main(String[] args) {

        //   1
        //  / \
        // 2   3

        Node<Integer> root = new Node<>(1,
                new Node<>(2),
                new Node<>(3));

        InOrderIterator<Integer> it = new InOrderIterator<>(root);

        while (it.hasNext()) {
            System.out.print(AnsiColor.YELLOW + "" + it.next() + ",");
        }

        System.out.println();
        Line.split();

        BinaryTree<Integer> tree = new BinaryTree<>(root);
        for (int n : tree) {
            System.out.print(AnsiColor.CYAN + "" + n + ",");
        }

        System.out.println();
        Line.split();

        Creature creature = new Creature();
        creature.setAgility(12);
        creature.setIntelligence(13);
        creature.setStrength(17);
        System.out.println(AnsiColor.RED +
                "Creature has a max stat of " + creature.max()
                + ", total stats of " + creature.sum()
                + " and an average stat of " + creature.average()
        );
        Line.reset();
        Line.split();

        NodeExercise<Character> node = new NodeExercise<>('a',
                new NodeExercise<>('b',
                        new NodeExercise<>('c'),
                        new NodeExercise<>('d')),
                new NodeExercise<>('e'));
        StringBuilder sb = new StringBuilder();
        java.util.Iterator<NodeExercise<Character>> iterator = node.preOrder();
        while (iterator.hasNext()) {
            sb.append(iterator.next().value);
        }
        System.out.println(AnsiColor.GREEN + sb);

        Line.reset();
    }

    // Binary Tree Traversal
    static class Node<T> {
        public T value;
        public Node<T> left, right, parent;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;

            left.parent = right.parent = this;
        }
    }

    static class InOrderIterator<T> implements java.util.Iterator<T> {
        private final Node<T> root;
        private Node<T> current;

        private boolean yieldedStart;

        public InOrderIterator(Node<T> root) {
            this.root = current = root;

            while (current.left != null) {
                current = current.left;
            }
        }

        private boolean hasRightmostParent(Node<T> node) {
            if (node.parent == null) {
                return false;
            } else {
                return (node == node.parent.left) || hasRightmostParent(node.parent);
            }
        }

        @Override
        public boolean hasNext() {
            return current.left != null
                    || current.right != null
                    || hasRightmostParent(current);
        }

        @Override
        public T next() {
            if (!yieldedStart) {
                yieldedStart = true;
                return current.value;
            }

            if (current.right != null) {
                current = current.right;
                while (current.left != null) {
                    current = current.left;
                }
            } else {
                Node<T> p = current.parent;
                while (p != null && current == p.right) {
                    current = p;
                    p = p.parent;
                }
                current = p;
            }
            return current.value;
        }
    }

    static class BinaryTree<T> implements Iterable<T> {
        private final Node<T> root; // Iterator must store a referance to what it is iterating.

        public BinaryTree(Node<T> root) {
            this.root = root;
        }

        @Override
        public java.util.Iterator<T> iterator() {
            return new InOrderIterator<>(root);
        }

        @Override
        public void forEach(Consumer<? super T> action) {
            for (T item : this) {
                action.accept(item);
            }
        }

        @Override
        public Spliterator<T> spliterator() {
            return null;
        }
    }

    // Array-Backed Properties
    static class SimpleCreature {
        private int strength, agility, intelligence;

        public int max() {
            return Math.max(strength,
                    Math.max(agility, intelligence));
        }

        public int sum() {
            return strength + agility + intelligence;
        }

        public double average() {
            return sum() / 3.0;
        }

        public int getStrength() {
            return strength;
        }

        public void setStrength(int strength) {
            this.strength = strength;
        }

        public int getAgility() {
            return agility;
        }

        public void setAgility(int agility) {
            this.agility = agility;
        }

        public int getIntelligence() {
            return intelligence;
        }

        public void setIntelligence(int intelligence) {
            this.intelligence = intelligence;
        }
    }

    static class Creature implements Iterable<Integer> {
        private final int[] stats = new int[3]; // All "groupable" fields are stored in arrays. Could have multiple arrays: stats, names, races...

        private final int str = 0; // To avoid magic numbers.

        public int getStrength() {
            return stats[str];
        }

        public void setStrength(int value) {
            stats[str] = value;
        }

        public int getAgility() {
            return stats[1];
        }

        public void setAgility(int value) {
            stats[1] = value;
        }

        public int getIntelligence() {
            return stats[2];
        }

        public void setIntelligence(int value) {
            stats[2] = value;
        }

        public int sum() {
            return IntStream.of(stats).sum();
        }

        public int max() {
            return IntStream.of(stats).max().orElse(0);
        }

        public double average() {
            return IntStream.of(stats).average().orElse(0D);
        }

        @Override
        public void forEach(Consumer<? super Integer> action) {
            for (int x : stats)
                action.accept(x);
        }

        @Override
        public Spliterator<Integer> spliterator() {
            return Arrays.spliterator(stats);
        }

        @Override
        public java.util.Iterator<Integer> iterator() {
            return IntStream.of(stats).iterator();
        }
    }

    // Exercise
    static class NodeExercise<T> {
        public T value;
        public NodeExercise<T> left, right, parent;

        public NodeExercise(T value) {
            this.value = value;
        }

        public NodeExercise(T value, NodeExercise<T> left, NodeExercise<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;

            left.parent = right.parent = this;
        }

        private void traverse(NodeExercise<T> current,
                              ArrayList<NodeExercise<T>> acc) {
            acc.add(current);
            if (current.left != null) {
                traverse(current.left, acc);
            }
            if (current.right != null) {
                traverse(current.right, acc);
            }
        }

        public java.util.Iterator<NodeExercise<T>> preOrder() {
            ArrayList<NodeExercise<T>> items = new ArrayList<>();
            traverse(this, items);
            return items.iterator();
        }
    }
}

