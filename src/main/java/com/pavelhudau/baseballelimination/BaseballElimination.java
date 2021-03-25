package com.pavelhudau.baseballelimination;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

public class BaseballElimination {
    private int numOfTeams;
    private ST<String, Integer> teamsToIdx;
    private int[] wins;
    private int[] loss;
    private int[] left;
    private int[][] games;

    /**
     * Create a baseball division from given filename in format specified below.
     * <p>
     * The input format is the number of teams in the division n followed by one line for each team. Each line contains
     * the team name (with no internal whitespace characters), the number of wins, the number of losses, the number of
     * remaining games, and the number of remaining games against each team in the division.
     * <p/>
     * <p>
     * team         wins loss left   Atl Phi NY  Mon
     * <p>
     * ------------------------------------------------
     * <p>
     * Atlanta       83   71    8     -   1   6   1
     * <p>
     * Philadelphia  80   79    3     1   -   0   2
     * <p>
     * New York      78   78    6     6   0   -   0
     * <p>
     * Montreal      77   82    3     1   2   0   -
     *
     * @param filename name of a file.
     */
    public BaseballElimination(String filename) {
        In in = new In(filename);
        this.numOfTeams = in.readInt();
        this.wins = new int[this.numOfTeams];
        this.loss = new int[this.numOfTeams];
        this.left = new int[this.numOfTeams];
        this.games = new int[this.numOfTeams][this.numOfTeams];
        this.teamsToIdx = new ST<>();
        for (int teamIdx = 0; teamIdx < numOfTeams; teamIdx++) {
            String teamName = in.readString();
            this.teamsToIdx.put(teamName, teamIdx);
            this.wins[teamIdx] = in.readInt();
            this.loss[teamIdx] = in.readInt();
            this.left[teamIdx] = in.readInt();
            this.games[teamIdx] = new int[this.numOfTeams];
            for (int j = 0; j < this.numOfTeams; j++) {
                this.games[teamIdx][j] = in.readInt();
            }
        }
    }

    /**
     * Number of teams.
     *
     * @return number of teams.
     */
    public int numberOfTeams() {
        return this.numOfTeams;
    }

    /**
     * All teams.
     *
     * @return All teams names.
     */
    public Iterable<String> teams() {
        return this.teamsToIdx.keys();
    }

    /**
     * Number of wins for given team.
     *
     * @param team Team name.
     * @return Number of wins for given team.
     */
    public int wins(String team) {
        return this.wins[this.resolveTeam(team)];
    }


    /**
     * Number of losses for given team.
     *
     * @param team Team name.
     * @return Number of losses for given team.
     */
    public int losses(String team) {
        return this.loss[this.resolveTeam(team)];
    }

    /**
     * Number of remaining games for given team.
     *
     * @param team Team name.
     * @return Number of remaining games for given team.
     */
    public int remaining(String team) {
        return this.left[this.resolveTeam(team)];
    }

    /**
     * Number of remaining games between team1 and team2.
     *
     * @param team1 Team name.
     * @param team2 Other team name.
     * @return Number of remaining games between team1 and team2.
     */
    public int against(String team1, String team2) {
        return this.games[this.resolveTeam(team1)][this.resolveTeam(team2)];
    }

//    public boolean isEliminated(String team)              // is given team eliminated?
//
//    public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated

    private int resolveTeam(String teamName) {
        if (teamName == null) {
            throw new IllegalArgumentException("team can not be null");
        }
        if (this.teamsToIdx.contains(teamName)) {
            return this.teamsToIdx.get(teamName);
        } else {
            throw new IllegalArgumentException("Unknown team " + teamName);
        }
    }
}
