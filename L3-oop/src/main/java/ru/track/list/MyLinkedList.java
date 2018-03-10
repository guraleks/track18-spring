package ru.track.list;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List implements Queue, Stack {

    private int size = 0;
    private Node head = null;
    private Node tail = null;

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    @Override
    void add(int item) {
        Node node = new Node(head, tail, item);
        if (tail == null) {
            tail = node;
            head = node;
            size++;
        } else {
            node.prev = tail;
            tail.next = node;
            node.next = null;
            tail = node;
            size++;
        }
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx < size) {
            if (idx >= 0) {
                int num = 0;
                Node current = head;
                while (num != idx) {
                    current = current.next;
                    num++;
                }
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                size--;
                return current.val;
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx < size) {
            if (idx >= 0) {
                Node current = head;
                for (int i = 0; i < idx; i++) {
                    current = current.next;
                }
                return current.val;
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    int size() {
        return size;
    }


    /*методы интерфейсов*/
    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
        return remove(0);
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        int num = 0;
        Node current = head;
        while (current.next != null) {
            current = current.next;
            num++;
        }
        return remove(num);
    }
}
