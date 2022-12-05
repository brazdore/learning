package org.example.patterns.structural;

import org.example.utils.Line;

import java.util.*;
import java.util.function.Function;

public class Flyweight {
    public static void main(String[] args) {
        User johnSmith = new User("John Smith");
        User janeSmith = new User("Jane Smith");
        String s = "a";

        UserFlyweight takanashiKiara = new UserFlyweight("Takanashi Kiara");
        UserFlyweight takanashiClara = new UserFlyweight("Takanashi Clara");

        System.out.println(takanashiKiara.getFullName());
        System.out.println(takanashiClara.getFullName());

        Line.split();

        FormattedText ft = new FormattedText("I'm Pomu");
        ft.capitalize(4, 7);
        System.out.println(ft);

        BetterFormattedText bft = new BetterFormattedText("How's it growing, buds?");
        bft.getRange(9, 15).capitalize = true;
        System.out.println(bft);

        Line.split();

        var sentence = new Sentence("hello world");
        sentence.getWord(1).capitalize = true;
        System.out.println(sentence); // writes "hello WORLD"

    }

    // Repeating names
    static class User {
        private final String fullName;

        public User(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "fullName='" + fullName + '\'' +
                    '}';
        }
    }

    static class UserFlyweight {
        private static final List<String> strings = new ArrayList<>(); // Shared by the class. "Takanashi" or "Smith" would only be stored once, allowing reuse.
        private final int[] names;

        public UserFlyweight(String fullName) {
            Function<String, Integer> getOrAdd = (String s) -> {
                int index = strings.indexOf(s);
                if (index != -1) {
                    return index;
                }
                strings.add(s);
                return strings.size() - 1;
            };

            names = Arrays.stream(fullName.split(" "))
                    .mapToInt(getOrAdd::apply)
                    .toArray();
        }

        public String getFullName() {
            StringBuilder sb = new StringBuilder();

            Arrays.stream(names)
                    .forEach(i -> sb.append(strings.get(i)).append(" "));

            return sb.deleteCharAt(sb.length() - 1).toString();
        }
    }

    // Formatting text
    static class FormattedText {
        private final String plainText;
        private final boolean[] capitalize;

        public FormattedText(String plainText) {
            this.plainText = plainText;
            capitalize = new boolean[plainText.length()];
        }

        public void capitalize(int start, int end) {
            for (int i = start; i <= end; ++i)
                capitalize[i] = true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < plainText.length(); ++i) {
                char c = plainText.charAt(i);
                sb.append(capitalize[i] ? Character.toUpperCase(c) : c);
            }
            return sb.toString();
        }
    }

    static class BetterFormattedText {
        private final String plainText;
        private final List<TextRange> formatting = new ArrayList<>();

        public BetterFormattedText(String plainText) {
            this.plainText = plainText;
        }

        public TextRange getRange(int start, int end) {
            TextRange range = new TextRange(start, end);
            formatting.add(range);
            return range;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < plainText.length(); ++i) {
                char c = plainText.charAt(i);
                for (TextRange range : formatting) {
                    if (range.covers(i) && range.capitalize)
                        c = Character.toUpperCase(c);
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        static class TextRange { // Flyweight.
            public int start, end;
            public boolean capitalize, bold, italic;

            public TextRange(int start, int end) {
                this.start = start;
                this.end = end;
            }

            public boolean covers(int position) {
                return position >= start && position <= end;
            }
        }
    }

    // Exercise

    static class Sentence {
        private final String[] words;
        private final Map<Integer, WordToken> tokens = new HashMap<>();

        public Sentence(String plainText) {
            this.words = plainText.split(" ");
        }

        public WordToken getWord(int index) {
            WordToken wordToken = new WordToken();
            tokens.put(index, wordToken);
            return wordToken;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < words.length; i++) {
                final WordToken wordToken = tokens.get(i);
                if (wordToken != null && wordToken.capitalize) {
                    sb.append(words[i].toUpperCase()).append(" ");
                } else sb.append(words[i]).append(" ");
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        }

        static class WordToken {
            public boolean capitalize;
        }
    }
}
