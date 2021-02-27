package com.pavelhudau.graphs;

public final class WordNetSingleton {
    private static WordNetSingleton INSTANCE;
    private final WordNet wn;

    private WordNetSingleton() {
        String synsetsFilePath = "src/main/resources/synsets.txt";
        String hypernymsFilePath = "src/main/resources/hypernyms.txt";
        this.wn = new WordNet(synsetsFilePath, hypernymsFilePath);
    }

    public synchronized static WordNetSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WordNetSingleton();
        }

        return INSTANCE;
    }

    public WordNet getWordNet() {
        return this.wn;
    }
}
