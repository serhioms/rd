
package games.board;

import games.board.*;
import java.util.*;

public class RandomPlayer implements Player {

  public Move getMove(NodeInterface node, int toPlay, boolean maximize) {
    double bestScore = -1.0;
    Move bestMove = null;

    MoveEnumeration moves = node.getMoves(toPlay);
    while(moves.moreMoves()) {
       Move move = moves.nextMove();
      double moveScore = Math.random();
      if (moveScore > bestScore) {
        bestScore = moveScore;
        bestMove = move;
      }
    }

    return bestMove;

  }
}
