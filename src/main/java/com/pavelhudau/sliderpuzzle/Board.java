package com.pavelhudau.sliderpuzzle;


public class Board {

    private int [][] tiles;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.tiles.length);
        for (int i = 0; i < this.tiles.length; i++) {
            int[] row = this.tiles[i];
            builder.append("\n");
            for (int j = 0; j < row.length; j++) {
                builder.append(row[j]);
                if (j < row.length - 1) {
                    builder.append(" ");
                }
            }
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension(){
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming(){
        int dimension = this.dimension();
        int outOfPlace = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            int[] row = this.tiles[i];
            int rowShift = i * dimension;
            int rowLength = i < this.tiles.length - 1 ? row.length : row.length - 1;
            for (int j = 0; j < rowLength; j++) {
                int inPlaceTileValue = rowShift + j + 1;
                if (inPlaceTileValue != row[j]) {
                    outOfPlace++;
                }
            }
        }
        return outOfPlace;
    }
//
//    // sum of Manhattan distances between tiles and goal
//    public int manhattan(){
//
//    }
//
//    // is this board the goal board?
//    public boolean isGoal(){
//
//    }
//
//    // does this board equal y?
//    public boolean equals(Object y){
//
//    }
//
//    // all neighboring boards
//    public Iterable<Board> neighbors(){
//
//    }
//
//    // a board that is obtained by exchanging any pair of tiles
//    public Board twin(){
//
//    }

    // unit testing (not graded)
    public static void main(String[] args){

    }
}
