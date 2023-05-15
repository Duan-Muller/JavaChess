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

public class Rook extends Piece {

    //Offset positions to consider for a rook to move to if the coordinate is not out of bounds
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};

    public Rook(final Alliance pieceAlliance, final int piecePosition) {
        super(piecePosition, pieceAlliance, PieceType.ROOK);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {

            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                //If the rule breaks down by the rook being in the first and eighth column
                //then this exclusion is set up to catch the edge cases
                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;

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

                        //If the bishop and another piece on a destination tile have different alliances than the
                        //move will be added to the list of legal moves
                        if (this.pieceAlliance != pieceAlliance) {

                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));

                        }
                        break;
                    }

                }

            }

        }


        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {

        return PieceType.ROOK.toString();

    }

    /**
     * First method to capture and handle edge cases to prevent the rook from moving to tiles which chess
     * rules do not allow i.e. other side of the board
     */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {

        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);

    }

    /**
     * Second method to capture and handle edge cases to prevent the rook from moving to tiles which chess
     * rules do not allow i.e. other side of the board
     */
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {

        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);

    }
}
