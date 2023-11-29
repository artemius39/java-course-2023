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
        Person previous = idMap.putIfAbsent(person.id(), person);
        if (previous != null) {
            throw new IllegalArgumentException("Person with id "  + person.id() + " is already saved");
        }

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
        return List.copyOf(nameMap.get(name));
    }

    @Override
    public List<Person> findByPhone(String phoneNumber) {
        return List.copyOf(phoneNumberMap.get(phoneNumber));
    }

    @Override
    public List<Person> findByAddress(String address) {
        return List.copyOf(addressMap.get(address));
    }
}
