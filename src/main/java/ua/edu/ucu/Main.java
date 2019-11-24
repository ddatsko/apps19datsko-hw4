package ua.edu.ucu;

import ua.edu.ucu.autocomplete.PrefixMatches;
import ua.edu.ucu.tries.RWayTrie;

public class Main {
    public static void main(String[] args) {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
        Iterable<String> it = pm.wordsWithPrefix("ab");
        for (String s: it) {
            System.out.println(s);
        }
    }
}
