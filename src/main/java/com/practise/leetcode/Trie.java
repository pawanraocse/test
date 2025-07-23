package com.practise.leetcode;

public class Trie {

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    private static class TrieNode {

        boolean isWord;
        TrieNode[] children;

        TrieNode() {
            children = new TrieNode[26];
        }

        public void put(char c, TrieNode node) {
            children[c - 'a'] = node;
        }

        public boolean isWord() {
            return isWord;
        }

        public void isWord(boolean val) {
            isWord = val;
        }

        public TrieNode get(char c) {
            return children[c - 'a'];
        }

        public boolean containsKey(char c) {
            return children[c - 'a'] != null;
        }
    }

    public void insert(String word) {
        TrieNode node = root;
        for (final char c : word.toCharArray()) {
            if (node.containsKey(c)) {
                node = node.get(c);
            } else {
                final TrieNode subNode = new TrieNode();
                node.put(c, subNode);
                node = subNode;
            }
        }
        node.isWord(true);
    }

    public boolean search(String word) {
        final TrieNode node = getNode(word);
        return node != null && node.isWord;
    }

    public boolean startsWith(String prefix) {
        final TrieNode node = getNode(prefix);
        return node != null;
    }

    private TrieNode getNode(String word) {
        TrieNode node = root;
        for (final char c : word.toCharArray()) {
            node = node.get(c);
            if (node == null) {
                break;
            }
        }
        return node;
    }

}
