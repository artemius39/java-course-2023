package edu.hw3.task5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public final class Task5 {
    private static final Comparator<Contact> CONTACT_COMPARATOR = Comparator.comparing(
            contact -> contact.lastName() == null ? contact.firstName() : contact.lastName(),
            String.CASE_INSENSITIVE_ORDER
    );
    private static final Comparator<Contact> REVERSE_CONTACT_COMPARATOR = CONTACT_COMPARATOR.reversed();

    public static Contact[] parseContacts(String[] rawContacts, SortOrder sortOrder) {
        Objects.requireNonNull(sortOrder);
        if (rawContacts == null) {
            return new Contact[0];
        }

        Contact[] contacts = new Contact[rawContacts.length];
        for (int i = 0; i < rawContacts.length; i++) {
            contacts[i] = parseContact(rawContacts[i]);
        }
        Arrays.sort(contacts, sortOrder == SortOrder.ASCENDING ? CONTACT_COMPARATOR : REVERSE_CONTACT_COMPARATOR);

        return contacts;
    }

    private static Contact parseContact(String rawContact) {
        Objects.requireNonNull(rawContact, "Name cannot be null");
        if (rawContact.endsWith(" ")) {
            throw new IllegalArgumentException("Trailing space in the name is not allowed: '" + rawContact + "'");
        }

        String[] name = rawContact.split(" ");
        if (name.length > 2) {
            throw new IllegalArgumentException("Illegal name format: only 1-2 words allowed with "
                    + "exactly 1 space between them: '" + rawContact + "'");
        }
        String firstName = name[0];
        if (firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        return new Contact(firstName, name.length == 1 ? null : name[1]);
    }

    private Task5() {
    }
}
