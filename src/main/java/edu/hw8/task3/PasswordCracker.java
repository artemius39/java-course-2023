package edu.hw8.task3;

import java.util.Map;

public interface PasswordCracker {
    Map<String, String> crack(Map<String, String> database, int maxLength);
}
