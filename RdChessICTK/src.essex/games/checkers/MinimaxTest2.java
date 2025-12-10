
package games.checkers;

import java.util.*;

class GameNodeImp implements NodeInterface {
  private List<NodeInterface> children;
  private int score;
  public static boolean MAXIMIZE = true;
  public static boolean MINIMIZE = true;

  public GameNodeImp(int score) {
    this.score = score;
    children = new ArrayList<NodeInterface>();
  }

  public void addChild(NodeInterface child) {
    children.add(child);
  }

  public List<NodeInterface> children() {
    return children;
  }

  public int score() {
    return score;
  }
}

/**

 One of the first things to note is that we have
 not even yet done this properly.  Have not defined the
 purpose of the search.

*/

public class MinimaxTest2 {

  public static void main(String[] args) {

    NodeInterface l1 = new GameNodeImp(10);
    NodeInterface l2 = new GameNodeImp(20);
    NodeInterface l3 = new GameNodeImp(30);
    NodeInterface l4 = new GameNodeImp(40);

    NodeInterface p1 = new GameNodeImp(100);
    NodeInterface p2 = new GameNodeImp(200);

    NodeInterface root = new GameNodeImp(300);

    root.addChild(p1);
    root.addChild(p2);

    p1.addChild(l1);
    p1.addChild(l3);

    p2.addChild(l2);
    p2.addChild(l4);

    System.out.println( Integer.MAX_VALUE + " : " + Integer.MIN_VALUE );
    System.out.println( "Best (maximizer) = " + Minimax.score(root, true, 4) );
    System.out.println( "Best (minimizer) = " + Minimax.score(root, false, 4) );

  }
}
