package com.pavelhudau.boggle;

public class TraversableTrieSET {
    private TraversableTrieSET.Node root;

    public TraversableTrieSET() {
    }

    public TraversableTrieSET.Node getRoot() {
        return this.root;
    }

    public void add(String word) {
        if (word == null || word.length() == 0) {
            throw new IllegalArgumentException("argument to add() is null or empty.");
        }

        this.root = this.add(this.root, word, 0);
    }

    public boolean contains(String word) {
        if (word == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        } else {
            TraversableTrieSET.Node x = this.get(this.root, word, 0);
            return x != null && x.isWord;
        }
    }

    private TraversableTrieSET.Node add(TraversableTrieSET.Node node, String word, int currentCharIdx) {
        if (node == null) {
            node = new Node();
        }

        if (word.length() == currentCharIdx) {
            node.isWord = true;
        } else {
            char currentChar = word.charAt(currentCharIdx);
            node.next[currentChar] = this.add(node.next[currentChar], word, currentCharIdx + 1);
        }

        return node;
    }

    private TraversableTrieSET.Node get(TraversableTrieSET.Node currentNode, String word, int currentCharIdx) {
        if (currentNode == null) {
            return null;
        }

        if (currentCharIdx == word.length()) {
            return currentNode;
        } else {
            char currentChar = word.charAt(currentCharIdx);
            return this.get(currentNode.next[currentChar], word, currentCharIdx + 1);
        }
    }

    static class Node {
        private final TraversableTrieSET.Node[] next = new Node[256];
        private boolean isWord;

        public TraversableTrieSET.Node getNext(char character) {
            return this.next[character];
        }

        public boolean isDictionaryWord() {
            return this.isWord;
        }
    }
}