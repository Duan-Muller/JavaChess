package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import static com.chess.engine.board.Board.*;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    public static final Move NULL_MOVE = new nullMove();

    /**
     * Constructor to initialize the Move class
     */
    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    //Generate a hashCode for each unique move
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;

        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();

        return result;

    }

    @Override
    public boolean equals(final Object other) {

        if (this == other) {

            return true;

        }

        if (!(other instanceof Move)) {

            return false;

        }

        final Move otherMove = (Move) other;
        return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getMovedPiece().equals(otherMove.getMovedPiece());

    }

    public int getCurrentCoordinate() {

        return this.movedPiece.getPiecePosition();

    }

    public int getDestinationCoordinate() {

        return this.destinationCoordinate;

    }

    public Piece getMovedPiece() {

        return this.movedPiece;

    }

    /* 3 Helper methods in determining types of moves and the creation of moves*/

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    /* End of helper methods */

    public Board execute() {

        final Builder builder = new Builder();
        //Go through all the current player's pieces and all the pieces that aren't moved will be placed at the
        //same spot on the new board
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
            //TODO hashcode and equals for pieces
            if (!this.movedPiece.equals(piece)) {

                builder.setPiece(piece);

            }

        }

        //Go through all the opponent player's pieces and place them at the same spot on the new board
        for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {

            builder.setPiece(piece);

        }
        //Move the moved piece to the new destination tile
        builder.setPiece(this.movedPiece.movePiece(this));
        //Change the next move maker to the other player to equally allow each player to play
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

        return builder.build();
    }


    /**
     * A method used to handle when major moves takes place
     */
    public static final class MajorMove extends Move {
        //Constructor for MajorMove
        public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    /**
     * A method to be called when an attacking move is made
     */
    public static class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {

            if (this == other) {

                return true;

            }

            if (!(other instanceof AttackMove)) {

                return false;

            }

            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());

        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }
    }

    /* Different unique moves as concrete subclasses below that furthermore extends the move class to populate
     * the list/collection of legal moves */

    public static final class pawnMove extends Move {

        public pawnMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static class pawnAttackMove extends AttackMove {

        public pawnAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

    }

    public static final class pawnEnPassantAttackMove extends pawnAttackMove {

        public pawnEnPassantAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                                       final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

    }

    public static final class pawnJump extends Move {

        public pawnJump(final Board board, final Piece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        //Place every piece on the board at the correct position and set the pawn that jumped at the correct tile and
        //handle the en passant move and determine whether it can be applied or not
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }

            }

            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);

            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    /**
     * A class that determines if a player is trying or did a castling move and to verify that is legal or not
     */
    static abstract class castleMove extends Move {

        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public castleMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                          final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {

            return this.castleRook;

        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {

            final Builder builder = new Builder();

            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }

            }

            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleRookDestination));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    public static final class kingSideCastleMove extends castleMove {

        public kingSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                                  final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString() {
            //Default PGN Chess format for king side castle
            return "O-O";
        }
    }

    public static final class queenSideCastleMove extends castleMove {

        public queenSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                                   final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString() {
            //Default PGN Chess format for queen side castle
            return "O-O-O";
        }

    }

    public static final class nullMove extends Move {

        public nullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute this move");
        }

    }

    public static class moveFactory {

        private moveFactory() {

            throw new RuntimeException("Not instantiable");

        }

        //A method to create a new move and add it to the list
        public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {

            for (final Move move : board.getAllLegalMoves()) {

                if (move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestinationCoordinate() == destinationCoordinate) {

                    return move;

                }

            }

            return NULL_MOVE;

        }

    }

}
