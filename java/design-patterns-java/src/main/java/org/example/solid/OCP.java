package org.example.solid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OCP { // Open-Close Principle + Specification

    public static void main(String[] args) {
        Product leaf = new Product("Pomu Leaf", Color.GREEN, Size.SMALL);
        Product dakimakura = new Product("Finana Dakimakura", Color.WHITE, Size.MEDIUM);
        Product rosebed = new Product("Rosebed", Color.RED, Size.LARGE);
        Product bread = new Product("Petra Bread", Color.RED, Size.MEDIUM);
        List<Product> products = List.of(leaf, dakimakura, rosebed, bread);

        ProductFilter productFilter = new ProductFilter();

        Stream<Product> green = productFilter.filter(products, new ColorSpecification(Color.GREEN));
        green.forEach(System.out::println);
        Stream<Product> medium = productFilter.filter(products, new SizeSpecification(Size.MEDIUM));
        medium.forEach(System.out::println);

        System.out.println("---");

        Stream<Product> redLarge = productFilter.filter(products, new AndSpecification<>(
                new ColorSpecification(Color.RED),
                new SizeSpecification(Size.LARGE)));
        redLarge.forEach(System.out::println);

        System.out.println("---");

        AndAllSpecification<Product> redMediumSpec = new AndAllSpecification<>();
        redMediumSpec.addAll(List.of(new ColorSpecification(Color.RED), new SizeSpecification(Size.MEDIUM)));

        Stream<Product> redMedium = productFilter.filter(products, redMediumSpec);
        redMedium.forEach(System.out::println);

    }

    enum Color {
        WHITE, GREEN, RED
    }

    enum Size {
        SMALL, MEDIUM, LARGE
    }

    interface Specification<T> {
        boolean isSatisfied(T item);
    }

    interface Filter<T> {
        Stream<T> filter(List<T> items, Specification<T> spec);
    }

    static class Product {
        public String name;
        public Color color;
        public Size size;

        public Product(String name, Color color, Size size) {
            this.name = name;
            this.color = color;
            this.size = size;
        }

        @Override
        public String toString() {
            return name + " is " + color + " and " + size + ".";
        }
    }

    static class ProductFilter implements Filter<Product> {

        @Override
        public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
            return items.stream().filter(spec::isSatisfied);
        }
    }

    static class ColorSpecification implements Specification<Product> {
        public Color color;

        public ColorSpecification(Color color) {
            this.color = color;
        }

        @Override
        public boolean isSatisfied(Product item) {
            return color == item.color;
        }
    }

    static class SizeSpecification implements Specification<Product> {
        public Size size;

        public SizeSpecification(Size size) {
            this.size = size;
        }

        @Override
        public boolean isSatisfied(Product item) {
            return size == item.size;
        }
    }

    static class AndSpecification<T> implements Specification<T> {
        private final Specification<T> first;
        private final Specification<T> second;

        public AndSpecification(Specification<T> first, Specification<T> second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean isSatisfied(T item) {
            return first.isSatisfied(item) && second.isSatisfied(item);
        }
    }

    static class AndAllSpecification<T> implements Specification<T> {
        private final List<Specification<T>> specList = new ArrayList<>();

        public void addAll(List<Specification<T>> specifications) {
            specList.addAll(specifications);
        }

        @Override
        public boolean isSatisfied(T item) {
            return specList.stream().allMatch(s -> s.isSatisfied(item));
        }
    }
}
