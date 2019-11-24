package ua.edu.ucu.iterators;

import java.util.Collections;
import java.util.Iterator;

public class TrieKLengthsIterator implements Iterator<String> {
    private Iterator<String> iter;
    private int k;
    private String next;
    private int sizesNum = 0;
    private int maxSize = -1;


    public TrieKLengthsIterator(Iterator<String> iter, int k) {
        if (k >= 2) {
            this.k = k;
            this.iter = iter;
            findNext();
        }
        else {
            this.iter = Collections.emptyIterator();
        }
    }

    private void findNext() {
        if (!iter.hasNext()) {
            next = null;
            return;
        }
        next = iter.next();
        if (sizesNum == k && next.length() > maxSize) {
            next = null;
            return;
        }

        if (next.length() > maxSize) {
            sizesNum++;
            maxSize = next.length();
        }
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

    public static Iterable<String> words(Iterable<String> iter, int k) {
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new TrieKLengthsIterator(iter.iterator(), k);
            }
        };
    }
}
