package edu.hw7.task3;

public class ReadWriteLockDataBaseTest implements PersonDataBaseTest<ReadWriteLockDataBase> {
    @Override
    public ReadWriteLockDataBase getDataBase() {
        return new ReadWriteLockDataBase();
    }
}
