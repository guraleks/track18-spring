package ru.track.list;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    public int capacity = 10;
    private int size = 0;
    private int[] storage;

    public MyArrayList() {
        storage = new int[capacity];
    }

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        storage = new int[this.capacity];
    }

    @Override
    void add(int item) {
        if (size < capacity) {
            storage[size] = item;
            size++;
        } else {
            capacity = 2 * capacity + 1;
            MyArrayList myArrayList = new MyArrayList(capacity);
            System.arraycopy(this.storage, 0, myArrayList.storage, 0, size);
            storage = myArrayList.storage;
            storage[size] = item;
            size++;
        }
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx < size) {
            if (idx >= 0) {
                int elem = storage[idx];
                MyArrayList myArrayList = new MyArrayList(capacity);
                System.arraycopy(this.storage, 0, myArrayList.storage, 0, idx);
                System.arraycopy(this.storage, idx + 1, myArrayList.storage, idx, size - idx - 1);
                storage = myArrayList.storage;
                size--;
                return elem;
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (size != 0) {
            return storage[idx];
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Кол-во элементов списка
     */
    @Override
    int size() {
        return size;
    }
}
