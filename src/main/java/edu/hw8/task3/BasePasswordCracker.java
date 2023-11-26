package edu.hw8.task3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class BasePasswordCracker implements PasswordCracker {
    protected final List<Character> alphabet;

    BasePasswordCracker(List<Character> alphabet) {
        this.alphabet = alphabet;
    }

    protected Map<Hash, String> hashDatabase(Map<String, String> database) {
        return database.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> new Hash(entry.getKey()), Map.Entry::getValue));
    }

    protected long passwordsWithLengthUpTo(int length) {
        return (long) Math.pow(alphabet.size(), length + 1) / (alphabet.size() - 1);
    }

    protected Map<String, String> crackImpl(PasswordGenerator generator, Map<String, String> database) {
        Map<String, String> crackedDatabase = new HashMap<>();
        Map<Hash, String> hashDatabase = hashDatabase(database);
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        while (generator.hasNextPassword()) {
            String password = generator.nextPassword();
            Hash hash = new Hash(md5.digest(password.getBytes()));
            String user = hashDatabase.get(hash);
            if (user != null) {
                crackedDatabase.put(user, password);
            }
        }

        return crackedDatabase;
    }
}
