package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloByteBuddyTest {
    @Test
    @DisplayName("Hello, ByteBuddy")
    void helloByteBuddy()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (DynamicType.Unloaded<Object> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello, ByteBuddy!"))
                .make()) {
            Class<?> clazz = dynamicType.load(getClass().getClassLoader())
                    .getLoaded();
            Object object = clazz.getConstructor().newInstance();
            assertThat(object.toString()).isEqualTo("Hello, ByteBuddy!");
        }
    }
}
