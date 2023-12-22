package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArithmeticUtilsInterceptorTest {
    @Test
    @DisplayName("Sum interception")
    void sumReturnsProduct()
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ByteBuddyAgent.install();
        try (var sum = new ByteBuddy()
                .redefine(ArithmeticUtils.class)
                .method(ElementMatchers.named("sum"))
                .intercept(MethodDelegation.to(ArithmeticUtilsDelegate.class))
                .make()) {
            sum.load(ClassLoader.getSystemClassLoader(), ClassReloadingStrategy.fromInstalledAgent())
                    .getLoaded()
                    .getConstructor()
                    .newInstance();
            ArithmeticUtils arithmeticUtils = new ArithmeticUtils();
            assertThat(arithmeticUtils.sum(3, 3)).isEqualTo(9);
        }
    }
}
