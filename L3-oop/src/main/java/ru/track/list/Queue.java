package ru.track.list;

/**
 * Created by Samsung on 10.03.2018.
 */
public interface Queue {
    void enqueue(int value); // поместить элемент в очередь
    int dequeue(); // вытащить первый элемент из очереди
}
