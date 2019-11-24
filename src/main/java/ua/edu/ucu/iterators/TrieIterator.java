package ua.edu.ucu.iterators;

import ua.edu.ucu.collections.Queue;
import ua.edu.ucu.tries.RWayTrie;

import java.util.Iterator;


public class TrieIterator implements Iterator<String> {
    private Queue queue;
    private String next;

    public TrieIterator(RWayTrie.Node node, String s) {
        queue = new Queue();
        queue.enqueue(new Object[]{node, s});
        findNext();
    }


    private void findNext() {
        while (!queue.empty()) {
            RWayTrie.Node curNode = (RWayTrie.Node) ((Object[]) queue.peek())[0];

            String pref = (String) ((Object[]) queue.dequeue())[1];
            for (Character c : curNode.map.keySet()) {
                queue.enqueue(new Object[]{curNode.map.get(c), pref + c});
            }

            if (curNode.weight != -1) {
                next = pref;
                return;

            }
        }
        next = null;
    }


    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public String next() {
        String tmp = next;
        findNext();
        return tmp;
    }

    public static Iterable<String> words(RWayTrie.Node node, String s) {
        return () -> new TrieIterator(node, s);
    }


}
