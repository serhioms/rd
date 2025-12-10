
package games.board;

import java.util.*;

/*
 At present the Minimax player is kept separate from
 the MinimaxSearch routine.  The only reasoning
 behind this is that one returns a Move and the other
 returns a Score.

 Maybe they should be merged.  This would result in less
 code and perhaps be more elegent from that viewpoint.
*/

public class MinimaxPlayer implements Player {

  static int DEFAULT_DEPTH = 4;
  int depth;

  StaticEvaluator evaluator;

  MinimaxInterface minimax;

  public MinimaxPlayer() {
    this(DEFAULT_DEPTH);
  }

  public MinimaxPlayer(int depth) {
    this( depth , new PieceCounter() );
  }

  public MinimaxPlayer( int depth, StaticEvaluator evaluator ) {
    this.depth = depth;
    this.evaluator = evaluator;
    // choose a minimax implementation...
    // minimax = new Minimax();

    minimax = new MinimaxAlphaBeta();
  }

  public Move getMove(NodeInterface board, int toPlay, boolean maximize) {

    MoveEnumeration moves = board.getMoves(toPlay);
    double bestScore = Minimax.worst(maximize);
    Move bestMove = null;

    while (moves.moreMoves()) {
      Move m = moves.nextMove();
      // apply the move to the board
      // then score the board
      // then retract it
      board.applyMove(m, board.getPlayer(maximize) );

      // call minimax with the inverted flag:
      // if this is a maximizing turn, then the next player
      // is going to choose its minimum

      double mScore = minimax.score(board, evaluator , !maximize, depth);
      // System.out.println(m + " scores " + mScore);
      board.retractMove(m);

      if (Minimax.betterThan(mScore, bestScore, maximize)) {
        bestScore = mScore;
        bestMove = m;
      }
    }
    return bestMove;
  }
}
