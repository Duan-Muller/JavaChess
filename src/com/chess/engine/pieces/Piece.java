package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    //Declaring a member field to assign a piece's position to this variable when the piece is being used
    protected final int piecePosition;

    //Declaring a member field to assign a piece's alliance(i.e. black or white) to this variable when the piece is being used
    protected final Alliance pieceAlliance;

    //Using the class main method to assign the piece position and piece alliance variables to a certain piece
    Piece(final int piecePosition, final Alliance pieceAlliance) {

        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;

    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    /**
     * Declaring a collection to store the legal moves for a piece which will return a list of legal moves when called
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

}
