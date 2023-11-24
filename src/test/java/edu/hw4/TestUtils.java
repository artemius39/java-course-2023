package edu.hw4;

import org.junit.jupiter.params.provider.Arguments;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class TestUtils {

    public static Stream<Arguments> collections(List<Animal> animals) {
        return Stream.of(
                Arguments.of(animals),
                Arguments.of(new ArrayList<>(animals)),
                Arguments.of(new LinkedList<>(animals)),
                Arguments.of(new ArrayDeque<>(animals))
        );
    }

    public static Stream<Arguments> collections(Animal... animals) {
        return collections(List.of(animals));
    }

    public static Animal withName(String name) {
        return new Animal(name, null, null, 0, 0, 0, true);
    }

    public static List<Animal> withNames(String... names) {
        return Stream.of(names).map(TestUtils::withName).toList();
    }

    public static Animal withType(Animal.Type type) {
        return new Animal(null, type, null, 0, 0, 0, false);
    }

    public static List<Animal> withTypes(Animal.Type... types) {
        return Stream.of(types).map(TestUtils::withType).toList();
    }

    public static Animal withTypeAndWeightAndName(Animal.Type type, int weight, String name) {
        return new Animal(name, type, null, 0, 0, weight, true);
    }

    public static Animal withTypeAndWeight(Animal.Type type, int weight) {
        return withTypeAndWeightAndName(type, weight, null);
    }

    public static Animal withAge(int age) {
        return new Animal(null, null, null, age, 0, 0, true);
    }

    public static List<Animal> withAges(int... ages) {
        return IntStream.of(ages)
                .mapToObj(TestUtils::withAge)
                .toList();
    }

    public static Animal withSex(Animal.Sex sex) {
        return new Animal(null, null, sex, 0, 0, 0, false);
    }

    public static List<Animal> withSexes(Animal.Sex... sexes) {
        return Stream.of(sexes)
                .map(TestUtils::withSex)
                .toList();
    }

    public static Animal withWeight(int weight) {
        return new Animal(null, null, null, 0, 0, weight, true);
    }

    public static List<Animal> withWeights(int... weights) {
        return IntStream.of(weights)
                .mapToObj(TestUtils::withWeight)
                .toList();
    }

    public static Animal withHeight(int height) {
        return new Animal(null, null, null, 0, height, 0, false);
    }

    public static List<Animal> withHeights(int... heights) {
        return IntStream.of(heights)
                .mapToObj(TestUtils::withHeight)
                .toList();
    }

    public static Animal withAgeAndType(int age, Animal.Type type) {
        return new Animal(null, type, null, age, 0, 0, false);
    }

    public static Animal withBitesAndHeight(boolean bites, int height) {
        return new Animal(null, null, null, 0, height, 0, bites);
    }

    public static Animal withWeightAndHeight(int weight, int height) {
        return new Animal(null, null, null, 0, height, weight, false);
    }

    public static Animal withHeightAndType(int height, Animal.Type type) {
        return new Animal(null, type, null, 0, height, 0, true);
    }

    public static Animal withTypeAndSex(Animal.Type type, Animal.Sex sex) {
        return new Animal(null, type, sex, 0, 0, 0, true);
    }

    public static Animal withTypeAndSexAndName(Animal.Type type, Animal.Sex sex, String name) {
        return new Animal(name, type, sex, 0, 0, 0, true);
    }

    public static Animal withBitesAndType(boolean bites, Animal.Type type) {
        return new Animal(null, type, null, 0, 0, 0, bites);
    }

    private TestUtils() {
    }
}
