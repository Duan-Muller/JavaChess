package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    //Variable to store a tile's coordinates, declared protected so that it can only be accessed by subclasses
    protected final int tileCoordinate;

    /**
     * Method to store all empty tiles in a cache
     */
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    /**
     * Initializing method to create all the empty tiles and store it in a cache
     */
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {

        //Using the map and HashMap objects to create the chess board
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        //Using a for loop to place an empty tile at every position on a chess board
        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));

        }

        //Return the empty tile map to make the chess board empty and making use of the Guava library made by Google
        //Making use of an Immutable Map to prevent anyone from changing the map that was created
        return ImmutableMap.copyOf(emptyTileMap);
    }

    /**
     * This method creates a new tile which gives an empty tile from the cache or create a new occupied tile
     */
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    //Constructor to create a new empty tile at the coordinates given
    private Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    /**
     * Declare an abstract method to check whether a tile has a piece on it or not
     */
    public abstract boolean isTileOccupied();

    /**
     * Declare an abstract method to get info on a piece
     */
    public abstract Piece getPiece();

    /* subclasses going to be used to represent empty tile */

    /**
     * Creating a class to give an empty tile attributes
     */
    public static final class EmptyTile extends Tile {

        //Constructor to create the empty tile object and using parent class attribute
        private EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        //Override occupied method to state that the tile is not occupied
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        //Override getPiece method to state that there is no piece present on said tile
        public Piece getPiece() {
            return null;
        }
    }

    /* subclasses going to be used to represent occupied tile */

    /**
     * Creating a class to give an occupied tile attributes
     */
    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        //Constructor to create the occupied tile object and using parent class attribute
        private OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        //Override occupied method to state that the tile is occupied
        public boolean isTileOccupied() {

            return true;
        }

        @Override
        //Override getPiece method to get what piece is occupying said tile
        public Piece getPiece() {

            return this.pieceOnTile;
        }
    }

}
