
package games.board;

public interface MinimaxInterface {
  public double score(NodeInterface node, StaticEvaluator evaluator ,  boolean maximize, int depth);
}
