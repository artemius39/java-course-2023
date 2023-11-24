package edu.hw7.task3;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDataBase implements PersonDataBase {
    private final Map<Integer, Person> idMap;
    private final ListMultimap<String, Person> nameMap;
    private final ListMultimap<String, Person> addressMap;
    private final ListMultimap<String, Person> phoneNumberMap;

    public BaseDataBase() {
        idMap = new HashMap<>();
        nameMap = ArrayListMultimap.create();
        addressMap = ArrayListMultimap.create();
        phoneNumberMap = ArrayListMultimap.create();
    }

    @Override
    public void add(Person person) {
        idMap.put(person.id(), person);
        nameMap.put(person.name(), person);
        addressMap.put(person.address(), person);
        phoneNumberMap.put(person.phoneNumber(), person);
    }

    @Override
    public void delete(int id) {
        Person person = idMap.remove(id);
        if (person == null) {
            return;
        }

        nameMap.remove(person.name(), person);
        addressMap.remove(person.address(), person);
        phoneNumberMap.remove(person.phoneNumber(), person);
    }

    @Override
    public List<Person> findByName(String name) {
        return nameMap.get(name);
    }

    @Override
    public List<Person> findByPhone(String phoneNumber) {
        return phoneNumberMap.get(phoneNumber);
    }

    @Override
    public List<Person> findByAddress(String address) {
        return addressMap.get(address);
    }
}
