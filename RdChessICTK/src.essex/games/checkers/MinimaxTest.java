package games.checkers;

import java.util.*;

/**
 * Okay - really need to get a simple tree search implemented - then develop it
 * into a Minimax search.
 * 
 * The first objective of the tree search is to choose the best alternative from
 * a given point.
 * 
 * Each node in the Tree must be able to return a Vector of nodes as its
 * children.
 * 
 * Okay - this works fine as it stands - but how could we make it minimax?
 * 
 * Could have a multiplier of +/- 1.
 * 
 * Or - could have a boolean thing
 */

class GameNode {
	
	private Vector children;
	private int score;
	
	public static boolean MAXIMIZE = true;
	public static boolean MINIMIZE = true;

	public GameNode(int score) {
		this.score = score;
		children = new Vector();
	}

	public void addChild(GameNode child) {
		children.addElement(child);
	}

	public Vector children() {
		return children;
	}

	public int score(boolean maximize) {
		// if this node has children - then
		// make it the max of them
		// otherwise it must be a leaf node
		// so - return its own score

		// the real problem here is defining the
		// set of possible moves for this one

		if (children.size() == 0)
			return score;
		else
			return bestScore(children, maximize);

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

	private int bestScore(Vector children, boolean maximize) {

		// initialise it to the worst
		int best = worst(maximize);

		for (int i = 0; i < children.size(); i++) {
			GameNode child = (GameNode) children.elementAt(i);
			int childScore = child.score(!maximize);
			if (betterThan(childScore, best, maximize))
				best = childScore;
		}
		return best;
	}
}

/**
 * One of the first things to note is that we have not even yet done this
 * properly. Have not defined the purpose of the search.
 */

public class MinimaxTest {

	public static void main(String[] args) {

		GameNode l1 = new GameNode(10);
		GameNode l2 = new GameNode(20);
		GameNode l3 = new GameNode(30);
		GameNode l4 = new GameNode(40);

		GameNode p1 = new GameNode(100);
		GameNode p2 = new GameNode(200);

		GameNode root = new GameNode(300);

		root.addChild(p1);
		root.addChild(p2);

		p1.addChild(l1);
		p1.addChild(l3);

		p2.addChild(l2);
		p2.addChild(l4);

		System.out.println(Integer.MAX_VALUE + " : " + Integer.MIN_VALUE);
		System.out.println("Best (maximizer) = " + root.score(true));
		System.out.println("Best (minimizer) = " + root.score(false));
	}
}
