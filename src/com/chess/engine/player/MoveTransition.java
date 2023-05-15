package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 * A class to capture the transition board(the board that the game will transition to when after a move is made)
 */
public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus) {

        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;

    }

}
