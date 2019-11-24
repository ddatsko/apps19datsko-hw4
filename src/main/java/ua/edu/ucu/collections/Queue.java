package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList list;

    public Queue() {
        list = new ImmutableLinkedList();
    }

    public boolean empty() {
        return list.size() == 0;
    }

    public Object peek() {
        return list.getFirst();
    }

    public Object dequeue() {
        Object el = list.getFirst();
        list = list.removeFirst();
        return el;
    }

    public void enqueue(Object e) {
        list = list.addLast(e);
    }
}