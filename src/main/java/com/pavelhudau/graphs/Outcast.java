package com.pavelhudau.graphs;

public class Outcast {
    private final WordNet wordNet;

    /**
     * Constructor takes a WordNet object.
     *
     * @param wordnet WordNet instance.
     */
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    /**
     * Given an array of WordNet nouns, return an outcast.
     *
     * @param nouns array of nouns.
     * @return Furthest noun to the rest of nouns.
     */
    public String outcast(String[] nouns) {
        int maxDistance = -1;
        int maxDistanceIdx = -1;
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i != j) {
                    distance = distance + this.wordNet.distance(nouns[i], nouns[j]);
                }
            }
            if (distance > maxDistance) {
                maxDistanceIdx = i;
                maxDistance = distance;
            }
        }
        return nouns[maxDistanceIdx];
    }
}
