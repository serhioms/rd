package games.checkers;

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

import java.util.*;

public class Minimax {

    public static int score(NodeInterface node, boolean maximize, int depth) {
        // if this node has children - then
        // make it the max of them
        // otherwise it must be a leaf node
        // so - return its own score

        // the real problem here is defining the
        // set of possible moves for this one

        if (node.children().size()==0 || (depth == 0))
            return node.score();
        else
            return bestScore(node.children(), maximize, depth);
    }

    private static int bestScore(List<NodeInterface> children, boolean maximize, int depth) {

        // initialise it to the worst
        int best = worst(maximize);

        for (int i=0, max=children.size(); i<max; i++) {
            NodeInterface child = (NodeInterface) children.get(i);
            int childScore = score(child, !maximize, depth - 1);
            if (betterThan(childScore, best, maximize))
                best = childScore;
        }
        return best;
    }

    private static int worst(boolean maximize) {
        if (maximize)
            return Integer.MIN_VALUE;
        else
            return Integer.MAX_VALUE;
    }

    private static boolean betterThan(int x, int y, boolean maximize) {
        return ((x > y) && maximize) || ((x < y) && !maximize);
    }
}
