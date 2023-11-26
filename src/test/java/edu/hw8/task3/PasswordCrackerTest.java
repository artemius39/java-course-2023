package edu.hw8.task3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

interface PasswordCrackerTest {
    List<Character> DIGITS = IntStream.rangeClosed('0', '9').mapToObj(ch -> (char) ch).toList();
    List<Character> LOWERCASE_LETTERS = IntStream.rangeClosed('a', 'z').mapToObj(ch -> (char) ch).toList();
    List<Character> UPPERCASE_LETTERS = IntStream.rangeClosed('A', 'Z').mapToObj(ch -> (char) ch).toList();
    List<Character> ALL_CHARACTERS =
            Stream.of(DIGITS, LOWERCASE_LETTERS, UPPERCASE_LETTERS).flatMap(Collection::stream).toList();

    private String hash(String password) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append("%02x".formatted(b));
        }
        return sb.toString();
    }

    PasswordCracker getPasswordCracker(List<Character> alphabet);

    @Test
    @DisplayName("digits")
    default void digits() {
        PasswordCracker passwordCracker = getPasswordCracker(DIGITS);
        Map<String, String> database = Map.of(hash("123456"), "user");

        Map<String, String> crackedDatabase = passwordCracker.crack(database, 6);

        assertThat(crackedDatabase).containsEntry("user", "123456");
    }

    @Test
    @DisplayName("Lowercase letters")
    default void lowercaseLetters() {
        PasswordCracker passwordCracker = getPasswordCracker(LOWERCASE_LETTERS);
        Map<String, String> database = Map.of(hash("abcd"), "user");

        Map<String, String> crackedDatabase = passwordCracker.crack(database, 4);

        assertThat(crackedDatabase).containsEntry("user", "abcd");
    }

    @Test
    @DisplayName("Uppercase letters")
    default void uppercaseLetters() {
        PasswordCracker passwordCracker = getPasswordCracker(UPPERCASE_LETTERS);
        Map<String, String> database = Map.of(hash("ABCD"), "user");

        Map<String, String> crackedDatabase = passwordCracker.crack(database, 4);

        assertThat(crackedDatabase).containsEntry("user", "ABCD");
    }

    @Test
    @DisplayName("Lowercase, uppercase and digits")
    default void lowercaseUppercaseAndDigits() {
        PasswordCracker passwordCracker = getPasswordCracker(ALL_CHARACTERS);
        Map<String, String> database = Map.of(hash("aB1"), "user");

        Map<String, String> crackedDatabase = passwordCracker.crack(database, 3);

        assertThat(crackedDatabase).containsEntry("user", "aB1");
    }

    @Test
    @DisplayName("Empty password")
    default void emptyPassword() {
        PasswordCracker passwordCracker = getPasswordCracker(ALL_CHARACTERS);
        Map<String, String> database = Map.of(hash(""), "user");

        Map<String, String> crackedDatabase = passwordCracker.crack(database, 3);

        assertThat(crackedDatabase).containsEntry("user", "");
    }

    @Test
    @DisplayName("Last password")
    default void lastPassword() {
        PasswordCracker passwordCracker = getPasswordCracker(DIGITS);
        Map<String, String> database = Map.of(hash("9999"), "user");

        Map<String, String> crackedDatabase = passwordCracker.crack(database, 4);

        assertThat(crackedDatabase).containsEntry("user", "9999");
    }
}
