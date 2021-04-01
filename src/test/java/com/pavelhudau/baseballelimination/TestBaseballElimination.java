package com.pavelhudau.baseballelimination;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class TestBaseballElimination {
    private final static String fileFourTeams = "src/main/resources/teams4.txt";
    private final static String fileFiveTeams = "src/main/resources/teams5.txt";

    @ParameterizedTest
    @CsvSource({
            fileFourTeams + ", 4",
            fileFiveTeams + ", 5",
    })
    void testNumberOfTeams(String file, int expectedNumberOfTeams) {
        BaseballElimination bm = new BaseballElimination(file);
        assertEquals(expectedNumberOfTeams, bm.numberOfTeams());
    }

    @Test
    void testTeams() {
        // GIVEN
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        Set<String> expectedTeams = new HashSet<>();
        expectedTeams.add("Atlanta");
        expectedTeams.add("Philadelphia");
        expectedTeams.add("New_York");
        expectedTeams.add("Montreal");
        int teamsCount = 0;
        // WHEN
        Iterable<String> teams = bm.teams();
        // THEN
        for (String team : teams) {
            teamsCount++;
            assertTrue(expectedTeams.contains(team));
        }
        assertEquals(4, teamsCount);
    }

    @ParameterizedTest
    @CsvSource({
            fileFourTeams + ", Atlanta, 83",
            fileFourTeams + ", Philadelphia, 80",
            fileFourTeams + ", New_York, 78",
            fileFourTeams + ", Montreal, 77",
            fileFiveTeams + ", New_York, 75",
            fileFiveTeams + ", Baltimore, 71",
            fileFiveTeams + ", Boston, 69",
            fileFiveTeams + ", Toronto, 63",
            fileFiveTeams + ", Detroit, 49",
    })
    void testWinsWhenInputIsCorrect(String file, String team, int expectedWins) {
        BaseballElimination bm = new BaseballElimination(file);
        assertEquals(expectedWins, bm.wins(team));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "NonExistingTeam"})
    void testWinsWhenTeamNameIsInvalid(String team) {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertThrows(IllegalArgumentException.class, () -> bm.wins(team));
    }

    @Test
    void testWinsWhenTeamNameIsNull() {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertThrows(IllegalArgumentException.class, () -> bm.wins(null));
    }

    @ParameterizedTest
    @CsvSource({
            fileFourTeams + ", Atlanta, 71",
            fileFourTeams + ", Philadelphia, 79",
            fileFourTeams + ", New_York, 78",
            fileFourTeams + ", Montreal, 82",
            fileFiveTeams + ", New_York, 59",
            fileFiveTeams + ", Baltimore, 63",
            fileFiveTeams + ", Boston, 66",
            fileFiveTeams + ", Toronto, 72",
            fileFiveTeams + ", Detroit, 86",
    })
    void testLossesWhenInputIsCorrect(String file, String team, int expectedWins) {
        BaseballElimination bm = new BaseballElimination(file);
        assertEquals(expectedWins, bm.losses(team));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "NonExistingTeam"})
    void testLossesWhenTeamNameIsInvalid(String team) {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertThrows(IllegalArgumentException.class, () -> bm.losses(team));
    }

    @Test
    void testLossesWhenTeamNameIsNull() {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertThrows(IllegalArgumentException.class, () -> bm.losses(null));
    }

    @ParameterizedTest
    @CsvSource({
            fileFourTeams + ", Atlanta, 8",
            fileFourTeams + ", Philadelphia, 3",
            fileFourTeams + ", New_York, 6",
            fileFourTeams + ", Montreal, 3",
            fileFiveTeams + ", New_York, 28",
            fileFiveTeams + ", Baltimore, 28",
            fileFiveTeams + ", Boston, 27",
            fileFiveTeams + ", Toronto, 27",
            fileFiveTeams + ", Detroit, 27",
    })
    void testRemainingWhenInputIsCorrect(String file, String team, int expectedWins) {
        BaseballElimination bm = new BaseballElimination(file);
        assertEquals(expectedWins, bm.remaining(team));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "NonExistingTeam"})
    void testRemainingWhenTeamNameIsInvalid(String team) {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertThrows(IllegalArgumentException.class, () -> bm.remaining(team));
    }

    @Test
    void testRemainingWhenTeamNameIsNull() {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertThrows(IllegalArgumentException.class, () -> bm.remaining(null));
    }

    @ParameterizedTest
    @CsvSource({
            "Atlanta , Atlanta, 0",
            "Philadelphia, Philadelphia, 0",
            "New_York, New_York, 0",
            "Montreal, Montreal, 0",
            "Atlanta , Philadelphia, 1",
            "Atlanta, New_York, 6",
            "Atlanta, Montreal, 1",
            "New_York, New_York, 0",
            "New_York, Atlanta, 6",
            "New_York, New_York, 0"
    })
    void testAgainstWhenInputIsCorrect(String team1, String team2, int expectedWins) {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertEquals(expectedWins, bm.against(team1, team2));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "NonExistingTeam"})
    void testAgainstWhenTeamNameIsInvalid(String invalidTeam) {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertThrows(IllegalArgumentException.class, () -> bm.against("Philadelphia", invalidTeam));
        assertThrows(IllegalArgumentException.class, () -> bm.against(invalidTeam, "Philadelphia"));
    }

    @Test
    void testAgainstWhenTeamNameIsNull() {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertThrows(IllegalArgumentException.class, () -> bm.against("Philadelphia", null));
        assertThrows(IllegalArgumentException.class, () -> bm.against(null, "Philadelphia"));
    }

    @ParameterizedTest
    @CsvSource({
            "Atlanta , false",
            "Philadelphia,true",
            "New_York, false",
            "Montreal, true",
    })
    void testIsEliminatedWithFourTeams(String team, boolean expectedIsEliminates) {
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        assertEquals(expectedIsEliminates, bm.isEliminated(team));
    }

    @ParameterizedTest
    @CsvSource({
            "New_York , false",
            "Baltimore,false",
            "Boston, false",
            "Toronto, false",
            "Detroit, true",
    })
    void testIsEliminatedWithFiveTeams(String team, boolean expectedIsEliminates) {
        BaseballElimination bm = new BaseballElimination(fileFiveTeams);
        assertEquals(expectedIsEliminates, bm.isEliminated(team));
    }

    @ParameterizedTest
    @CsvSource({
            "Atlanta",
            "New_York",
    })
    void testCertificateOfEliminationWithFourTeamsWhenNotEliminated(String team) {
        // GIVEN
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        // WHEN
        Iterable<String> certificateOfElimination = bm.certificateOfElimination(team);
        // THEN
        assertFalse(certificateOfElimination.iterator().hasNext());
    }

    @Test
    void testCertificateOfEliminationWithFourTeamsWhenPhiladelphiaIsEliminated() {
        // GIVEN
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        HashSet<String> expectedCertificateOfElimination = new HashSet<>();
        expectedCertificateOfElimination.add("Atlanta");
        expectedCertificateOfElimination.add("New_York");
        // WHEN
        Iterable<String> certificateOfElimination = bm.certificateOfElimination("Philadelphia");
        //THEN
        List<String> certificateOfEliminationActual =  StreamSupport
                .stream(certificateOfElimination.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(expectedCertificateOfElimination.size(), certificateOfEliminationActual.size());
        for (String actual : certificateOfEliminationActual) {
            assertTrue(expectedCertificateOfElimination.contains(actual));
        }
    }

    @Test
    void testCertificateOfEliminationWithFourTeamsWhenMontrealIsEliminated() {
        // GIVEN
        BaseballElimination bm = new BaseballElimination(fileFourTeams);
        HashSet<String> expectedCertificateOfElimination = new HashSet<>();
        expectedCertificateOfElimination.add("Atlanta");
        expectedCertificateOfElimination.add("Philadelphia");
        expectedCertificateOfElimination.add("New_York");
        // WHEN
        Iterable<String> certificateOfElimination = bm.certificateOfElimination("Montreal");
        //THEN
        List<String> certificateOfEliminationActual =  StreamSupport
                .stream(certificateOfElimination.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(expectedCertificateOfElimination.size(), certificateOfEliminationActual.size());
        for (String actual : certificateOfEliminationActual) {
            assertTrue(expectedCertificateOfElimination.contains(actual));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "New_York",
            "Baltimore",
            "Boston",
            "Toronto"
    })
    void testCertificateOfEliminationWithFiveTeamsWhenNotEliminated(String team) {
        // GIVEN
        BaseballElimination bm = new BaseballElimination(fileFiveTeams);
        // WHEN
        Iterable<String> certificateOfElimination = bm.certificateOfElimination(team);
        // THEN
        assertFalse(certificateOfElimination.iterator().hasNext());
    }

    @Test
    void testCertificateOfEliminationWithFiveTeamsWhenDetroitIsEliminated() {
        // GIVEN
        BaseballElimination bm = new BaseballElimination(fileFiveTeams);
        HashSet<String> expectedCertificateOfElimination = new HashSet<>();
        expectedCertificateOfElimination.add("New_York");
        expectedCertificateOfElimination.add("Boston");
        expectedCertificateOfElimination.add("Baltimore");
        expectedCertificateOfElimination.add("Toronto");
        // WHEN
        Iterable<String> certificateOfElimination = bm.certificateOfElimination("Detroit");
        //THEN
        List<String> certificateOfEliminationActual =  StreamSupport
                .stream(certificateOfElimination.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(expectedCertificateOfElimination.size(), certificateOfEliminationActual.size());
        for (String actual : certificateOfEliminationActual) {
            assertTrue(expectedCertificateOfElimination.contains(actual));
        }
    }
}
