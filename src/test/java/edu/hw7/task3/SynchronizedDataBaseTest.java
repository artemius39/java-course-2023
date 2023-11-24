package edu.hw7.task3;

public class SynchronizedDataBaseTest implements PersonDataBaseTest<SynchronizedDataBase> {
    @Override
    public SynchronizedDataBase getDataBase() {
        return new SynchronizedDataBase();
    }
}
