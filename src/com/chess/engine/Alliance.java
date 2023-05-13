package com.chess.engine;

/**
 * Enum to keep alliance typesafe
 */
public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };

    /**
     * A method used to determine if a piece must make use of the + or - sign to move on the board depending on their
     * alliance which is White Alliance is - and Black Alliance is +
     */
    public abstract int getDirection();

    /**
     * A method to confirm if a piece is of White alliance
     */
    public abstract boolean isWhite();


    /**
     * A method to confirm if a piece is of Black alliance
     */
    public abstract boolean isBlack();

}
