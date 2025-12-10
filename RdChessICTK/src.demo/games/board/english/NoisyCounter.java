//
//  NoisyCounter.java
//  Checkers
//
//  Created by tom on Tue Apr 09 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package games.board.english;


public class NoisyCounter implements StaticEvaluator {
    
        public double staticValue(Game game) {
        
            double result = 0.0;
            
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    switch (game.board(i, j)) {
                    case -Game.KING:
                        result -= 1.5;
                        break;
                    case -Game.CHECKER:
                        result -= 1.0;
                        break;
                    case Game.KING:
                        result += 1.5;
                        break;
                    case Game.CHECKER:
                        result += 1.0;
                        break;
                    }
                    
            return result + Math.random() * 0.1 - 0.05;
                    
        } // staticValue

}
