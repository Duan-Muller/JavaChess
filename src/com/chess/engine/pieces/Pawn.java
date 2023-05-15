package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece {

    //Offset positions to consider for a Pawn to move to if the coordinate is not out of bounds and within chess rules
    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(piecePosition, pieceAlliance, PieceType.PAWN);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {

            //Get a possible destination for a pawn to move to
            //Depending on the pawn's alliance the move will either be +8 or -8 to move
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

            //Test to see whether the coordinate is on the board
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            //Normal move for a pawn to an empty tile if the test passes
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                //More work to do!!(PROMOTIONS OF PAWNS)
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                //An if statement to check whether it is a pawn's first move or not and if true the piece may do a
                //pawn jump of 2 tiles but only when it is first move
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {

                final int behindCandidateDestinationCoordinate = this.piecePosition +
                        (this.pieceAlliance.getDirection() * 8);

                //If statement to check if the immediate tile in front of a pawn is empty to ensure that
                //it can do the pawn jump and add it to the major moves collection otherwise it is not allowed
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
                /* If statement to handle a pawn's attacking move and catching edge cases for white and black alliance
                pawns on the First and Eight column to ensure and prevent them from attacking off the board diagonally
                 */
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {

                /* If statement to check whether a pawn can do the attacking move left diagonally and to confirm that
                the piece to be attacked is not the same alliance and handling when a pawn is attacking into a
                pawn promotion tile*/
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {

                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //More work to do !!
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                    }
                }


            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {

                /* If statement to check whether a pawn can do the attacking move right diagonally and to confirm that
                the piece to be attacked is not the same alliance and handling when a pawn is attacking into a
                pawn promotion tile*/
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {

                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //More work to do !!
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                    }
                }

            }

        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {

        return PieceType.PAWN.toString();

    }
}
