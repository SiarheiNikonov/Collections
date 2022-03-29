package ru.clevertec.customlist;

public interface CustomList<E> {

    CustomIterator<E> getIterator();

    void setMaxSize(int size);

    boolean add(E element);

    boolean addAll(CustomList<? extends E> list);

    boolean addAll(Object[] array);

    E set(int pos, E newElement);

    E remove(int pos);

    void clear();

    int find(E element);

    E get(int pos);

    ///В ТЗ "массив toArray(массив)"
    Object[] toArray();

    int size();

    void trim();
}
