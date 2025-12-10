package games.board;

import games.checkers.Board;

public class PieceCounter implements StaticEvaluator {

    // the default values only make sense with
    // the piece codings in the games.checkers.Board class
    // - you need to provide your own for a different
    // board game by calling the second constructor

    // question arises - how does this perform the reversal,
    // that depends on who is who?

    public static int[] defaultValues = new int[Board.POSSIBLE_SQUARE_VALUES];

    static {
        defaultValues[Board.EMPTY] = 0;
        defaultValues[Board.WHITE_PAWN] = -1;
        defaultValues[Board.BLACK_PAWN] = 1;
        defaultValues[Board.WHITE_KING] = -3;
        defaultValues[Board.BLACK_KING] = 3;
    }

    public int[] values;

    public PieceCounter() {
        this(defaultValues);
    }

    public PieceCounter(int[] values) {
        this.values = values;
    }

    public PieceCounter(int man, int king) {
        this(new int[]{0, man, -man, king, -king});
    }

    // declare pieceCount final to reduce cost of method call
    public final double evaluate(int[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                count += values[board[i][j]];
        return count;
    }
}
