package ua.edu.ucu.autocomplete;

import ua.edu.ucu.iterators.TrieKLengthsIterator;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int added = 0;
        for (String s : strings) {
            String[] words = s.split("\\s+");
            for (String word : words) {
                trie.add(new Tuple(word, word.length()));
                added++;
            }
        }
        return added;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        return TrieKLengthsIterator.words(trie.wordsWithPrefix(pref), k);
    }

    public int size() {
        return trie.size();
    }
}
