package ua.edu.ucu.autocomplete;

import org.junit.Before;
import org.junit.Test;
import ua.edu.ucu.collections.Queue;

import static org.junit.Assert.*;

public class QueueTest {
    private Queue q;

    @Before
    public void setUp() {
        q = new Queue();
    }

    @Test
    public void testQueue1() {
        int[] elements = new int[]{1, 2, 3, 4, 5};
        for (int i : elements) {
            q.enqueue(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(elements[i], q.peek());
            assertEquals(elements[i], q.dequeue());
        }
    }

    @Test
    public void testQueue2() {
        int[] elements = new int[]{1, 1, 1, 1, 1, 1};
        for (int i : elements) {
            q.enqueue(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(elements[i], q.peek());
            assertEquals(elements[i], q.dequeue());
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testQueueError() {
        q.dequeue();
    }
}