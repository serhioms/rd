package ca.mss.rd.chessictk.game;

import java.util.HashMap;
import java.util.Map;


import ca.mss.rd.chessictk.engine.ChessEngine;
import ca.mss.rd.chessictk.engine.impl.RandomChessEngine;
import ca.mss.rd.chessictk.engine.impl.SwampChessEngine;
import ca.mss.rd.util.UtilMisc;


public class WorkflowChessHelper {

	public static final String module = WorkflowChessHelper.class.getName();

	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	// Game storage cache
	private static boolean pgnCache = false;
	private static Map<String, String> pngCache = new HashMap<String, String>();  
	private static Map<String, ChessGame> chessCache = new HashMap<String, ChessGame>();  

	private static Map<String, ChessEngine> randomCache = new HashMap<String, ChessEngine>();  
	private static Map<String, ChessEngine> swampCache = new HashMap<String, ChessEngine>();  
	
	
	final public static Map getPlayerInfo(String partyId){
    	return UtilMisc.toMap("partyId", partyId);
    }

	final public static void saveGame(ChessGame game){
		if( pgnCache ){
		   	pngCache.put(game.getProperty("tableId"), game.serializeToPgn().getPgnGame());
		} else {
			chessCache.put(game.getProperty("tableId"), game);
		}
     }
    
	final public static ChessGame selectGame(String tableId){
		if( pgnCache ){
	    	return new ChessGame(pngCache.get(tableId));
		} else {
	    	return chessCache.get(tableId);
		}
	}
    
    final public static ChessEngine getRandomChessEngine(String tableId) {
    	ChessEngine engine = randomCache.get(tableId);
    	if( engine == null ){
    		engine = new RandomChessEngine(selectGame(tableId));
    		randomCache.put(tableId, engine);
    	}
    	return engine;
    }

    final public static ChessEngine getSwampChessEngine(String tableId) {
    	ChessEngine engine = swampCache.get(tableId);
    	if( engine == null ){
    		engine = new SwampChessEngine(selectGame(tableId));
    		swampCache.put(tableId, engine);
    	}
    	return engine;
    }

}
