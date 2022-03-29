package ru.clevertec.customlist;

import java.util.Arrays;

public class CustomArrayList<E> implements CustomList<E> {

    public Object[] mArray;
    private int mCurrentPosition;
    int maxSize = Integer.MAX_VALUE;

    public CustomArrayList() {
        this(10);
    }

    public CustomArrayList(int size) {
        mCurrentPosition = 0;
        mArray = new Object[size];
    }

    @Override
    public CustomIterator<E> getIterator() {
        return new CustomArrayListIterator();
    }

    @Override
    public void setMaxSize(int size) {
        maxSize = size;
        if (size < mCurrentPosition) resize(size);
    }

    @Override
    public boolean add(E element) {
        if (mCurrentPosition >= mArray.length) {
            if (maxSize <= mArray.length) return false;
            long newSize = (long) (mArray.length * 1.5);
            resize(newSize > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) newSize);
        }
        mArray[mCurrentPosition] = element;
        mCurrentPosition++;
        return true;
    }

    public boolean add(E element, int position) {
        if (mCurrentPosition >= mArray.length) {
            if (maxSize <= mArray.length) return false;
            long newSize = (long) (mArray.length * 1.5);
            resize(newSize > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) newSize);
        }
        //идея предлагает поменять на System.arraycopy, но пусть будет так.
        for (int i = mCurrentPosition; i > position; i--) {
            mArray[i] = mArray[i - 1];
        }
        mArray[position] = element;
        mCurrentPosition++;
        return true;
    }

    @Override
    public boolean addAll(CustomList<? extends E> coll) {
        if (coll == null) return false;
        return addAll(coll.toArray());
    }

    @Override
    public boolean addAll(Object[] array) {
        if (array == null) return false;
        if (array.length >= mArray.length - mCurrentPosition) {
            if(maxSize - mArray.length < array.length) return false;
            long newSize = (long) mArray.length + array.length;
            if (newSize > Integer.MAX_VALUE) return false;
            else resize((int) newSize);
        }
        //я делал через for(int i = 0...) IDEA сама предложила переделать так :)
        System.arraycopy(array, 0, mArray, mCurrentPosition, array.length);
        mCurrentPosition += array.length;
        return true;
    }

    @Override
    public E set(int pos, E newElement) {
        Object oldElement = mArray[pos];
        mArray[pos] = newElement;
        return (E) oldElement;
    }

    @Override
    public E remove(int pos) {
        Object oldElement = mArray[pos];
        for (int i = pos; i < mCurrentPosition - 1; i++) {
            mArray[i] = mArray[i + 1];
        }
        mArray[mCurrentPosition] = null;
        mCurrentPosition--;
        return (E) oldElement;
    }

    @Override
    public void clear() {
        for (int i = 0; i < mCurrentPosition; i++) {
            mArray[i] = null;
        }
        mCurrentPosition = 0;
    }

    //Не знаю какой поиск нужен: по ссылке или эквивалентный.
    @Override
    public int find(E element) {
        for (int i = 0; i < mCurrentPosition; i++) {
            if (element.equals(mArray[i])) return i;
        }
        return -1;
    }

    @Override
    public E get(int pos) {
        if (pos < 0 || pos >= mCurrentPosition) return null;
        return (E) mArray[pos];
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(mArray, mCurrentPosition);
    }

    @Override
    public int size() {
        return mCurrentPosition;
    }

    @Override
    public void trim() {
        Object[] newArr = new Object[mArray.length];
        int currentPosition = 0;
        for (int i = 0; i < mCurrentPosition; i++) {
            if (mArray[i] != null) {
                newArr[currentPosition] = mArray[i];
                currentPosition++;
            }
        }
        mArray = newArr;
        mCurrentPosition = currentPosition;
    }

    private void resize(int newSize) {
        mArray = Arrays.copyOf(mArray, newSize);
        if (mCurrentPosition > newSize) mCurrentPosition = newSize;
    }

    class CustomArrayListIterator implements CustomIterator<E> {
        int position = 0;

        @Override
        public E next() {
            return (E) mArray[position++];
        }

        @Override
        public boolean hasNext() {
            return position < mCurrentPosition;
        }

        @Override
        public void remove() {
            CustomArrayList.this.remove(position);
        }

        @Override
        public void addBefore(E element) {
            add(element, position);
            position++;
        }

        @Override
        public void addAfter(E element) {
            add(element, position);
        }
    }
}
