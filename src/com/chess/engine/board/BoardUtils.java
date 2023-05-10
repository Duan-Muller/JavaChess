package com.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = null;
    public static final boolean[] SECOND_COLUMN = null;
    public static final boolean[] SEVENTH_COLUMN = null;
    public static final boolean[] EIGHTH_COLUMN = null;

    //BoardUtils constructor with a runtimeException to prevent a user from instantiating this class
    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    /**
     * Method to check if the possible coordinate after applying the offset will still be on the board and not out of bounds
     */
    public static boolean isValidTileCoordinate(int coordinate) {

        return coordinate >= 0 && coordinate < 64;

    }
}
