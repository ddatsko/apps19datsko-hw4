package ua.edu.ucu.tries;

public interface Trie {

    void add(Tuple word);

    boolean contains(String word);

    boolean delete(String word);

    Iterable<String> words();

    Iterable<String> wordsWithPrefix(String pref);

    int size();
}
