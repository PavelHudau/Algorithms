package com.pavelhudau.baseballelimination;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

import java.util.ArrayList;

public class BaseballElimination {
    private final int numOfTeams;
    private final ST<String, Integer> teamsToIdx;
    private final String[] teams;
    private final int[] wins;
    private final int[] loss;
    private final int[] left;
    private final int[][] games;

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
        this.teams = new String[this.numOfTeams];
        for (int teamIdx = 0; teamIdx < numOfTeams; teamIdx++) {
            String teamName = in.readString();
            this.teamsToIdx.put(teamName, teamIdx);
            this.teams[teamIdx] = teamName;
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

    /**
     * Determine whether a given team is eliminated?
     *
     * @param team Team name.
     * @return True if a given team is eliminated, else False.
     */
    public boolean isEliminated(String team) {
        int teamIdx = this.resolveTeam(team);
        return this.isTriviallyEliminated(teamIdx) || this.isNominallyEliminated(teamIdx);
    }

    /**
     * Subset R of teams that eliminates given team.
     *
     * @param team Team name.
     * @return Collection of teams names that eliminates given team; null if not eliminated.
     */
    public Iterable<String> certificateOfElimination(String team) {
        int teamX = this.resolveTeam(team);
        FordFulkerson fordFulkerson = new FordFulkerson(createFlowNetwork(teamX));
        fordFulkerson.run(this.sourceVertex(), this.targetVertex());
        ArrayList<String> certificateOfEliminationTeams = new ArrayList<>();

        for (int i = 0; i < this.numOfTeams; i++) {
            if (i != teamX) {
                if (fordFulkerson.inMinCut(i)) {
                    certificateOfEliminationTeams.add(this.teams[i]);
                }
            }
        }

        return certificateOfEliminationTeams;
    }

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

    private boolean isTriviallyEliminated(int team) {
        int teamMaxPossibleWins = this.wins[team] + this.left[team];
        for (int i = 0; i < this.numOfTeams; i++) {
            if (i == team && this.wins[i] > teamMaxPossibleWins) {
                return true;
            }
        }

        return false;
    }

    private boolean isNominallyEliminated(int teamX) {
        FordFulkerson fordFulkerson = new FordFulkerson(createFlowNetwork(teamX));
        fordFulkerson.run(this.sourceVertex(), this.targetVertex());
        for (int i = 0; i < this.numOfTeams; i++) {
            if (i != teamX) {
                // If team i is in a min cut then it means that team i has still games to play (there is still residual
                // value of one of team's i game edges) but the team i already won more games than teamX can possibly do
                // (Edge [team i -> target] has no residual value left).
                return fordFulkerson.inMinCut(i);
            }
        }

        return false;
    }

    private FlowNetwork createFlowNetwork(int teamX) {
        // We make team vertices match team IDs, thereby simplify matching between team and team vertex and
        // hence simplify search for certificate of elimination.
        //
        // We do not need to come back to game vertices, therefore we do not care what indices they will get,
        // as long as they are in the range and valid.
        // We start game vertices right after team max id which is exactly number of teams.
        int nextGamesVertex = this.numOfTeams;
        int vertexSource = this.sourceVertex(); // Total number of vertices - 2
        int vertexTarget = this.targetVertex(); // Total number of vertices - 1
        FlowNetwork network = new FlowNetwork(vertexTarget + 1);

        int maxPossibleWinsOfTeamX = this.wins[teamX] + this.left[teamX];

        for (int i = 0; i < this.numOfTeams; i++) {
            if (i == teamX) {
                continue;
            }

            // Below loop adds edges corresponding to all possible games between all teams except for team x
            for (int j = i + 1; j < this.numOfTeams; j++) {
                if (j == teamX) {
                    continue;
                }

                // Connect source and game vertices.
                network.addEdge(new FlowEdge(vertexSource, nextGamesVertex, this.games[i][j]));
                // Connect game vertex with team vertices.
                network.addEdge(new FlowEdge(nextGamesVertex, i, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(nextGamesVertex, j, Double.POSITIVE_INFINITY));
                nextGamesVertex++;
            }

            // Connect team vertex to target vertex.
            // We want to know if there is some way of completing all the games so that team x ends up winning at least
            // as many games as team i. Since team x can win as many as this.wins[x] + this.left[x] games, we prevent
            // team i from winning more than that many games in total, by including an edge from team vertex i to the
            // target vertex with capacity  this.wins[x] + this.left[x] - this.win[i].
            network.addEdge(new FlowEdge(i, vertexTarget, Math.max(maxPossibleWinsOfTeamX - this.wins[i], 0)));
        }

        return network;
    }

    private int sourceVertex() {
        // total combination - diagonal (self with self) / half
        int totalGameVerticesCnt = (this.numOfTeams * this.numOfTeams - this.numOfTeams) / 2;
        // Source vertex starts after all game vertices and all team vertices.
        // Team vertices are from 0 to this.numOfTeams - 1.
        // Vertex ids are 0 based, therefore we do not add 1;
        return totalGameVerticesCnt + 1;
    }

    private int targetVertex() {
        // Target vertex haves next index to source vertex;
        return this.sourceVertex() + 1;
    }
}
