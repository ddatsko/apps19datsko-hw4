
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

/**
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;
    private PrefixMatches pm2;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");

        pm2 = new PrefixMatches(new RWayTrie());
        pm2.load("a", "b", "aa", "bb", "aab", "aaab", "aabb", "abab", "adddddd", "addddddddddd");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);
        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};
        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix() {
        String pref = "a";
        String[] expected = new String[]{"a", "aa", "aab", "aaab", "aabb", "abab", "adddddd", "addddddddddd"};
        int i = 0;
        for (String s : pm2.wordsWithPrefix(pref)) {
            assertEquals(expected[i], s);
            i++;
        }
    }

    @Test
    public void testWordsWithPrefixKLongDistance() {
        String pref = "a";
        String[] expected = new String[]{"a", "aa", "aab", "aaab", "aabb", "abab", "adddddd"};
        int i = 0;
        for (String s : pm2.wordsWithPrefix(pref, 5)) {
            assertEquals(expected[i], s);
            i++;
        }

        expected = new String[]{"a", "aa", "aab", "aaab", "aabb", "abab", "adddddd", "addddddddddd"};
        i = 0;
        for (String s : pm2.wordsWithPrefix(pref, 6)) {
            assertEquals(expected[i], s);
            i++;
        }
    }

    @Test
    public void testWords() {
        Trie trie = new RWayTrie();
        String[] words = {"a", "b", "aa", "bb", "aab", "aaab", "aabb", "abab", "adddddd", "addddddddddd"};
        for (String s : words) {
            trie.add(new Tuple(s, s.length()));
        }
        Iterable<String> res = trie.words();
        assertThat(res, containsInAnyOrder(words));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testPrefixMatchesSize() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        String[] s = new String[]{"a", "aa", "b", "bb", "cc"};
        for (int i = 0; i < 5; i++) {
            assertEquals(pm.size(), i);
            pm.load(s[i]);
        }
        for (int i = 5; i >= 1; i--) {
            assertEquals(pm.size(), i);
            pm.delete(s[i - 1]);
        }
    }

    @Test
    public void testPrefixMatchesLoad() {
        assertEquals(pm.load("123456"), 1);
        assertEquals(pm.size(), 6);
        assertEquals(pm.load("123456"), 0);
        assertEquals(pm.size(), 6);
    }

    @Test
    public void testPrefixMatchesDelete() {
        assertTrue(pm.delete("abc"));
        assertEquals(pm.size(), 4);
        assertFalse(pm.delete("123"));
        assertEquals(pm.size(), 4);
    }

    @Test
    public void testPrefixMatchesContains() {
        assertTrue(pm.contains("abc"));
        assertEquals(pm.size(), 5);
        assertFalse(pm.contains("123"));
        assertEquals(pm.size(), 5);
    }


}
