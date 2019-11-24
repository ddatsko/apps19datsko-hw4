package ua.edu.ucu.tries;


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

        public Node(int weight) {
            this();
            this.weight = weight;
        }
    }

    private Node head = new Node();
    private int size;
    private int max_depth;

    public RWayTrie() {
    }


    private Node getNode(String word) {
        Node cur_node = head;
        for (char c : word.toCharArray()) {
            if (cur_node.map.containsKey(c)) {
                cur_node = cur_node.map.get(c);
            } else {
                return null;
            }
        }
        return cur_node;
    }

    @Override
    public void add(Tuple t) {
        Node cur_node = head;
        for (Character c : t.term.toCharArray()) {
            if (!cur_node.map.containsKey(c)) {
                cur_node.map.put(c, new Node());
            }
            cur_node = cur_node.map.get(c);
        }
        if (cur_node.weight == -1) {
            size++;
        }
        cur_node.weight = t.weight;
        max_depth = Math.max(max_depth, t.term.length());
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
        return TrieIterator.words(node, s);
    }

    @Override
    public int size() {
        return size;
    }
}
