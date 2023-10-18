package edu.hw3.task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class BackwardIterator<E> implements Iterator<E> {
    private final ListIterator<E> iterator;

    public BackwardIterator(Collection<E> collection) {
        iterator = List.copyOf(Objects.requireNonNull(collection)).listIterator(collection.size());
    }

    @Override
    public boolean hasNext() {
        return iterator.hasPrevious();
    }

    @Override
    public E next() {
        return iterator.previous();
    }
}
