package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import static com.chess.engine.board.Move.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    //Offset positions to consider for a knight to move to if the coordinate is not out of bounds
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final Alliance pieceAlliance, final int piecePosition) {
        super(piecePosition, pieceAlliance, PieceType.KNIGHT);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        //Using a for each loop to loop through all the candidate move coordinates
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {

            //Apply the offset to a variable to determine a possible position to which a knight may move
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                //If the rule breaks down by the knight being in the first, second, seventh, eighth column
                //then this exclusion is set up to catch the edge cases
                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }


                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                //If statement to test whether the tile is occupied or not and if it is not occupied
                //the piece may move there
                if (!candidateDestinationTile.isTileOccupied()) {

                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                } else {
                    //Else if the tile is occupied the alliance of the piece on that tile will be obtained
                    //using the methods below

                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    //If the Knight and another piece on a destination tile have different alliances than the
                    //move will be added to the list of legal moves as an attack move
                    if (this.pieceAlliance != pieceAlliance) {

                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));

                    }

                }

            }

        }
        //Return the list of legal moves after it is determined
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {

        return PieceType.KNIGHT.toString();

    }

    /**
     * First method to capture and handle edge cases to prevent the knight from moving to tiles which chess
     * rules do not allow i.e. other side of the board
     */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        //In the case where a knight is on the first column of the chess board and the offset is at positions
        //where the rule breaks down then this method will be used
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);

    }

    /**
     * Second method to capture and handle edge cases to prevent the knight from moving to tiles which chess
     * rules do not allow i.e. other side of the board
     */
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        //In the case where a knight is on the second column of the chess board and the offset is at positions
        //where the rule breaks down then this method will be used
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);

    }

    /**
     * Third method to capture and handle edge cases to prevent the knight from moving to tiles which chess
     * rules do not allow i.e. other side of the board
     */
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        //In the case where a knight is on the seventh column of the chess board and the offset is at positions
        //where the rule breaks down then this method will be used
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);

    }

    /**
     * Fourth method to capture and handle edge cases to prevent the knight from moving to tiles which chess
     * rules do not allow i.e. other side of the board
     */
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        //In the case where a knight is on the eighth column of the chess board and the offset is at positions
        // where the rule breaks down then this method will be used
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);

    }

}
