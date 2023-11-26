package edu.hw8.task3;

import java.util.List;

class MultiThreadedPasswordCrackerTest implements PasswordCrackerTest {
    @Override
    public PasswordCracker getPasswordCracker(List<Character> alphabet) {
        return new MultiThreadedPasswordCracker(alphabet);
    }
}
