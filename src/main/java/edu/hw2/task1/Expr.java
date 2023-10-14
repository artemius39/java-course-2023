package edu.hw2.task1;

public sealed interface Expr {
    double evaluate();

    record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }
    }

    record Negate(Expr operand) implements Expr {
        @Override
        public double evaluate() {
            return -operand.evaluate();
        }
    }

    record Addition(Expr leftOperand, Expr rightOperand) implements Expr {
        @Override
        public double evaluate() {
            return leftOperand.evaluate() + rightOperand.evaluate();
        }
    }

    record Multiplication(Expr leftOperand, Expr rightOperand) implements Expr {
        @Override
        public double evaluate() {
            return leftOperand.evaluate() * rightOperand.evaluate();
        }
    }

    record Exponent(Expr leftOperand, Expr rightOperand) implements Expr {
        @Override
        public double evaluate() {
            return Math.pow(leftOperand.evaluate(), rightOperand.evaluate());
        }
    }
}
