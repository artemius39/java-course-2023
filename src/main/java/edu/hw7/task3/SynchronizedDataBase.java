package edu.hw7.task3;

import java.util.List;

public class SynchronizedDataBase extends BaseDataBase {
    @Override
    public synchronized void add(Person person) {
        super.add(person);
    }

    @Override
    public synchronized void delete(int id) {
        super.delete(id);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return super.findByName(name);
    }

    @Override
    public synchronized List<Person> findByPhone(String phoneNumber) {
        return super.findByPhone(phoneNumber);
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return super.findByAddress(address);
    }
}
