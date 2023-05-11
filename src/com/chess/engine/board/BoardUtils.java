package com.chess.engine.board;

public class BoardUtils {

    /**
     * A static method to be utilized when a piece is being moved to ensure each of the moves are legal.
     * Only First, Second, Seventh and Eighth column is used as this is the only place where the rule break
     * takes place for special pieces
     */
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    /**
     * A static method to be utilized when a piece is being moved to ensure each of the moves are legal.
     * Only First, Second, Seventh and Eighth column is used as this is the only place where the rule break
     * takes place for special pieces
     */
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    /**
     * A static method to be utilized when a piece is being moved to ensure each of the moves are legal.
     * Only First, Second, Seventh and Eighth column is used as this is the only place where the rule break
     * takes place for special pieces
     */
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    /**
     * A static method to be utilized when a piece is being moved to ensure each of the moves are legal.
     * Only First, Second, Seventh and Eighth column is used as this is the only place where the rule break
     * takes place for special pieces
     */
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    //BoardUtils constructor with a runtimeException to prevent a user from instantiating this class
    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    /**
     * A method to initialize the column in which a piece is standing and setting every tile in that column to true
     */
    private static boolean[] initColumn(int columnNumber) {

        //Declare array of booleans with 64 spaces representing each tile on a chess board
        final boolean[] column = new boolean[NUM_TILES];

        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);

        return column;

    }

    /**
     * Method to check if the possible coordinate after applying the offset will still be on the board and not out of bounds
     */
    public static boolean isValidTileCoordinate(final int coordinate) {

        return coordinate >= 0 && coordinate < NUM_TILES;

    }
}
