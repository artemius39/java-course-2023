package edu.hw4;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Animals {

    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String TYPE = "type";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String SEX = "sex";

    private static <T> Collector<T, ?, Integer> countingInt() {
        return Collectors.summingInt(e -> 1);
    }

    public static List<Animal> sortByHeight(Collection<Animal> animals) {
        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::height))
                .toList();
    }

    public static List<Animal> pickHeaviestAnimals(Collection<Animal> animals, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("pick count cannot be negative");
        }
        if (count == 0) {
            return List.of();
        }

        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::weight).reversed())
                .limit(count)
                .toList();
    }

    public static Map<Animal.Type, Integer> countByType(Collection<Animal> animals) {
        Map<Animal.Type, Integer> map = animals.stream()
                .collect(Collectors.groupingBy(
                        Animal::type,
                        () -> new EnumMap<>(Animal.Type.class),
                        countingInt()
                ));
        for (Animal.Type type : Animal.Type.values()) {
            map.putIfAbsent(type, 0);
        }
        return map;
    }

    public static Animal animalWithLongestName(Collection<Animal> animals) {
        return animals.stream()
                .max(Comparator.comparingInt(animal -> animal.name().length()))
                .orElseThrow();
    }

    public static Animal.Sex mostNumerousSex(Collection<Animal> animals) {
        return animals.stream()
                .collect(Collectors.groupingBy(Animal::sex, countingInt()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    public static Map<Animal.Type, Animal> heaviestAnimalByType(Collection<Animal> animals) {
        return animals.stream()
                .collect(Collectors.groupingBy(
                        Animal::type,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Animal::weight)),
                                Optional::orElseThrow
                        )
                ));
    }

    public static Animal kthOldestAnimal(Collection<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }

        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::age).reversed())
                .skip(k - 1)
                .findFirst()
                .orElseThrow();
    }

    public static int totalPaws(Collection<Animal> animals) {
        return animals.stream()
                .mapToInt(Animal::paws)
                .sum();
    }

    public static List<Animal> ageDiffersFromPawCount(Collection<Animal> animals) {
        return animals.stream()
                .filter(animal -> animal.age() != animal.paws())
                .toList();
    }

    public static List<Animal> animalsThatBiteAndAreTallerThan100cm(Collection<Animal> animals) {
        final int MIN_HEIGHT = 100;
        return animals.stream()
                .filter(Animal::bites)
                .filter(animal -> animal.height() > MIN_HEIGHT)
                .toList();
    }

    public static int weightIsGreaterThanHeight(Collection<Animal> animals) {
        return animals.stream()
                .filter(animal -> animal.weight() > animal.height())
                .collect(countingInt());
    }

    public static List<Animal> animalsWithNamesLongerThanTwoWords(Collection<Animal> animals) {
        return animals.stream()
                .filter(animal -> animal.name()
                                          .trim()
                                          .split("\\s+")
                                          .length > 2
                ).toList();
    }

    public static boolean containsDogTallerThan(Collection<Animal> animals, int bound) {
        return animals.stream()
                .filter(animal -> animal.type() == Animal.Type.DOG)
                .anyMatch(animal -> animal.height() > bound);
    }

    public static Map<Animal.Type, Integer> totalWeightOfAnimalsWithWeightBetweenByType(Collection<Animal> animals,
                                                                                        int lowerWeightBound,
                                                                                        int upperWeightBound) {

        Map<Animal.Type, Integer> map = animals.stream()
                .filter(animal -> animal.weight() >= lowerWeightBound)
                .filter(animal -> animal.weight() <= upperWeightBound)
                .collect(Collectors.groupingBy(
                        Animal::type,
                        () -> new EnumMap<>(Animal.Type.class),
                        Collectors.summingInt(Animal::weight)
                ));
        for (Animal.Type value : Animal.Type.values()) {
            map.putIfAbsent(value, 0);
        }
        return map;
    }

    public static List<Animal> sortByTypeSexName(Collection<Animal> animals) {
        return animals.stream()
                .sorted(Comparator.comparing(Animal::type)
                        .thenComparing(Animal::sex)
                        .thenComparing(Animal::name)
                ).toList();
    }

    public static boolean spidersBiteMoreThanDogs(Collection<Animal> animals) {
        Map<Animal.Type, Integer> count = countByType(animals);

        long totalDogs = count.get(Animal.Type.DOG);
        if (totalDogs == 0) {
            return false;
        }
        long totalSpiders = count.get(Animal.Type.SPIDER);
        if (totalSpiders == 0) {
            return false;
        }

        long dogsThatBite = countBitingOfType(animals, Animal.Type.DOG);
        long spidersThatBite = countBitingOfType(animals, Animal.Type.SPIDER);

        return spidersThatBite * totalDogs > dogsThatBite * totalSpiders;
    }

    private static long countBitingOfType(Collection<Animal> animals, Animal.Type dog) {
        return animals.stream()
                .filter(animal -> animal.type() == dog)
                .filter(Animal::bites)
                .count();
    }

    @SafeVarargs
    public static Animal heaviestFish(List<Animal>... lists) {
        return Stream.of(lists)
                .flatMap(Collection::stream)
                .filter(animal -> animal.type() == Animal.Type.FISH)
                .max(Comparator.comparingInt(Animal::weight))
                .orElseThrow();
    }

    public static Map<String, Set<ValidationError>> validate(Collection<Animal> animals) {
        return animals.stream()
                .map(animal -> new AbstractMap.SimpleEntry<>(animal.name(), Animals.validateAnimal(animal)))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.reducing(
                                new HashSet<>(),
                                Map.Entry::getValue,
                                (set1, set2) -> {
                                    set1.addAll(set2);
                                    return set1;
                                }
                        )
                ));
    }

    private static Set<ValidationError> validateAnimal(Animal animal) {
        Set<ValidationError> errors = new HashSet<>();

        if (animal.age() < 0) {
            errors.add(new ValidationError("age cannot be negative", AGE));
        }

        if (animal.name() == null) {
            errors.add(new ValidationError("name cannot be null", NAME));
        } else if (animal.name().isBlank()) {
            errors.add(new ValidationError("name cannot be blank", NAME));
        }

        if (animal.type() == null) {
            errors.add(new ValidationError("animal type cannot be null", TYPE));
        }

        if (animal.height() <= 0) {
            errors.add(new ValidationError("height must be positive", HEIGHT));
        }

        if (animal.weight() <= 0) {
            errors.add(new ValidationError("weight must be positive", WEIGHT));
        }

        if (animal.sex() == null) {
            errors.add(new ValidationError("sex cannot be null", SEX));
        }

        return errors;
    }

    public static Map<String, String> validateJoining(Collection<Animal> animals) {
        return validate(animals)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .map(ValidationError::fieldName)
                                .collect(Collectors.joining(", "))
                ));
    }

    private Animals() {
    }
}
