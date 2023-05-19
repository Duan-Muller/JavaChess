package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.*;

public class Board {

    private final List<Tile> gameBoard;

    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    //Two classes to keep track of the White Alliance and Black Alliance players
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    /**
     * A method to call the board builder method and create the chess board
     */
    private Board(final Builder builder) {

        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);

        //Adding a collection of the legal moves for each alliance to the Move collection
        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);

        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    @Override
    public String toString() {

        //A method to print the board in a hash map to interpret the board
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {

            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));

            if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {

                builder.append("\n");

            }

        }

        return builder.toString();

    }

    public Player whitePlayer() {

        return this.whitePlayer;

    }

    public Player blackPlayer() {

        return this.blackPlayer;

    }

    public Player currentPlayer() {

        return this.currentPlayer;

    }

    //Methods to get the Collection of each alliance's pieces for each respective player class
    public Collection<Piece> getBlackPieces() {

        return this.blackPieces;

    }

    public Collection<Piece> getWhitePieces() {

        return this.blackPieces;

    }

    /**
     * A method to combine every individual pieces list of legal moves and create a collection of legal moves
     * for each alliance
     */
    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final Piece piece : pieces) {
            //Using the for loop and this statement to get a collection and add all the elements into the legal
            //moves variable
            legalMoves.addAll(piece.calculateLegalMoves(this));

        }

        return ImmutableList.copyOf(legalMoves);

    }

    /**
     * A method to track both White and Black alliance's current pieces on the board
     */
    private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {

        final List<Piece> activePieces = new ArrayList<>();

        //Using a for loop to check whether there is a piece on a tile and if so the piece is added to the list of
        //active pieces
        for (final Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {

                final Piece piece = tile.getPiece();

                if (piece.getPieceAlliance() == alliance) {

                    activePieces.add(piece);

                }

            }

        }

        return ImmutableList.copyOf(activePieces);

    }

    /**
     * A method to get a tile's coordinate when called and return the coordinate
     */
    public Tile getTile(final int tileCoordinate) {

        return gameBoard.get(tileCoordinate);

    }

    /**
     * A method that populates a list of tiles numbered 0 to 63 that will represent the chess board
     * Making use of Immutable List to prevent from anyone changing the amount of tiles on the board
     */
    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];

        //Using a for loop to map the tiles on the board from the list
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            //Getting the piece that is associated with a specific start tile and create it with that piece on it
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }

        return ImmutableList.copyOf(tiles);
    }

    /**
     * A method to create the standard board and place every piece at their initial position
     */
    public static Board createStandardBoard() {

        final Builder builder = new Builder();

        //Black Alliance layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));

        //White Alliance Layout
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));

        //White to move first
        builder.setMoveMaker(Alliance.WHITE);
        return builder.build();
    }

    /**
     * A method to combine all the legal moves of both the white and black player
     */
    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }

    /**
     * Creating a class making use of the Builder Pattern to be able to create the chess board
     */
    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;

        public Builder() {

            this.boardConfig = new HashMap<>();

        }

        /**
         * A method to set a certain piece on their initial tile at the start of a chess match
         */
        public Builder setPiece(final Piece piece) {

            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;

        }

        /**
         * A method to change the move maker from White to Black alliance and vice versa to make it possible
         * for each side to make a move after another
         */
        public Builder setMoveMaker(final Alliance nextMoveMaker) {

            this.nextMoveMaker = nextMoveMaker;
            return this;

        }


        public Board build() {
            return new Board(this);

        }


        public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        }
    }
}
