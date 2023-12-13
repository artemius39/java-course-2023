package edu.hw10.task1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class RandomObjectGenerator {
    private static final Map<Class<?>, Supplier<?>> PRIMITIVE_GENERATORS = Map.of(
            boolean.class, () -> ThreadLocalRandom.current().nextBoolean(),
            long.class, () -> ThreadLocalRandom.current().nextLong(),
            int.class, () -> ThreadLocalRandom.current().nextInt(),
            short.class, () -> (short) ThreadLocalRandom.current().nextInt(Short.MIN_VALUE, ((int) Short.MAX_VALUE) + 1),
            byte.class, () -> (byte) ThreadLocalRandom.current().nextInt(Byte.MIN_VALUE, ((int) Byte.MAX_VALUE) + 1),
            char.class, () -> (char) ThreadLocalRandom.current().nextInt(Character.MIN_VALUE, ((int) Character.MAX_VALUE) + 1),
            double.class, () -> ThreadLocalRandom.current().nextDouble(),
            float.class, () -> ThreadLocalRandom.current().nextFloat()
    );
    private static final int MAX_ARRAY_SIZE = 100;

    private Optional<?> generateBasicType(Class<?> clazz, Annotation[] annotations) {
        if (clazz.equals(int.class)) {
            int min = Integer.MIN_VALUE;
            int max = Integer.MAX_VALUE;
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Min.class)) {
                    min = ((Min) annotation).value();
                } else if (annotation.annotationType().equals(Max.class)) {
                    max = ((Max) annotation).value();
                }
            }
            return Optional.of(ThreadLocalRandom.current().nextInt(min, max));
        }
        if (clazz.isPrimitive()) {
            return Optional.of(PRIMITIVE_GENERATORS.get(clazz).get());
        }
        if (clazz.isArray()) {
            int length = ThreadLocalRandom.current().nextInt(MAX_ARRAY_SIZE);
            Class<?> componentType = clazz.getComponentType();
            Object array = Array.newInstance(componentType, length);
            for (int i = 0; i < length; i++) {
                Array.set(array, i, nextObject(componentType));
            }
            return Optional.of(array);
        }
        return Optional.empty();
    }

    public <T> T nextObject(Class<T> clazz) {
        T result = null;
        while (result == null) {
            result = nextObjectImpl(clazz, new Annotation[0]);
        }
        return result;
    }

    public <T> T nextObject(Class<T> clazz, String factoryMethodName) {
        Method factory = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().equals(factoryMethodName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Could not find factory method '" + factoryMethodName + "' in class '" + clazz.getName() + "'"
                ));

        Object[] parameters = getParameters(factory);
        factory.setAccessible(true);
        try {
            //noinspection unchecked
            return (T) factory.invoke(null, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to generate instance of class " + clazz.getName(), e);
        }
    }

    private <T> T nextObjectImpl(Class<T> clazz, Annotation[] annotations) {
        Optional<?> optionalObject = generateBasicType(clazz, annotations);
        if (optionalObject.isPresent()) {
            //noinspection unchecked
            return (T) optionalObject.get();
        }
        if (Arrays.stream(annotations).noneMatch(annotation -> annotation.annotationType().equals(NotNull.class))) {
            if (ThreadLocalRandom.current().nextInt(100) == 0) {
                return null;
            }
        }

        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            constructors = clazz.getDeclaredConstructors();
        }
        if (constructors.length == 0) {
            throw new RuntimeException("Unsupported class: " + clazz.getName());
        }
        Constructor<?> constructor = constructors[0];

        Object[] parameters = getParameters(constructor);
        constructor.setAccessible(true);
        try {
            //noinspection unchecked
            return (T) constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to generate instance of class " + clazz.getName(), e);
        }
    }

    private Object[] getParameters(Executable executable) {
        Parameter[] parameters = executable.getParameters();
        Object[] arguments = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            arguments[i] = nextObjectImpl(parameter.getType(), parameter.getAnnotations());
        }
        return arguments;
    }
}
