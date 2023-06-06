package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;
import org.example.utils.Line;

public class Visitor {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        // 1+(2+3)
        AdditionExpression e1 = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(new DoubleExpression(2), new DoubleExpression(3)));
        e1.print(sb);
        System.out.println(AnsiColor.YELLOW + "Intrusive: " + sb);

        Line.split();
        sb.setLength(0);

        ReflectiveAdditionExpression e2 = new ReflectiveAdditionExpression(
                new ReflectiveDoubleExpression(1),
                new ReflectiveAdditionExpression(new ReflectiveDoubleExpression(2), new ReflectiveDoubleExpression(3)));
        ReflectiveExpressionPrinter.print(e2, sb);
        System.out.println(AnsiColor.CYAN + "Reflective: " + sb);

        Line.split();
        sb.setLength(0);

        ClassicAdditionExpression e3 = new ClassicAdditionExpression(
                new ClassicDoubleExpression(1),
                new ClassicAdditionExpression(new ClassicDoubleExpression(2), new ClassicDoubleExpression(3)));

        ClassicExpressionPrinter ep1 = new ClassicExpressionPrinter();
        ep1.visit(e3);

        ExpressionCalculator ec = new ExpressionCalculator();
        ec.visit(e3);

        System.out.println(AnsiColor.PURPLE + "Classic: " + ep1 + " = " + ec.result);

        Line.split();
        sb.setLength(0);

        AcyclicAdditionExpression e4 = new AcyclicAdditionExpression(
                new AcyclicDoubleExpression(1),
                new AcyclicAdditionExpression(new AcyclicDoubleExpression(2), new AcyclicDoubleExpression(3)));

        AcyclicExpressionPrinter ep2 = new AcyclicExpressionPrinter();
        ep2.visit(e4);

        System.out.println(AnsiColor.RED + "Acyclic: " + ep2);

        Line.reset();
    }


    // Intrusive Visitor: Not a good way because we must implement a new method on every single member of a hierarchy.
    static abstract class Expression {
        public abstract void print(StringBuilder sb); // OCP & SRP are broken.
    }

    static class DoubleExpression extends Expression {
        private final double value;

        public DoubleExpression(double value) {
            this.value = value;
        }

        @Override
        public void print(StringBuilder sb) {
            sb.append(value);
        }
    }

    static class AdditionExpression extends Expression {
        private final Expression left, right;

        public AdditionExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void print(StringBuilder sb) {
            sb.append("(");
            left.print(sb);
            sb.append("+");
            right.print(sb);
            sb.append(")");
        }
    }

    // Reflective Visitor: Bad because we check types of instances, which makes operations very slow.
    static abstract class ReflectiveExpression {
    }

    static class ReflectiveDoubleExpression extends ReflectiveExpression {
        public final double value;

        public ReflectiveDoubleExpression(double value) {
            this.value = value;
        }
    }

    static class ReflectiveAdditionExpression extends ReflectiveExpression {
        private final ReflectiveExpression left, right;

        public ReflectiveAdditionExpression(ReflectiveExpression left, ReflectiveExpression right) {
            this.left = left;
            this.right = right;
        }
    }

    static class ReflectiveExpressionPrinter {
        public static void print(ReflectiveExpression e, StringBuilder sb) {
            if (e.getClass() == ReflectiveDoubleExpression.class) {
                sb.append(((ReflectiveDoubleExpression) e).value);
            } else if (e.getClass() == ReflectiveAdditionExpression.class) {
                sb.append("(");
                print(((ReflectiveAdditionExpression) e).left, sb);
                sb.append("+");
                print(((ReflectiveAdditionExpression) e).right, sb);
                sb.append(")");
            }
        }
    }

    // Classic Visitor (Double Dispatch): Pretty good. Jumps to `accept()` implementation and then back to `visit()` implementation. Enforce contract due to `accept()` override and interface definition. Downside is tight coupling.
    interface ExpressionVisitor {
        void visit(ClassicDoubleExpression e);

        void visit(ClassicAdditionExpression e);
    }

    static abstract class ClassicExpression {
        public abstract void accept(ExpressionVisitor visitor);
    }

    static class ClassicDoubleExpression extends ClassicExpression {
        public final double value;

        public ClassicDoubleExpression(double value) {
            this.value = value;
        }

        @Override
        public void accept(ExpressionVisitor visitor) {
            visitor.visit(this);
        }
    }

    static class ClassicAdditionExpression extends ClassicExpression {
        private final ClassicExpression left, right;

        public ClassicAdditionExpression(ClassicExpression left, ClassicExpression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void accept(ExpressionVisitor visitor) {
            visitor.visit(this);
        }
    }

    static class ClassicExpressionPrinter implements ExpressionVisitor {
        private final StringBuilder sb = new StringBuilder();

        @Override
        public void visit(ClassicDoubleExpression e) {
            sb.append(e.value);
        }

        @Override
        public void visit(ClassicAdditionExpression e) {
            sb.append("(");
            e.left.accept(this);
            sb.append("+");
            e.right.accept(this);
            sb.append(")");
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }

    static class ExpressionCalculator implements ExpressionVisitor {
        public double result;

        @Override
        public void visit(ClassicDoubleExpression e) {
            result = e.value;
        }

        @Override
        public void visit(ClassicAdditionExpression e) {
            e.left.accept(this);
            double left = result;
            e.right.accept(this);
            double right = result;
            result = left + right;
        }
    }

    // Acyclic Visitor: ISP compliant. Avoid tight coupling & add more flexibility. Downside is the need to create multiple interfaces and a bit of type checking.
    interface AcyclicVisitor { // Marker interface
    }

    interface AcyclicExpressionVisitor extends AcyclicVisitor {
        void visit(AcyclicExpression obj);
    }

    interface AcyclicDoubleExpressionVisitor extends AcyclicVisitor {
        void visit(AcyclicDoubleExpression obj);
    }

    interface AcyclicAdditionExpressionVisitor extends AcyclicVisitor {
        void visit(AcyclicAdditionExpression obj);
    }

    abstract static class AcyclicExpression {
        // optional
        public void accept(AcyclicVisitor visitor) {
            if (visitor instanceof AcyclicExpressionVisitor) {
                ((AcyclicExpressionVisitor) visitor).visit(this);
            }
        }
    }

    static class AcyclicDoubleExpression extends AcyclicExpression {
        public double value;

        public AcyclicDoubleExpression(double value) {
            this.value = value;
        }

        @Override
        public void accept(AcyclicVisitor visitor) {
            if (visitor instanceof AcyclicDoubleExpressionVisitor) {
                ((AcyclicDoubleExpressionVisitor) visitor).visit(this);
            }
        }
    }

    static class AcyclicAdditionExpression extends AcyclicExpression {
        public AcyclicExpression left, right;

        public AcyclicAdditionExpression(AcyclicExpression left, AcyclicExpression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void accept(AcyclicVisitor visitor) {
            if (visitor instanceof AcyclicAdditionExpressionVisitor) {
                ((AcyclicAdditionExpressionVisitor) visitor).visit(this);
            }
        }
    }

    static class AcyclicExpressionPrinter
            implements AcyclicDoubleExpressionVisitor, AcyclicAdditionExpressionVisitor {
        private final StringBuilder sb = new StringBuilder();

        @Override
        public void visit(AcyclicDoubleExpression obj) {
            sb.append(obj.value);
        }

        @Override
        public void visit(AcyclicAdditionExpression obj) {
            sb.append('(');
            obj.left.accept(this);
            sb.append('+');
            obj.right.accept(this);
            sb.append(')');
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }
}