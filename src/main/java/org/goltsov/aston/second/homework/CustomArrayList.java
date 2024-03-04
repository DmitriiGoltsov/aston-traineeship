package org.goltsov.aston.second.homework;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CustomArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private T[] array;

    private int size;

    public CustomArrayList() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public CustomArrayList(int initialCapacity) {
        this.array = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {

        if (isEmpty()) {
            return false;
        }

        for (int i = 0; i < this.size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementIterator();
    }

    @Override
    public Object[] toArray() {
        final T[] newArray = (T[]) new Object[this.size];
        System.arraycopy(array, 0, newArray, 0, this.size);
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return a;
    }

    @Override
    public boolean add(final T t) {
        if (array.length == this.size) {
            doubleArray();
        }

        array[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (this.size == 1 && array[0].equals(o)) {
            array = (T[]) new Object[DEFAULT_CAPACITY];
            size--;
        }

        for (int i = 0; i < size; i++) {
            if (array[i].equals(o) && i != this.size - 1) {
                System.arraycopy(array, i + 1, array, i, this.size - 1);

                size--;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        if (c.isEmpty() || isEmpty()) {
            return false;
        }

        for (final Object item : c) {
            if (!this.contains(item)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {

        if (c.isEmpty()) {
            return false;
        }

        if (array.length + c.size() >= this.size) {
            final T[] oldArray = array;
            array = (T[]) new Object[(this.array.length + c.size()) * 2];
            System.arraycopy(oldArray, 0, array, 0, oldArray.length);
        }

        for (T item : c) {
            array[size++] = item;
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {

        if (c.isEmpty()) {
            return false;
        }

        final T[] oldArray = array;

        if (array.length + c.size() >= this.size) {
            array = (T[]) new Object[(array.length + c.size()) * 2];
            System.arraycopy(oldArray, 0, array, index + c.size(), oldArray.length);
        }

        System.arraycopy(c.toArray(), 0, array, index, c.size());
        //System.arraycopy(oldArray, index, array, index, array.length);
        size = size + c.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        for (var item : c) {
            if (this.contains(item)) {
                this.remove(item);
            }
        }

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        int index = 0;
        while (index < this.size) {
            if (!c.contains(array[index])) {
                this.remove(index);
            } else {
                index++;
            }
        }

        return true;
    }

    @Override
    public void clear() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.array[index];
    }

    @Override
    public T set(int index, T element) {
        T previousElement = array[index];
        array[index] = element;
        return previousElement;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        if (this.size == array.length) {
            this.doubleArray();
        }

        T[] newPart = Arrays.copyOfRange(array, index, array.length);
        array[index] = element;
        System.arraycopy(newPart, 0, array, index + 1, newPart.length - 1);
        size++;
    }

    @Override
    public T remove(int index) {

        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        T elementToDelete = this.get(index);
        T[] newLeftPart = Arrays.copyOfRange(array, index + 1, array.length);
        System.arraycopy(newLeftPart, 0, array, index, newLeftPart.length);
        size--;
        return elementToDelete;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < this.size; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = this.size - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ElementIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ElementIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {

        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Either fromIndex or toIndex is incorrect. Or both are.");
        }

//        if (toIndex - fromIndex <= 0) {
//            return new CustomArrayList<>();
//        }

        List<T> newList = new CustomArrayList<>(toIndex - fromIndex);
        newList.addAll(Arrays.asList(array).subList(fromIndex, toIndex + 1));

        return newList;
    }

    private void doubleArray() {
        final T[] oldArray = array;
        array = (T[]) new Object[this.size * 2];
        System.arraycopy(oldArray, 0, array, 0, oldArray.length);
    }

    private class ElementIterator implements ListIterator<T> {

        private static final int LAST_INDEX_IS_NOT_SET = -1;

        private int index;

        private int lastIndex;

        public ElementIterator() {
            this.index = 0;
            this.lastIndex = LAST_INDEX_IS_NOT_SET;
        }

        public ElementIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return CustomArrayList.this.array.length > index;
        }

        @Override
        public T next() {
            final int currentIndex = index;
            index += 1;
            lastIndex = index;
            return (T) CustomArrayList.this.array[currentIndex];
        }

        @Override
        public boolean hasPrevious() {
            return this.index > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("There is no a previous element");
            }

            int previousIndex = --index;
            lastIndex = index;
            return (T) CustomArrayList.this.array[previousIndex];
        }

        @Override
        public int nextIndex() {
            if (this.hasNext()) {
                return index + 1;
            } else {
                return CustomArrayList.this.size;
            }
        }

        @Override
        public int previousIndex() {
            if (hasPrevious()) {
                return this.index - 1;
            } else {
                return -1;
            }
        }

        @Override
        public void remove() {
            if (lastIndex == LAST_INDEX_IS_NOT_SET) {
                throw new IllegalStateException("remove() call can only be made once per call to next or previous. "
                        + "It can be made only if add(E) has not been called after the last call to next or previous.");
            }

            CustomArrayList.this.remove(lastIndex);
        }

        @Override
        public void set(final T t) {
            if (lastIndex == LAST_INDEX_IS_NOT_SET) {
                throw new IllegalStateException("set() call can be made only if neither remove() nor add(E) "
                        + "have been called after the last call to next or previous");
            }
            CustomArrayList.this.set(lastIndex, t);
        }

        @Override
        public void add(final T element) {
            CustomArrayList.this.add(index, element);
            index++;
            lastIndex = LAST_INDEX_IS_NOT_SET;
        }
    }
}
