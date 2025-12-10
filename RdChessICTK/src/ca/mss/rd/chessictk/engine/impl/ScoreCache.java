/**
 * 
 */
package ca.mss.rd.chessictk.engine.impl;


import ictk.boardgame.chess.ChessMove;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mss.rd.chessictk.game.ChessGame;


/**
 * @author smoskov
 *
 */
public class ScoreCache {

	public static final String module = ScoreCache.class.getName();
	static final long serialVersionUID = module.hashCode();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	private ChessGame game;
	private Map<Integer,Map<String, Long>> scoreCache; 

	public ScoreCache(ChessGame game) {
		this.game = game;
		this.scoreCache = new HashMap<Integer,Map<String, Long>>();
		
		initialize();
	}
	

	// get score cache of certain half-move number with instantiation 
	final public Map<String, Long> getScoreCache(int mn2){
		Map<String,Long> scoreCacheMN2 = scoreCache.get(mn2);
		if( scoreCacheMN2 == null ){ 
			scoreCacheMN2 = new HashMap<String,Long>();
			scoreCache.put(mn2, scoreCacheMN2);
		}
		return scoreCacheMN2;
	}
	
	// remove score cache for the half-move number level less then <hawDeep> moves ago
	final public void clean(int hawDeep) {
		Map<String,Long> scoreCache3 = scoreCache.remove(game.getCurrentMoveNumber2()-hawDeep);
		if( scoreCache3 != null ) scoreCache3.clear();
	}

	// we do not need cache from 3 moves ago inclusive
	final public void clean3() {
		clean(3);
	}
	
	// put score into cache of certain half-move number 
	final public void put(ChessMove evalMove){
		getScoreCache(game.getCurrentMoveNumber2()).put(evalMove.toStringNV(), evalMove.score);
	}
	

	// pre-populate scores to legal moves from cache
	final public boolean populateScores(List<ChessMove> moveList){
		
		boolean isPopulated = false;

		int mn2 = game.getCurrentMoveNumber2();
		
		Map<String,Long> scoreCacheMN2 = getScoreCache(mn2); 	// current cache
		Map<String,Long> scoreCacheMN22 = getScoreCache(mn2-2); 	// cache from previous level
		
		if( !scoreCacheMN2.isEmpty() || !scoreCacheMN22.isEmpty()){
			for(int i=0, max=moveList.size(); i<max; i++ ){
				ChessMove move = moveList.get(i);
				String key = move.toStringNV();

				Object scoreValue = scoreCacheMN2.get(key);
				if( scoreValue != null ) {
					move.score = ((Long )scoreValue).longValue();
					isPopulated = true;
				} else if( !scoreCacheMN22.isEmpty() ){
					scoreValue = scoreCacheMN22.get(key);
					if( scoreValue != null ){ 
						move.score = ((Long )scoreValue).longValue();
						isPopulated = true;
					}
				}
			}
		}
		return isPopulated;
	}
	
	// sort legal moves by score from cache
	final boolean sort(List<ChessMove> moveList, boolean maximize) {
		if( populateScores(moveList) ){ 
			game.sortMoves(moveList, maximize);
			return true;
		}
		return false;
	}
	
	
	private void initialize() {
		
		Map<String,Long> whiteCach1 = new HashMap<String,Long>(); 
		
		whiteCach1.put("e2-e3", 64L);
		whiteCach1.put("e2-e4", 64L);
		whiteCach1.put("d2-d4", 62L);
		whiteCach1.put("d2-d3", 60L);
		whiteCach1.put("b1-c3", 57L);
		whiteCach1.put("c2-c4", 57L);
		whiteCach1.put("c2-c3", 57L);
		whiteCach1.put("b2-b4", 57L);
		whiteCach1.put("a2-a4", 56L);
		whiteCach1.put("b2-b3", 56L);
		whiteCach1.put("g1-f3", 56L);
		whiteCach1.put("g2-g3", 56L);
		whiteCach1.put("g2-g4", 56L);
		whiteCach1.put("h2-h4", 56L);
		whiteCach1.put("b1-a3", 55L);
		whiteCach1.put("h2-h3", 55L);
		whiteCach1.put("f2-f4", 55L);
		whiteCach1.put("f2-f3", 55L);
		whiteCach1.put("a2-a3", 55L);
		whiteCach1.put("g1-h3", 54L);
		
		
		Map<String,Long> blackCach1 = new HashMap<String,Long>();
		
		blackCach1.put("e7-e5", 62L);
		blackCach1.put("e7-e6", 60L);
		blackCach1.put("g7-g6", 56L);
		blackCach1.put("g7-g5", 55L);
		blackCach1.put("b7-b5", 54L);
		blackCach1.put("g8-f6", 54L);
		blackCach1.put("c7-c5", 54L);
		blackCach1.put("b8-c6", 53L);
		blackCach1.put("b7-b6", 53L);
		blackCach1.put("b8-a6", 52L);
		blackCach1.put("c7-c6", 52L);
		blackCach1.put("a7-a6", 52L);
		blackCach1.put("a7-a5", 52L);
		blackCach1.put("h7-h6", 52L);
		blackCach1.put("h7-h5", 52L);
		blackCach1.put("g8-h6", 51L);
		blackCach1.put("d7-d5", 44L);
		blackCach1.put("d7-d6", 44L);
		blackCach1.put("f7-f6", 40L);
		blackCach1.put("f7-f5", 40L);
		
		scoreCache.put(1, whiteCach1); // #1
		scoreCache.put(2, blackCach1); // #2
	}
}
