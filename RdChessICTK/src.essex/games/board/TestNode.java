
package games.board;

import java.util.*;

public class TestNode { // implements NodeInterface {
  private Vector children;
  private int score;
  public static boolean MAXIMIZE = true;
  public static boolean MINIMIZE = true;

  public TestNode(int score) {
    this.score = score;
    children = new Vector();
  }

  public void addChild(GameNode child) {
    children.addElement(child);
  }

  public Vector children() {
    return children;
  }

  public int score() {
    // if this node has children - then
    // make it the max of them
    // otherwise it must be a leaf node
    // so - return its own score

    // the real problem here is defining the
    // set of possible moves for this one

    if (children.size() > 0)
      throw new RuntimeException("Called score on non-leaf node");
    return score;
  }

  public boolean gameOver() {
    return children.size() == 0;
  }

  public static void main(String[] args) {

    TestNode l1 = new TestNode(10);
    TestNode l2 = new TestNode(20);
    TestNode l3 = new TestNode(30);
    TestNode l4 = new TestNode(40);

    TestNode p1 = new TestNode(100);
    TestNode p2 = new TestNode(200);

    TestNode root = new TestNode(300);

    root.addChild(p1);
    root.addChild(p2);

    p1.addChild(l1);
    p1.addChild(l3);

    p2.addChild(l2);
    p2.addChild(l4);

    System.out.println( Integer.MAX_VALUE + " : " + Integer.MIN_VALUE );

    Minimax m = new Minimax();
    System.out.println( "Best (maximizer) = " + m.score(root, true, 2) );
    System.out.println( "Best (minimizer) = " + m.score(root, false, 2) );
  }
}
