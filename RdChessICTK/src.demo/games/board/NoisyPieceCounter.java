
package games.board;

public class NoisyPieceCounter implements StaticEvaluator {

  static PieceCounter pc = new PieceCounter();

  public double evaluate( int[][] board ) {
    return pc.evaluate( board ) + 0.1 * ( Math.random() - 0.5 );
  }

}
