//
//  Minimax.java
//  Checkers
//
//  Created by tom on Sun Apr 07 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//
package games.board.english;


public class Minimax implements Player {

    private StaticEvaluator eval;
    private int depth;
    private double[] values = new double[Game.MAXBRANCHES];
    private int nValues = 0;
    private boolean verbose;


    Minimax(StaticEvaluator evaluator, int depth, boolean verbose) {
    
        eval = evaluator;
        this.depth = depth;
        this.verbose = verbose;
        
    } // Minimax constructor
    
    
    public Game pickMove(Game game) { 
    
    //  Move reordering not implemented.
    
        double alpha = Game.MAX_LOSS;
        double beta = Game.MIN_LOSS;
        Game[] next = game.possible();
        
        if (next.length == 0)
            return null;
            
        Game best = next[0];
        
        for (int i = 0; i < next.length; i++) {
            double score = alphaBeta(next[i], alpha, beta, depth - 1);
            if (verbose) {
                next[i].printLastTurn();
                System.out.println(" scores " + score);
            }
            if (game.maximize() && score > alpha) {
                best = next[i];
                alpha = score;
            } else if (!game.maximize() && score < beta) {
                best = next[i];
                beta = score;
            }
        }
       
        for (int i = 0; i < next.length; i++)
            if (next[i] != best)
                Game.deallocateGame(next[i]);
        
        Game.deallocateGameVector(next);
   
        return best;
        
    } // pickMove
    
    
    private double alphaBeta(Game game, double alpha, double beta, int depth) {
        
        if (game.draw())
            return 0.0;
        
        if (!game.canMove())
            if (game.maximize())
                return Game.MAX_LOSS - depth;
            else
                return Game.MIN_LOSS + depth;

        if (depth < 1) 
            return eval.staticValue(game);

        Game[] next = game.possible();
    
        if (game.maximize())
            for (int i = 0; i < next.length; i++) {
                alpha = Math.max(alpha, alphaBeta(next[i], alpha, beta, depth - 1));
                if (alpha >= beta)
                    break;
            }
        else
            for (int i = 0; i < next.length; i++) {
                beta = Math.min(beta, alphaBeta(next[i], alpha, beta, depth - 1));
                if (alpha >= beta)
                    break;
            }
       
        for (int i = 0; i < next.length; i++)
            Game.deallocateGame(next[i]);
        
        Game.deallocateGameVector(next);

        return game.maximize() ? alpha : beta;
        
    } // alphaBeta
    
} // class Minimax
