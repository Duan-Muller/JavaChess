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

    public static final boolean[] FIRST_ROW = initRow(0);

    /**
     * A static method to be utilized by the Pawn class to determine whether it is in the second row
     * i.e. White Alliance
     */
    public static final boolean[] SECOND_ROW = initRow(8);

    public static final boolean[] THIRD_ROW = initRow(16);

    public static final boolean[] FOURTH_ROW = initRow(24);

    public static final boolean[] FIFTH_ROW = initRow(32);

    public static final boolean[] SIXTH_ROW = initRow(40);

    /**
     * A static method to be utilized by the Pawn class to determine whether it is in the second row
     * i.e. Black Alliance
     */
    public static final boolean[] SEVENTH_ROW = initRow(48);

    public static final boolean[] EIGHTH_ROW = initRow(56);

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
     * A method to initialize the rows in which a piece is standing and setting every tile in that column to true
     */
    private static boolean[] initRow(int rowNumber) {

        final boolean[] row = new boolean[NUM_TILES];

        do {
            row[rowNumber] = true;
            rowNumber++;
        } while (rowNumber % NUM_TILES_PER_ROW != 0);

        return row;

    }

    /**
     * Method to check if the possible coordinate after applying the offset will still be on the board and
     * not out of bounds
     */
    public static boolean isValidTileCoordinate(final int coordinate) {

        return coordinate >= 0 && coordinate < NUM_TILES;

    }
}
