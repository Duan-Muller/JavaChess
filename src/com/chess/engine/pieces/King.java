package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    //Offset positions to consider for a King to move to if the coordinate is not out of bounds and within chess rules
    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(final Alliance pieceAlliance, final int piecePosition) {
        super(piecePosition, pieceAlliance, PieceType.KING);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {

            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {

                continue;

            }

            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                //If statement to test whether the tile is occupied or not and if it is not occupied
                //the piece may move there
                if (!candidateDestinationTile.isTileOccupied()) {

                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

                } else {
                    //Else if the tile is occupied the alliance of the piece on that tile will be obtained
                    //using the methods below

                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    //If the King and another piece on a destination tile have different alliances than the
                    //move will be added to the list of legal moves as an attack move
                    if (this.pieceAlliance != pieceAlliance) {

                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));

                    }

                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {

        return PieceType.KING.toString();

    }

    /**
     * First method to capture and handle edge cases to prevent the King from moving to tiles which chess
     * rules do not allow i.e. other side of the board
     */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        //In the case where a King is on the first column of the chess board and the offset is at positions
        //where the rule breaks down then this method will be used
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
                candidateOffset == 7);

    }

    /**
     * Second method to capture and handle edge cases to prevent the King from moving to tiles which chess
     * rules do not allow i.e. other side of the board
     */
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        //In the case where a King is on the second column of the chess board and the offset is at positions
        //where the rule breaks down then this method will be used
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 ||
                candidateOffset == 9);

    }
}
