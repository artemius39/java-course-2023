package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;
import static edu.hw4.Animals.heaviestFish;
import static edu.hw4.TestUtils.withTypeAndWeight;
import static edu.hw4.TestUtils.withTypes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeaviestFishTest {
    @Test
    @DisplayName("No fish")
    void noFish() {
        assertThrows(
                NoSuchElementException.class,
                () -> heaviestFish(
                        withTypes(Animal.Type.CAT, Animal.Type.DOG),
                        withTypes(Animal.Type.SPIDER, Animal.Type.BIRD),
                        withTypes(Animal.Type.CAT, Animal.Type.BIRD)
                )
        );
    }

    @Test
    @DisplayName("No lists")
    void noLists() {
        assertThrows(NoSuchElementException.class, Animals::heaviestFish);
    }

    @Test
    @DisplayName("Heaviest is in first list")
    void heaviestIsInFirstList() {
        Animal actual = heaviestFish(
                List.of(
                        withTypeAndWeight(Animal.Type.FISH, 1000),
                        withTypeAndWeight(Animal.Type.CAT, 10),
                        withTypeAndWeight(Animal.Type.BIRD, 100)
                ),
                List.of(
                        withTypeAndWeight(Animal.Type.FISH, 256)
                )
        );

        assertThat(actual).isEqualTo(withTypeAndWeight(Animal.Type.FISH, 1000));
    }

    @Test
    @DisplayName("Heaviest is in other list")
    void heaviestIsInOtherList() {
        Animal actual = heaviestFish(
                List.of(
                        withTypeAndWeight(Animal.Type.FISH, 1000),
                        withTypeAndWeight(Animal.Type.CAT, 10),
                        withTypeAndWeight(Animal.Type.BIRD, 100)
                ),
                List.of(
                        withTypeAndWeight(Animal.Type.FISH, 256),
                        withTypeAndWeight(Animal.Type.FISH, 10000)
                ),
                List.of(
                        withTypeAndWeight(Animal.Type.BIRD, 256),
                        withTypeAndWeight(Animal.Type.SPIDER, 100)
                )
        );

        assertThat(actual).isEqualTo(withTypeAndWeight(Animal.Type.FISH, 10000));
    }

    @Test
    @DisplayName("Fish is not the heaviest animal")
    void fishIsNotTheHeaviestAnimal() {
        Animal actual = heaviestFish(
                List.of(
                        withTypeAndWeight(Animal.Type.FISH, 256)
                ),
                List.of(
                        withTypeAndWeight(Animal.Type.FISH, 1000),
                        withTypeAndWeight(Animal.Type.CAT, 10000000),
                        withTypeAndWeight(Animal.Type.BIRD, 100)
                )
        );

        assertThat(actual).isEqualTo(withTypeAndWeight(Animal.Type.FISH, 1000));
    }
}
