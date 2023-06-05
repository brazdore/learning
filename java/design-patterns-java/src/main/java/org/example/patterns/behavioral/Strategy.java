package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;

import java.util.List;
import java.util.function.Supplier;

public class Strategy {
    public static void main(String[] args) {
        List<String> names = List.of("Finana Ryugu", "Enna Alouette", "Rosemi Lovelock");
        TextProcessor tp = new TextProcessor(OutputFormat.MARKDOWN);
        tp.createList(names);
        System.out.println(AnsiColor.YELLOW + "Dynamic markdown list:");
        System.out.println(tp);

        System.out.println(AnsiColor.RESET);

        tp.clear();
        tp.setStrategy(OutputFormat.HTML); // Switch list creation strategy; very flexible!
        tp.createList(names);
        System.out.println(AnsiColor.GREEN + "Dynamic HTML list:");
        System.out.println(tp);

        System.out.println(AnsiColor.RESET);

        StaticTextProcessor<MarkdownListStrategy> markdownProcessor = new StaticTextProcessor<>(MarkdownListStrategy::new);
        markdownProcessor.createList(names);
        System.out.println(AnsiColor.CYAN + "Static markdown list:");
        System.out.println(markdownProcessor);

        System.out.println(AnsiColor.RESET);

        StaticTextProcessor<HtmlListStrategy> htmlProcessor = new StaticTextProcessor<>(HtmlListStrategy::new);
        htmlProcessor.createList(names);
        System.out.println(AnsiColor.PURPLE + "Static HTML list:");
        System.out.println(htmlProcessor);

        System.out.println(AnsiColor.RESET);
    }

    // Dynamic Strategy

    // HTML: <ul><li></li></ul>
    // Markdown: * foo
    enum OutputFormat {
        MARKDOWN, HTML
    }

    interface ListStrategy {
        default void start(StringBuilder sb) {
        }

        void addListItem(StringBuilder sb, String item);

        default void end(StringBuilder sb) {
        }
    }

    static class MarkdownListStrategy implements ListStrategy {
        @Override
        public void addListItem(StringBuilder sb, String item) {
            sb.append(" * ")
                    .append(item)
                    .append(System.lineSeparator());
        }
    }

    static class HtmlListStrategy implements ListStrategy {
        @Override
        public void start(StringBuilder sb) {
            sb.append("<ul>")
                    .append(System.lineSeparator());
        }

        @Override
        public void addListItem(StringBuilder sb, String item) {
            sb.append("  <li>")
                    .append(item)
                    .append("</li>")
                    .append(System.lineSeparator());
        }

        @Override
        public void end(StringBuilder sb) {
            sb.append("</ul>")
                    .append(System.lineSeparator());
        }
    }

    static class TextProcessor {
        private final StringBuilder sb = new StringBuilder();
        private ListStrategy strategy;

        public TextProcessor(OutputFormat outputFormat) {
            setStrategy(outputFormat);
        }

        public void setStrategy(OutputFormat outputFormat) {
            switch (outputFormat) {
                case MARKDOWN -> strategy = new MarkdownListStrategy();
                case HTML -> strategy = new HtmlListStrategy();
                default -> throw new IllegalStateException("Unexpected value: " + outputFormat);
            }
        }

        public void createList(List<String> items) {
            strategy.start(sb);
            items.forEach(i -> strategy.addListItem(sb, i));
            strategy.end(sb);
        }

        public void clear() {
            sb.setLength(0);
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }

    // Static Strategy
    static class StaticTextProcessor<T extends ListStrategy> {
        private final StringBuilder sb = new StringBuilder();
        private final T strategy;

        public StaticTextProcessor(Supplier<? extends T> constructor) { // Upper Bounded Wildcards: List<? extends Number> – represents a list of Number or its subtypes (for instance, Double or Integer).
            strategy = constructor.get();
        }

        public void createList(List<String> items) {
            strategy.start(sb);
            items.forEach(i -> strategy.addListItem(sb, i));
            strategy.end(sb);
        }

        public void clear() {
            sb.setLength(0);
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }

    // Exercise
    interface DiscriminantStrategy {
        double calculateDiscriminant(double a, double b, double c);
    }

    static class OrdinaryDiscriminantStrategy implements DiscriminantStrategy {
        @Override
        public double calculateDiscriminant(double a, double b, double c) {
            return (Math.pow(b, 2D)) - (4 * a * c);
        }
    }

    static class RealDiscriminantStrategy implements DiscriminantStrategy {
        @Override
        public double calculateDiscriminant(double a, double b, double c) {
            double discriminant = (Math.pow(b, 2D)) - (4 * a * c);
            return discriminant < 0 ?
                    Double.NaN :
                    discriminant;
        }
    }
}