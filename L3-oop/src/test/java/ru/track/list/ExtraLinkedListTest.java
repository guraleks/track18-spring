package ru.track.list;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Samsung on 10.03.2018.
 */
public class ExtraLinkedListTest {

    // В реализованных тестах не было проверки на то, что идет корректное удаление нескольких подряд элементов из начала или конца связного списка
    @Test
    public void popTest() {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.add(0);
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        myLinkedList.add(4);
        myLinkedList.add(5);

        Assert.assertEquals(myLinkedList.pop(), 5);
        Assert.assertEquals(myLinkedList.pop(), 4);
        Assert.assertEquals(myLinkedList.pop(), 3);
        Assert.assertEquals(myLinkedList.pop(), 2);
    }

    @Test
    public void dequeueTest() {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.add(0);
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        myLinkedList.add(4);
        myLinkedList.add(5);

        Assert.assertEquals(myLinkedList.dequeue(), 0);
        Assert.assertEquals(myLinkedList.dequeue(), 1);
        Assert.assertEquals(myLinkedList.dequeue(), 2);
        Assert.assertEquals(myLinkedList.dequeue(), 3);
    }

}
