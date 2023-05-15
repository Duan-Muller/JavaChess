package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {

        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;

    }

    //A method to ensure that there is a King for the player on the board to ensure the game is still taking place
    private King establishKing() {

        for (final Piece piece : getActivePieces()) {

            if (piece.getPieceType().isKing()) {

                return (King) piece;

            }

        }

        throw new RuntimeException("Should not reach here! Not a valid board");
    }

    /**
     * A method to test if a move is contained in a player's legal move collection
     */
    public boolean isMoveLegal(final Move move) {

        return this.legalMoves.contains(move);

    }

    /**
     * A method to test/check whether a player is placed in check or not
     */
    public boolean isInCheck() {
        return false;
    }

    /**
     * A method to test/check whether a player is placed in check mate or not
     */
    public boolean isInCheckMate() {
        return false;
    }

    /**
     * A method to check whether players reached a stalemate or not
     */
    public boolean isInStaleMate() {
        return false;
    }

    /**
     * A method to check if a player made use of the castling strategy in chess or not(swapping a king and a rook)
     */
    public boolean isCastled() {
        return false;
    }

    /**
     * A method to capture the transition board(the board that the game will transition to when after a move is made)
     */
    public MoveTransition makeMove(final Move move) {
        return null;
    }

    /**
     * A get method to return a list/collection of active pieces on a player's Alliance
     */
    public abstract Collection<Piece> getActivePieces();

    /**
     * A method to get a piece's alliance
     */
    public abstract Alliance getAlliance();

    /**
     * A method to get a player's opponent's alliance
     */
    public abstract Player getOpponent();

}
