package edu.hw2.task1;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.BinaryOperator;

import static edu.hw2.task1.Expr.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ExprTest {
    private static final Offset<Double> PRECISION = Assertions.offset(1e-12);

    private void testBinaryOperation(Expr leftOperand, Expr rightOperand, BinaryOperator<Expr> operationConstructor, BinaryOperator<Double> operation) {
        double left = leftOperand.evaluate();
        double right = rightOperand.evaluate();
        Expr expr = operationConstructor.apply(leftOperand, rightOperand);

        double expected = operation.apply(left, right);
        double actual = expr.evaluate();

        assertThat(actual).isCloseTo(expected, PRECISION);
    }

    static Arguments[] constants() {
        return new Arguments[]{
                Arguments.of(new Constant(1), 1),
                Arguments.of(new Constant(0), 0),
                Arguments.of(new Constant(-1), -1),
                Arguments.of(new Constant(Math.PI), Math.PI)
        };
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Constants")
    void constants(Constant constant, double value) {
        double actual = constant.evaluate();

        assertThat(value).isEqualTo(actual);
    }

    static Arguments[] operands() {
        return new Arguments[]{
                Arguments.of(new Constant(1), new Constant(2)),
                Arguments.of(new Constant(0.1), new Constant(0.2))
        };
    }

    @ParameterizedTest
    @MethodSource("operands")
    @DisplayName("Addition")
    void addition(Expr leftOperand, Expr rightOperand) {
        testBinaryOperation(leftOperand, rightOperand, Addition::new, Double::sum);
    }

    @ParameterizedTest
    @MethodSource("operands")
    @DisplayName("Multiplication")
    void multiplication(Expr leftOperand, Expr rightOperand) {
        testBinaryOperation(leftOperand, rightOperand, Multiplication::new, (a, b) -> a * b);
    }

    @ParameterizedTest
    @MethodSource("operands")
    @DisplayName("Exponentiation")
    void exponentiation(Expr leftOperand, Expr rightOperand) {
        testBinaryOperation(leftOperand, rightOperand, Exponent::new, Math::pow);
    }

    static Arguments[] negation() {
        return new Arguments[]{
                Arguments.of(new Constant(1)),
                Arguments.of(new Constant(0)),
                Arguments.of(new Constant(0.123)),
        };
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Negation")
    void negation(Expr operand) {
        double value = operand.evaluate();
        Expr expr = new Negate(operand);

        double expected = -value;
        double actual = expr.evaluate();

        assertThat(actual).isCloseTo(expected, PRECISION);
    }

    @Test
    @DisplayName("Complex expression")
    void complex() {
        Expr expression =
                new Addition(
                        new Exponent(
                                new Multiplication(
                                        new Addition(
                                                new Constant(2),
                                                new Constant(4)
                                        ),
                                        new Negate(new Constant(1))
                                ),
                                new Constant(2)
                        ),
                        new Constant(1)
                );

        double expected = Math.pow((2 + 4) * -1, 2) + 1;
        double actual = expression.evaluate();

        assertThat(expected).isCloseTo(actual, PRECISION);
    }
}
