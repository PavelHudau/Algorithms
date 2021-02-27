package com.pavelhudau.graphs;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestWordNet {
    private static WordNet wn;

    @Test
    void testNouns() {
        assertNotNull(this.getWordNet().nouns());
    }

    @Test
    void testIsNoun() {
        assertTrue(this.getWordNet().isNoun("zooflagellate"));
        assertFalse(this.getWordNet().isNoun("zooflagellateX"));
    }

    @Test
    void testDistance() {
        assertEquals(0, this.getWordNet().distance("zooflagellate", "zoomastigote"));
        assertEquals(2, this.getWordNet().distance("motion", "tremor"));
        assertEquals(2, this.getWordNet().distance("tremor", "motion"));
        assertEquals(4, this.getWordNet().distance("leap", "miracle"));
        assertEquals(4, this.getWordNet().distance("miracle", "leap"));
    }

    @Test
    void sapTest() {
        assertEquals("zoomastigote zooflagellate", this.getWordNet().sap("zooflagellate", "zoomastigote"));
        assertEquals("motion", this.getWordNet().sap("motion", "tremor"));
        assertEquals("motion", this.getWordNet().sap("tremor", "motion"));
        assertEquals("happening occurrence occurrent natural_event", this.getWordNet().sap("leap", "miracle"));
        assertEquals("happening occurrence occurrent natural_event", this.getWordNet().sap("miracle", "leap"));
    }

    private WordNet getWordNet() {
        return WordNetSingleton.getInstance().getWordNet();
    }
}
