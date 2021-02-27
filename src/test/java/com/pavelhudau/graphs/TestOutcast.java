package com.pavelhudau.graphs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestOutcast {
    @Test
    void testOutcast() {
        Outcast outcast = new Outcast(getWordNet());
        assertEquals("damage",  outcast.outcast(new String[]{"running", "travel", "damage"}));
    }

    private WordNet getWordNet() {
        return WordNetSingleton.getInstance().getWordNet();
    }
}
