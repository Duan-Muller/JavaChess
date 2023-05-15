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

    }

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

    //Using an enum to assign values to the hash map(ascii) board where capitalized letters are White alliance
    //and lower case letters are Black alliance
    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
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

    }

}
