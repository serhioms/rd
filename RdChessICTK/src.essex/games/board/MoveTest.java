
package games.board;

import xml.serial.*;

import java.io.*;

public class MoveTest {
  public static void main(String[] args) throws Exception {

    SquareSequenceMove ssm = new SquareSequenceMove();
    RemoteMoveRequest rmr = new RemoteMoveRequest( ssm , 1 , true );
    PrintWriter pw = new PrintWriter( System.out );
    XMLWriter out = new JSXWriter( pw );
    out.writeObject( rmr );

  }
}

