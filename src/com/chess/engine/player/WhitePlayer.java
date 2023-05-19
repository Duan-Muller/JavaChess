package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player {
    public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {

        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);


    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {

        final List<Move> kingCastles = new ArrayList<>();
        //If statements to check whether a player is eligible to make a castling move or not and if eligible then add
        //the move to the kingCastles list
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            
            //King side castle for white
            if (!this.board.getTile(61).isTileOccupied() &&
                    !this.board.getTile(62).isTileOccupied()) {

                final Tile rookTile = this.board.getTile(63);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {

                    if (Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {

                        kingCastles.add(null);

                    }


                }
            }

            //Queen side castle for white
            if (!this.board.getTile(59).isTileOccupied() &&
                    !this.board.getTile(58).isTileOccupied() &&
                    !this.board.getTile(57).isTileOccupied()) {

                final Tile rookTile = this.board.getTile(56);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {

                    kingCastles.add(null);

                }


            }

        }

        return ImmutableList.copyOf(kingCastles);
    }

}
