
package games.board;

import games.checkers.Board;

public class PositionalCounter implements StaticEvaluator {

  public PositionFunction man;
  public PositionFunction king;
  double noise = 0.01;

  public PositionalCounter( PositionFunction man, PositionFunction king ) {
    this.man = man;
    this.king = king;
  }

  public final double evaluate(int[][] board) {
    double count = 0;
    for (int i=0; i<board.length; i++) {
      for (int j=0; j<board[0].length; j++) {
        // set up the value for this board position
        count += f( i , j , board[i][j] , board.length);
      }
    }
    return count + Math.random() * noise;
  }


  /**
   Call the positional piece evaluator,
   but impose a 180 degree rotational symmetry.

  */

  private final double f( int x, int y, int z, int n ) {
    // Thread.sleep( 1000 );
    double fac = 1.0;
    if ( z == 0 ) return 0.0;
    if ( (z == Board.BLACK_PAWN) || (z == Board.BLACK_KING) ) {
      x = n - x - 1;
      y = n - y - 1;
      fac = -fac;
    }
    if ( ( z == Board.WHITE_PAWN ) ||
         ( z == Board.BLACK_PAWN ) )
    {
      return fac * man.f( x , y );
    }
    if ( ( z == Board.WHITE_KING ) ||
         ( z == Board.BLACK_KING ) )
    {
      return fac * king.f( x , y );
    }
    System.out.println("Should not be here!!!");
    return 0.0;
  }
}
