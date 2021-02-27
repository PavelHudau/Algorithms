package com.pavelhudau.graphs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.DirectedCycle;


public class WordNet {
    private final Digraph digraph;
    private final ST<String, SET<Integer>> nounsToSynsetKeyMap;
    private final ST<Integer, String> synsetToNounsKeyMap;
    private final SAP sap;
    private int verticesCount = 0;

    /**
     * Constructor.
     *
     * @param synsets synsets input file name.
     * @param hypernyms hypernyms input file name.
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null) {
            throw new IllegalArgumentException("synsets can not be null");
        }
        if (hypernyms == null) {
            throw new IllegalArgumentException("hypernyms can not be null");
        }

        this.synsetToNounsKeyMap = new ST<>();
        this.nounsToSynsetKeyMap = new ST<>();
        this.loadSynsets(synsets);
        this.digraph = this.buildDigraph(hypernyms);

        if(hasCycle(this.digraph)) {
            throw new IllegalArgumentException("digraph has cycle");
        }

        if (!isRooted(this.digraph)) {
            throw new IllegalArgumentException("digraph is not rooted");
        }

        sap = new SAP(this.digraph);
    }

    /**
     * All WordNet nouns.
     *
     * @return All WordNet nouns.
     */
    public Iterable<String> nouns() {
        return this.nounsToSynsetKeyMap.keys();
    }

    /**
     * Is the word a WordNet noun?
     *
     * @param word A word.
     * @return Whether the word a WordNet noun?
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word can not be null");
        }
        return this.nounsToSynsetKeyMap.contains(word);
    }

    /**
     * Distance between nounA and nounB
     *
     * @param nounA Noun A.
     * @param nounB Noun B.
     * @return Distance between nounA and nounB.
     */
    public int distance(String nounA, String nounB) {
        Iterable<Integer> nounASynsets = this.nounSynset(nounA);
        Iterable<Integer> nounBSynsets = this.nounSynset(nounB);
        return this.sap.length(nounASynsets, nounBSynsets);
    }

    /**
     * A synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     * in a shortest ancestral path.
     *
     * @param nounA Noun A.
     * @param nounB Noun B.
     * @return A synset that is the common ancestor of nounA and nounB in a shortest ancestral path.
     */
    public String sap(String nounA, String nounB) {
        Iterable<Integer> nounASynset = this.nounSynset(nounA);
        Iterable<Integer> nounBSynset = this.nounSynset(nounB);
        int shortestAncestorSynset = this.sap.ancestor(nounASynset, nounBSynset);
        return this.synsetToNounsKeyMap.get(shortestAncestorSynset);
    }

    private void loadSynsets(String synsetsFileName) {
        int maxSynsetKey = -1;
        In synsetsInput = new In(synsetsFileName);
        while (synsetsInput.hasNextLine()) {
            String line = synsetsInput.readLine();
            if (line != null) {
                String[] split = line.split(",");
                if (split.length > 1) {
                    int synsetKey = Integer.parseInt(split[0]);
                    String synsetNouns = split[1];
                    maxSynsetKey = Math.max(maxSynsetKey, synsetKey);
                    for (String noun : synsetNouns.split(" ")) {
                        SET<Integer> synsets = this.nounsToSynsetKeyMap.get(noun);
                        if(synsets == null) {
                            synsets = new SET<>();
                            this.nounsToSynsetKeyMap.put(noun, synsets);
                        }
                        synsets.add(synsetKey);
                    }

                    synsetToNounsKeyMap.put(synsetKey, synsetNouns);
                }
            }
        }
        this.verticesCount = maxSynsetKey + 1;
    }

    private Digraph buildDigraph(String hypernyms) {
        Digraph digraph = new Digraph(this.verticesCount);
        In hypernymsInput = new In(hypernyms);
        while (hypernymsInput.hasNextLine()) {
            String line = hypernymsInput.readLine();
            if (line != null) {
                String[] split = line.split(",");
                if (split.length > 1) {
                    int hyponym = Integer.parseInt(split[0]);
                    for (int i = 1; i < split.length; i++) {
                        int hypernym = Integer.parseInt(split[i]);
                        digraph.addEdge(hyponym, hypernym);
                    }
                }
            }
        }
        return digraph;
    }

    private static boolean hasCycle(Digraph digraph) {
        DirectedCycle cycle = new DirectedCycle(digraph);
        return cycle.hasCycle();
    }

    private static boolean isRooted(Digraph digraph) {
        DirectedRootedCheck rootedCheck = new DirectedRootedCheck(digraph);
        return rootedCheck.isRooted();
    }

    private Iterable<Integer> nounSynset(String noun) {
        if (noun == null) {
            throw new IllegalArgumentException("noun can not be null");
        }
        SET<Integer> synsets= this.nounsToSynsetKeyMap.get(noun);
        if (synsets == null) {
            throw new IllegalArgumentException(noun + " is not a noun.");
        }
        return synsets;
    }
}
