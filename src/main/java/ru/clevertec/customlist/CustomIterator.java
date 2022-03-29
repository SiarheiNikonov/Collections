package ru.clevertec.customlist;

public interface CustomIterator<E> {
    E next();
    boolean hasNext();
    void remove();
    void addBefore(E element);
    void addAfter(E element);
}
