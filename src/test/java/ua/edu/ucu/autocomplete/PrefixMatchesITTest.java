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
        pm2.load("a", "b", "aa", "bb", "aab", "aaab", "aabb", "abab", "aadddddd", "addddddddddd");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);
        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};
        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefixNotExist() {
        String pref = "abdsdsdsd";

        Iterable<String> result = pm.wordsWithPrefix(pref);
        String[] expResult = {};
        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testLoad() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("a", "b", "aaaa", null);
        assertEquals(pm.size(), 1);
        pm.load(null);
        assertEquals(pm.size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_String_K0() {
        String pref = "";

        pm.wordsWithPrefix(pref);

    }

    @Test
    public void testWordsWithPrefix() {
        String pref = "aa";
        String[] expected = new String[]{"aab", "aaab", "aabb", "aadddddd"};
        int i = 0;
        ;
        for (String s : pm2.wordsWithPrefix(pref)) {
            assertEquals(expected[i], s);
            i++;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefixOneSymbol() {
        String pref = "aaa";
        pm2.wordsWithPrefix(pref, -1);
    }

    @Test
    public void testWordsWithPrefixKLongDistance() {
        String pref = "aa";
        String[] expected = new String[]{"aab", "aaab", "aabb", "aadddddd"};
        int i = 0;
        for (String s : pm2.wordsWithPrefix(pref, 5)) {
            assertEquals(expected[i], s);
            i++;
        }

        expected = new String[]{"aab", "aaab", "aabb", "aadddddd"};
        i = 0;
        for (String s : pm2.wordsWithPrefix(pref, 6)) {
            assertEquals(expected[i], s);
            i++;
        }
    }

    @Test
    public void testWords() {
        Trie trie = new RWayTrie();
        String[] words = {"a", "b", "aa", "bb", "aab", "aaab", "aabb", "abab", "aadddddd", "addddddddddd"};
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
        String[] s = new String[]{"aaa", "aaaa", "bbbb", "bcbbb", "cccccc"};
        for (int i = 0; i < 5; i++) {
            assertEquals(pm.size(), i);
            pm.load(s[i]);
        }
        pm.load("bbbb");
        assertEquals(pm.size(), 5);


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
        assertEquals(pm.load(null), 0);
        assertEquals(pm.size(), 6);
    }

    @Test
    public void testPrefixMatchesDelete() {
        assertTrue(pm.delete("abc"));
        assertEquals(pm.size(), 4);
        assertFalse(pm.delete("123"));
        assertEquals(pm.size(), 4);
        assertFalse(pm.delete(null));
        assertEquals(pm.size(), 4);
    }

    @Test
    public void testPrefixMatchesContains() {
        assertTrue(pm.contains("abc"));
        assertEquals(pm.size(), 5);
        assertFalse(pm.contains("123"));
        assertEquals(pm.size(), 5);
        assertFalse(pm.contains(null));
    }


}
