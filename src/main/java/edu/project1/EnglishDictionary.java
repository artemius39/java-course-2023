package edu.project1;

import java.util.List;
import java.util.Random;

public final class EnglishDictionary {
    private static final List<String> WORDS = List.of(
            "hello", "world", "bibaboba", "word", "list", "of", "override", "string", "java", "tea",
            "never", "gonna", "give", "you", "up", "let", "down", "desert"
    );

    private static final Random RANDOM = new Random();

    public static String randomWord() {
        return WORDS.get(RANDOM.nextInt(WORDS.size()));
    }

    private EnglishDictionary() {
    }
}
