package edu.hw8.task3;

import java.util.List;
import java.util.Map;

public class SingleThreadedPasswordCracker extends BasePasswordCracker {
    public SingleThreadedPasswordCracker(List<Character> alphabet) {
        super(alphabet);
    }

    @Override
    public Map<String, String> crack(Map<String, String> database, int maxLength) {
        PasswordGenerator generator = new BruteForcePasswordGenerator(alphabet, passwordsWithLengthUpTo(maxLength));
        return crackImpl(generator, database);
    }
}
