package ua.edu.ucu.tries;

import java.util.Collections;
import java.util.HashMap;

import ua.edu.ucu.iterators.TrieIterator;

public class RWayTrie implements Trie {
    public static class Node {
        public HashMap<Character, Node> map;
        public int weight;


        public Node() {
            map = new HashMap<>();
            weight = -1;
        }

    }

    private Node head = new Node();
    private int size;
    private int maxDepth;

    public RWayTrie() {
    }


    private Node getNode(String word) {
        if (word == null) {
            return null;
        }
        Node curNode = head;
        for (char c : word.toCharArray()) {
            if (curNode.map.containsKey(c)) {
                curNode = curNode.map.get(c);
            } else {
                return null;
            }
        }
        return curNode;
    }

    @Override
    public void add(Tuple t) {
        Node curNode = head;
        for (Character c : t.term.toCharArray()) {
            if (!curNode.map.containsKey(c)) {
                curNode.map.put(c, new Node());
            }
            curNode = curNode.map.get(c);
        }
        if (curNode.weight == -1) {
            size++;
        }
        curNode.weight = t.weight;
        maxDepth = Math.max(maxDepth, t.term.length());
    }

    @Override
    public boolean contains(String word) {
        Node node = getNode(word);
        return node != null && node.weight != -1;
    }

    @Override
    public boolean delete(String word) {
        Node node = getNode(word);
        if (node != null && node.weight != -1) {
            size--;
            node.weight = -1;
            return true;
        }
        return false;
    }


    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Node node = getNode(s);
        if (node == null) {
            return Collections::emptyIterator;
        }
        return TrieIterator.words(node, s);
    }

    @Override
    public int size() {
        return size;
    }
}
