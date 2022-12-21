package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;
import org.example.utils.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Interpreter {
    public static void main(String[] args) {
        String exp = "(13+4)-(12+1)";
        List<Token> tokens = Token.lex(exp);
        System.out.println(AnsiColor.CYAN + tokens.stream()
                .map(Token::toString)
                .collect(Collectors.joining("\t")));

        Line.split();

        Element parsed = parse(tokens);
        System.out.println(AnsiColor.YELLOW + exp + " = " + parsed.eval());

        Line.reset();

        ExpressionProcessor.calculate("(1+2+3)");
    }

    // Lexing
    enum Type {
        INTEGER,
        PLUS,
        MINUS,
        LPAREN,
        RPAREN
    }

    static class Token {
        private final Type type;
        private String text;

        public Token(Type type, String text) {
            this.type = type;
            this.text = text;
        }

        public static List<Token> lex(String input) {
            ArrayList<Token> result = new ArrayList<>();

            for (int i = 0; i < input.length(); i++) {
                switch (input.charAt(i)) {
                    case '+' -> result.add(new Token(Type.PLUS, "+"));
                    case '-' -> result.add(new Token(Type.MINUS, "-"));
                    case '(' -> result.add(new Token(Type.LPAREN, "("));
                    case ')' -> result.add(new Token(Type.RPAREN, ")"));
                    default -> {
                        StringBuilder stringBuilder = new StringBuilder("" + input.charAt(i));
                        for (int j = i + 1; j < input.length(); j++) {
                            if (Character.isDigit(input.charAt(j))) {
                                stringBuilder.append(input.charAt(j));
                                i++;
                            } else {
                                result.add(new Token(Type.INTEGER, stringBuilder.toString()));
                                break;
                            }
                        }
                    }
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "`" + text + "`";
        }
    }

    // Parsing
    interface Element {
        int eval();
    }

    enum BinaryType {
        ADDITION,
        SUBTRACTION
    }

    static class Integer implements Element {
        private int value;

        public Integer(int value) {
            this.value = value;
        }

        @Override
        public int eval() {
            return value;
        }
    }

    static class BinaryOperation implements Element {
        private BinaryType type;
        private Element left, right;

        @Override
        public int eval() {
            switch (type) {
                case ADDITION -> {
                    return left.eval() + right.eval();
                }
                case SUBTRACTION -> {
                    return left.eval() - right.eval();
                }
                default -> {
                    return 0;
                }
            }
        }
    }

    public static Element parse(List<Token> tokens) {
        BinaryOperation result = new BinaryOperation();
        boolean haveLHS = false;

        for (int i = 0; i < tokens.size(); ++i) {
            Token token = tokens.get(i);

            switch (token.type) {
                case INTEGER -> {
                    Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
                    if (!haveLHS) {
                        result.left = integer;
                        haveLHS = true;
                    } else {
                        result.right = integer;
                    }
                }
                case PLUS -> result.type = BinaryType.ADDITION;
                case MINUS -> result.type = BinaryType.SUBTRACTION;
                case LPAREN -> {
                    int j = i; // Location of RPAREN, no nesting!
                    for (; j < tokens.size(); ++j) {
                        if (tokens.get(j).type == Type.RPAREN) {
                            break;
                        }
                    }
                    List<Token> subExp = tokens.stream()
                            .skip(i + 1)
                            .limit(j - i - 1)
                            .collect(Collectors.toList());
                    Element element = parse(subExp);
                    if (!haveLHS) {
                        result.left = element;
                        haveLHS = true;
                    } else result.right = element;
                    i = j;
                }
            }
        }
        return result;
    }

    // Exercise
    static class ExpressionProcessor {
        public Map<Character, Integer> variables = new HashMap<>();

        public static void calculate(String expression) { // "(1+2+3)"
            expression.chars()
                    .mapToObj(i -> (char) i)
                    .filter(c -> !remove(c))
                    .forEach(System.out::println);
        }

        private static boolean remove(Character c) {
            return c == '(' || c == ')';
        }
    }

}
