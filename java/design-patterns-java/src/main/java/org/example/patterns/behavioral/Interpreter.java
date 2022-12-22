package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;
import org.example.utils.Line;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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

        Line.split();

        ExpressionProcessor expressionProcessor = new ExpressionProcessor();
        expressionProcessor.parse("1+2+3");
        expressionProcessor.calculate(); // 6
        expressionProcessor.clear();

        Line.split();

        expressionProcessor.parse("1+2+xy");
        expressionProcessor.calculate(); // 0
        expressionProcessor.clear();

        Line.split();

        expressionProcessor.addVariable('x', 3);
        expressionProcessor.parse("(10-2-x)");
        expressionProcessor.calculate(); // 5
        expressionProcessor.clear();
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
        private final String text;

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
        private final int value;

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
    enum Operation {
        ADDITION("+"),
        SUBTRACTION("-");

        final String simbol;

        Operation(String simbol) {
            this.simbol = simbol;
        }

        public static Operation fromSimbol(String s) {
            if (Objects.equals(s, ADDITION.simbol)) {
                return Operation.ADDITION;
            }

            if (Objects.equals(s, SUBTRACTION.simbol)) {
                return Operation.SUBTRACTION;
            }

            throw new IllegalArgumentException("Unknown operation.");
        }
    }

    static class ExpressionProcessor {
        public Map<Character, java.lang.Integer> variables = new HashMap<>();
        private final List<java.lang.Integer> numbers = new ArrayList<>();
        private final List<Operation> operations = new ArrayList<>();
        private boolean illegal = false;

        public void parse(String expression) { // "(1+2+3)"
            System.out.println(AnsiColor.YELLOW + "exp: " + expression);
            Line.reset();

            String withoutParenthesis = expression.replaceAll("[(|)]", "");
            String[] valueArr = withoutParenthesis.split("[+|-]");
            String[] operationArr = withoutParenthesis.replaceAll("[0-9a-zA-Z]", "").split("");

            System.out.println(AnsiColor.PURPLE + "valueArr: " + Arrays.toString(valueArr));
            System.out.println(AnsiColor.PURPLE + "operationArr: " + Arrays.toString(operationArr));
            Line.reset();

            List<String> valueList = Arrays.stream(valueArr).map(String::valueOf).toList();
            List<Operation> operationList = Arrays.stream(operationArr)
                    .map(String::valueOf)
                    .map(Operation::fromSimbol)
                    .toList();

            valueList.forEach(s -> {
                if (s.length() > 1 && s.matches("[a-zA-Z]+")) {
                    illegal = true;
                    return;
                }

                if (s.matches("[a-zA-Z]+") && !variables.containsKey(s.charAt(0))) {
                    numbers.add(0);
                    return;
                }

                if (variables.containsKey(s.charAt(0))) {
                    numbers.add(variables.get(s.charAt(0)));
                    return;
                }

                java.lang.Integer converted = convert(s);
                if (converted != null) {
                    numbers.add(converted);
                }
            });

            operations.addAll(operationList);

            System.out.println(AnsiColor.RED + "numbers: " + numbers);
            System.out.println(AnsiColor.BLUE + "operations: " + operations);
            Line.reset();
        }

        public void calculate() {
            if (illegal) {
                System.out.println(AnsiColor.CYAN + "result: 0");
                return;
            }

            final int[] index = {0};
            AtomicBoolean firstRun = new AtomicBoolean(true);
            java.lang.Integer reduce = numbers.stream()
                    .reduce(0, (x, y) -> {
                        if (firstRun.get()) {
                            firstRun.set(false);
                            return y;
                        }
                        Operation op = operations.get(index[0]);
                        index[0] = index[0]++;
                        return doMath(x, y, op);
                    });
            System.out.println(AnsiColor.CYAN + "result: " + reduce);
            Line.reset();
        }

        public void addVariable(Character var, java.lang.Integer value) {
            variables.put(var, value);
        }

        public void clear() {
            illegal = false;
            operations.clear();
            numbers.clear();
            variables.clear();
        }

        private int doMath(int x, int y, Operation op) {
            if (op == Operation.ADDITION) {
                return x + y;
            }

            if (op == Operation.SUBTRACTION) {
                return x - y;
            }

            throw new IllegalArgumentException("Operation is not supported.");
        }

        private static java.lang.Integer convert(String s) {
            try {
                return java.lang.Integer.valueOf(s);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

}
