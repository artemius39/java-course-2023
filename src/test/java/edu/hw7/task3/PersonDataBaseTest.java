package edu.hw7.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

interface PersonDataBaseTest<T extends PersonDataBase> {
    T getDataBase();

    @Test
    @DisplayName("Search mid addition")
    default void searchMidAddition() {
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
            List<Person> byPhoneNumber = dataBase.findByPhone("phone number" + id);
            List<Person> byAddress = dataBase.findByAddress("address" + id);
            List<Person> byName = dataBase.findByName("name" + id);

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
    }

    @Test
    @DisplayName("Search mid removal")
    default void searchMidRemoval() {
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
        new Thread(() -> {
            for (int id = 0; id < iterationCount; id++) {
                dataBase.delete(id);
            }
        }).start();

        for (int id = 0; id < iterationCount; id++) {
            List<Person> byPhoneNumber = dataBase.findByPhone("phone number" + id);
            List<Person> byAddress = dataBase.findByAddress("address" + id);
            List<Person> byName = dataBase.findByName("name" + id);

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
    }
}
