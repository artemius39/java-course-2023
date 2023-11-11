package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw5.RegularExpressions.RUSSIAN_LICENCE_PLATE;
import static org.assertj.core.api.Assertions.assertThat;

class RussianLicencePlateTest {
    @ParameterizedTest
    @DisplayName("Valid plates")
    @ValueSource(strings = {"А123ВЕ21", "У001ЕР01", "В100СТ100"})
    void validPlates(String plate) {
        assertThat(plate).matches(RUSSIAN_LICENCE_PLATE);
    }

    @Test
    @DisplayName("Three zeros in the middle")
    void threeZerosInTheMiddle() {
        assertThat("А000ВЕ21").doesNotMatch(RUSSIAN_LICENCE_PLATE);
    }

    @ParameterizedTest
    @DisplayName("Invalid digit count in the middle")
    @ValueSource(strings = {"А1234УО77", "Е12ЕЕ199"})
    void invalidDigitCountInTheMiddle(String licencePlate) {
        assertThat(licencePlate).doesNotMatch(RUSSIAN_LICENCE_PLATE);
    }

    @Test
    @DisplayName("Three-digit region code starts with zero")
    void threeDigitRegionCodeStartsWithZero() {
        assertThat("Х123ХХ012").doesNotMatch(RUSSIAN_LICENCE_PLATE);
    }

    @Test
    @DisplayName("Zero region code")
    void zeroRegionCode() {
        assertThat("А123ВС00").doesNotMatch(RUSSIAN_LICENCE_PLATE);
    }

    @Test
    @DisplayName("Missing letters on the left")
    void missingLettersOnTHeLeft() {
        assertThat("123АУ23").doesNotMatch(RUSSIAN_LICENCE_PLATE);
    }

    @ParameterizedTest
    @DisplayName("Missing letters on the right")
    @ValueSource(strings = {"А12312", "В123Р12"})
    void missingLettersOnTHeRight(String licencePlate) {
        assertThat(licencePlate).doesNotMatch(RUSSIAN_LICENCE_PLATE);
    }

    @Test
    @DisplayName("Lowercase letters")
    void lowercaseLetters() {
        assertThat("а123уе13").doesNotMatch(RUSSIAN_LICENCE_PLATE);
    }

    @Test
    @DisplayName("Letters that do not have similar latin counterparts")
    void lettersThatDoNotHaveSimilarLatinCounterparts() {
        assertThat("А123ВГ77").doesNotMatch(RUSSIAN_LICENCE_PLATE);
    }
}
