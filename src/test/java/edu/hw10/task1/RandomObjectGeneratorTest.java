package edu.hw10.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class RandomObjectGeneratorTest {
    private record SimpleRecord(int x, double y) {
    }

    @SuppressWarnings({"unused", "FieldMayBeFinal", "FieldCanBeLocal"})
    private static class SimplePOJO {
        private int x;
        private float y;
        private boolean z;

        public SimplePOJO(int x, float y, boolean z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    @SuppressWarnings("unused")
    private static class ComplexClass {
        SimplePOJO pojo;
        SimpleRecord simpleRecord;
        final int[] x;
        ComplexClass complexClass = null;

        public ComplexClass(SimplePOJO pojo, SimpleRecord simpleRecord, int[] x) {
            this.pojo = pojo;
            this.simpleRecord = simpleRecord;
            this.x = x;
        }
    }

    @SuppressWarnings("unused")
    private static class FactoryClass {
        int x;
        boolean[] y;
        SimpleRecord simpleRecord;
        SimplePOJO[] simplePOJOs;

        private FactoryClass(int x, boolean[] y, SimpleRecord simpleRecord, SimplePOJO[] simplePOJOs) {
            this.x = x;
            this.y = y;
            this.simpleRecord = simpleRecord;
            this.simplePOJOs = simplePOJOs;
        }

        public static FactoryClass create(int x, boolean[] y, SimpleRecord simpleRecord, SimplePOJO[] simplePOJOs) {
            return new FactoryClass(x, y, simpleRecord, simplePOJOs);
        }
    }

    @SuppressWarnings("unused")
    private static class ConstructorWithAnnotations {
        int x;
        int y;
        SimpleRecord simpleRecord;

        public ConstructorWithAnnotations(@Min(10) int x, @Max(100) int y, @NotNull SimpleRecord simpleRecord) {
            this.x = x;
            this.y = y;
            this.simpleRecord = simpleRecord;
        }
    }

    @SuppressWarnings("unused")
    private static class FactoryWithAnnotations {
        int x;
        int y;
        SimplePOJO simplePOJO;

        private FactoryWithAnnotations(@Min(1) int x, @Max(20) int y, @NotNull SimplePOJO simplePOJO) {
            this.x = x;
            this.y = y;
            this.simplePOJO = simplePOJO;
        }

        public static FactoryWithAnnotations create(@Min(1) int x, @Max(20) int y, @NotNull SimplePOJO simplePOJO) {
            return new FactoryWithAnnotations(x, y, simplePOJO);
        }
    }

    @ParameterizedTest
    @ValueSource(classes =
            {int.class, long.class, short.class, byte.class, double.class, float.class, boolean.class, char.class})
    @DisplayName("Primitives")
    void primitives(Class<?> clazz) {
        assertThatNoException().isThrownBy(() -> new RandomObjectGenerator().nextObject(clazz));
    }

    @ParameterizedTest
    @ValueSource(classes =
            {int[].class, long[].class, short[].class, byte[].class, double[].class, float[].class, boolean[].class, char[].class})
    @DisplayName("Primitive arrays")
    void primitiveArrays(Class<?> clazz) {
        assertThatNoException().isThrownBy(() -> new RandomObjectGenerator().nextObject(clazz));
    }

    @Test
    @DisplayName("Simple record")
    void simpleRecord() {
        assertThatNoException().isThrownBy(() -> new RandomObjectGenerator().nextObject(SimpleRecord.class));
    }

    @Test
    @DisplayName("Simple POJO")
    void simplePOJO() {
        assertThatNoException().isThrownBy(() -> new RandomObjectGenerator().nextObject(SimplePOJO.class));
    }

    @Test
    @DisplayName("Complex object")
    void complexObject() {
        assertThatNoException().isThrownBy(() -> new RandomObjectGenerator().nextObject(ComplexClass.class));
    }

    @Test
    @DisplayName("Complex object array")
    void complexObjectArray() {
        assertThatNoException().isThrownBy(() -> new RandomObjectGenerator().nextObject(ComplexClass[].class));
    }

    @Test
    @DisplayName("Class with factory method")
    void classWithFactoryMethod() {
        assertThatNoException().isThrownBy(() -> new RandomObjectGenerator().nextObject(FactoryClass.class, "create"));
    }

    @Test
    @DisplayName("Class with annotated constructor parameters")
    void classWithAnnotatedConstructorParameters() {
        ConstructorWithAnnotations obj = new RandomObjectGenerator().nextObject(ConstructorWithAnnotations.class);

        assertThat(obj.x).isGreaterThanOrEqualTo(10);
        assertThat(obj.y).isLessThanOrEqualTo(100);
        assertThat(obj.simpleRecord).isNotNull();
    }

    @Test
    @DisplayName("Class with annotated factory parameters")
    void classWithAnnotatedFactoryParameters() {
        FactoryWithAnnotations obj = new RandomObjectGenerator().nextObject(FactoryWithAnnotations.class, "create");

        assertThat(obj.x).isGreaterThanOrEqualTo(1);
        assertThat(obj.y).isLessThanOrEqualTo(20);
        assertThat(obj.simplePOJO).isNotNull();
    }
}