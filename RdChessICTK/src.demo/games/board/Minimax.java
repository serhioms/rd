
package games.board;

/**

 This class provides an implementation of
 a general minimax search.

 The idea is to expand the game tree to the
 maximum depth, then propagate back the
 minimum of the maximum etc - alternating
 which at each alternate level.

 depth starts off at the desired number of levels
 to search - when depth is zero, or when a node has
 no children then the score of that node is returned.

*/

// import java.util.*;

public class Minimax implements MinimaxInterface {

  public double score(NodeInterface node, boolean maximize, int depth) {
    return staticScore(node, maximize, depth);
  }

  public static double staticScore(NodeInterface node, boolean maximize, int depth) {
    // if this node has children - then
    // make it the max of them
    // otherwise it must be a leaf node
    // so - return its own score

    // the real problem here is defining the
    // set of possible moves for this one

    if ( node.gameOver() || (depth == 0) ) {
      // System.out.println("----------\n" + node + " scores " + node.score() + "\n---------\n");
      // utilities.Wait.Input();
      return node.score();
    }
    else
      return bestScore(node, maximize, depth);
  }

  private static double bestScore(NodeInterface node, boolean maximize, int depth) {

    // initialise it to the worst
    double best = worst(maximize);

    MoveEnumeration moves = node.getMoves(node.getPlayer(maximize));

    while ( moves.moreMoves() ) {
      Move m = moves.nextMove();
      node.applyMove(m, node.getPlayer(maximize) );
      // System.out.println(node + " scores " + node.score());
      double childScore = staticScore(node, !maximize, depth-1);
      // utilities.Wait.Input();
      node.retractMove(m);
      if ( betterThan(childScore, best, maximize) )
        best = childScore;
    }
    return best;
  }

  // this should perhaps be a  method of NodeInterface
  public static double worst(boolean maximize) {
    if (maximize)
      return Integer.MIN_VALUE;
    else
      return Integer.MAX_VALUE;
  }

  public static boolean betterThan(double x, double y, boolean maximize) {
    return ((x > y) && maximize) || ((x < y) && !maximize);
  }
}
