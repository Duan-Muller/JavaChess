package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {

        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        //A method to determine if an opponents current moves attack the king position and get a list of moves
        //and if that list is not empty then the player will be placed in check
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();

    }

    public King getPlayerKing() {

        return this.playerKing;

    }

    public Collection<Move> getLegalMoves() {

        return this.legalMoves;

    }

    /**
     * A method to go through the opponents list of moves to see if any of the moves destination coordinate is that
     * of the other player's King position meaning it will be attacking the king
     */
    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();

        for (final Move move : moves) {
            if (piecePosition == move.getDestinationCoordinate()) {

                attackMoves.add(move);

            }

        }

        return ImmutableList.copyOf(attackMoves);
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
        return this.isInCheck;
    }

    /**
     * A method to test/check whether a player is placed in check mate or not
     */
    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    /**
     * A method to check whether players reached a stalemate or not
     */
    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    /**
     * A method to calculate whether a King can escape by going through each of the player's legal moves and
     * setting each move where the King can escape to true
     */
    protected boolean hasEscapeMoves() {

        for (final Move move : this.legalMoves) {

            final MoveTransition transition = makeMove(move);

            if (transition.getMoveStatus().isDone()) {

                return true;

            }

        }
        return false;
    }

    /**
     * A method to check if a player made use of the castling strategy in chess or not(swapping a king and a rook)
     */
    public boolean isCastled() {
        return false;
    }

    /**
     * A method to capture the transition board
     * (the board that the game will transition to when after a legal move is made)
     */
    public MoveTransition makeMove(final Move move) {

        //If a player move is not legal then the board transition will not take place and the move status will be
        //changed to illegal
        if (!isMoveLegal(move)) {

            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);

        }

        //Polymorphic-ally execute the player moves on a transition board which has not been created yet and return
        //the new board
        final Board transitionBoard = move.execute();

        //This variable determines if there are any attacks on a player's king and if there are then the move should not
        //be able to execute and the board doesn't change but the move status changes
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(
                transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());

        if (!kingAttacks.isEmpty()) {

            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);

        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
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

    /**
     * A method that will be used to determine if a player can Castle with their king and rook or not
     */
    protected abstract Collection<Move> calculateKingCastles(
            Collection<Move> playerLegals, Collection<Move> opponentsLegals);

}
