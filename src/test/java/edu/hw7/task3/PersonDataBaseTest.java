package edu.hw7.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

interface PersonDataBaseTest<T extends PersonDataBase> {
    T getDataBase();

    private void assertConsistentSearch(int id, PersonDataBase dataBase, Thread modifyingThread)
            throws InterruptedException {
        modifyingThread.wait();
        List<Person> byPhoneNumber = List.copyOf(dataBase.findByPhone("phone number" + id));
        List<Person> byAddress = List.copyOf(dataBase.findByAddress("address" + id));
        List<Person> byName = List.copyOf(dataBase.findByName("name" + id));
        modifyingThread.notify();

        if (byPhoneNumber.isEmpty()) {
            assertThat(byName).isEmpty();
            assertThat(byPhoneNumber).isEmpty();
        } else {
            Person person = new Person(
                    id,
                    "name" + id,
                    "address" + id,
                    "phone number" + id
            );
            assertThat(byName).contains(person);
            assertThat(byAddress).contains(person);
            assertThat(byPhoneNumber).contains(person);
        }
    }

    @Test
    @DisplayName("Search mid addition")
    default void searchMidAddition() throws InterruptedException {
        PersonDataBase dataBase = getDataBase();
        int iterationCount = 1_000_000;
        Thread thread = new Thread(() -> {
            for (int id = 0; id < iterationCount; id++) {
                dataBase.add(new Person(
                        id,
                        "name" + id,
                        "address" + id,
                        "phone number" + id
                ));
            }
        });
        thread.start();

        for (int id = 0; id < iterationCount; id++) {
            assertConsistentSearch(id, dataBase, thread);
        }
    }

    @Test
    @DisplayName("Search mid removal")
    default void searchMidRemoval() throws InterruptedException {
        PersonDataBase dataBase = getDataBase();
        int iterationCount = 1_000_000;
        for (int id = 0; id < iterationCount; id++) {
            dataBase.add(new Person(
                    id,
                    "name" + id,
                    "address" + id,
                    "phone number" + id
            ));
        }
        Thread thread = new Thread(() -> {
            for (int id = 0; id < iterationCount; id++) {
                dataBase.delete(id);
            }
        });
        thread.start();

        for (int id = 0; id < iterationCount; id++) {
            assertConsistentSearch(id, dataBase, thread);
        }
    }
}
