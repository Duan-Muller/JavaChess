package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    //Declaring a member field to get the type of piece which is present on a certain tile
    protected final PieceType pieceType;

    //Declaring a member field to check/confirm if it is a piece's first move or not
    protected final boolean isFirstMove;

    //Declaring a member field to assign a piece's position to this variable when the piece is being used
    protected final int piecePosition;

    //Declaring a member field to assign a piece's alliance(i.e. black or white) to this variable when the piece is being used
    protected final Alliance pieceAlliance;

    private final int cachedHashCode;

    /**
     * A class which will be used for every piece to assign their Position, Alliance to them as well as check
     * if it is a piece's first move
     */
    Piece(final int piecePosition, final Alliance pieceAlliance, final PieceType pieceType) {

        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        //More work to do!
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();

    }

    private int computeHashCode() {

        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;

    }

    //Overriding the default equals method as I want object equality and not reference equality
    @Override
    public boolean equals(final Object other) {

        if (this == other) {

            return true;

        }

        if (!(other instanceof Piece)) {

            return false;

        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() &&
                pieceType == otherPiece.getPieceType() &&
                pieceAlliance == otherPiece.getPieceAlliance() &&
                isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {

        return this.cachedHashCode;

    }


    /**
     * Simple get method to return a piece's current position
     */
    public int getPiecePosition() {

        return this.piecePosition;

    }

    /**
     * Simple method to return a piece's alliance
     */
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    /**
     * Simple method to assign a value to isFirstMove and return it when called
     */
    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public PieceType getPieceType() {

        return this.pieceType;

    }

    /**
     * Declaring a collection to store the legal moves for a piece which will return a list of legal moves when called
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    /**
     * A method that will get and apply a move to an existing piece the player is on and return a new piece which is
     * the same as the old piece just with an update position
     */
    public abstract Piece movePiece(Move move);

    //Using an enum to assign values to the hash map(ascii) board where capitalized letters are White alliance
    //and lower case letters are Black alliance
    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private String pieceName;

        PieceType(String pieceName) {

            this.pieceName = pieceName;

        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();
    }

}
