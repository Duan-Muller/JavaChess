package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    protected final int tileCoordinate;                                                                                 //Variable to store a tile's coordinates, declared protected so that it can only be accessed by subclasses

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();                           //Declare a method to store all empty tiles in a cache

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {                                              //Initializing method to create all the empty tiles and store it in a cache

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();                                                   //Using the map and HashMap objects to create the chess board

        for (int i = 0; i < 64; i++){                                                                                   //Using a for loop to place an empty tile at every position on a chess board
            emptyTileMap.put(i,new EmptyTile(i));

        }

        return ImmutableMap.copyOf(emptyTileMap);                                                                       //Return the empty tile map to make the chess board empty and making use of the Guava library made by Google
    }                                                                                                                   //Making use of an Immutable Map to prevent anyone from changing the map that was created

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);               //This method creates a new tile which gives an empty tile from the cache or create a new occupied tile
    }

    private Tile(int tileCoordinate){                                                                                   //Constructor to create a new empty tile at the coordinates given
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();                                                                           //Declare an abstract method to check whether a tile has a piece on it or not

    public abstract Piece getPiece();                                                                                   //Declare an abstract method to get info on a piece

    /* subclasses going to be used to represent empty tile */

    public static final class EmptyTile extends Tile{                                                                   //Creating a class to give an empty tile attributes

        private EmptyTile (final int coordinate){                                                                               //Constructor to create the empty tile object and using parent class attribute
            super(coordinate);
        }

        @Override                                                                                                       //Override occupied method to state that the tile is not occupied
        public boolean isTileOccupied(){
            return false;
        }

        @Override                                                                                                       //Override getPiece method to state that there is no piece present on said tile
        public Piece getPiece(){
            return null;
        }
    }

    /* subclasses going to be used to represent occupied tile */

    public static final class OccupiedTile extends Tile{                                                                //Creating a class to give an occupied tile attributes

        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate, Piece pieceOnTile){                                                            //Constructor to create the occupied tile object and using parent class attribute
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override                                                                                                       //Override occupied method to state that the tile is occupied
        public boolean isTileOccupied(){

            return true;
        }

        @Override                                                                                                       //Override getPiece method to get what piece is occupying said tile
        public Piece getPiece(){

            return this.pieceOnTile;
        }
    }

}
