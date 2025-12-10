/**
 * 
 */
package ca.mss.rd.chessictk.wkfrepo;

import ictk.boardgame.chess.ChessGameInfo;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessResult;

import java.util.Map;


import ca.mss.rd.chessictk.game.ChessGame;
import ca.mss.rd.chessictk.game.WorkflowChessHelper;
import ca.mss.rd.util.UtilValidate;

import ca.mss.rd.workflow.dynamic.service.WkfProcedureDelegator;


/**
 * @author smoskov
 *
 */
public class ChessProcedure implements WkfProcedureDelegator {

	final public static String module = ChessProcedure.class.getName();
	static final long serialVersionUID = module.hashCode();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	private static int table_id_sequence = 1000000;
	
	final public void chessAssignOwner(Map context){
		//if( logger.isInfoEnabled() ) logger.info("chessAssignOwner [context="+context+"]");
		
    	String tableId = (String )context.get("tableId");
    	String ownerPartyId = (String )context.get("ownerPartyId");

		if( UtilValidate.isEmpty(ownerPartyId) ){
			ownerPartyId = "mss";
			context.put("ownerPartyId", ownerPartyId);
		}
		
		// Set owner party
		String roleTypeId = "TABLE_OWNER";
		String partyId = ownerPartyId;
		
		context.put("partyId", partyId);

		if( logger.isInfoEnabled() ) logger.info("chessAssignOwner [tableId="+tableId+"][partyId="+partyId+"][roleTypeId="+roleTypeId+"]");
	}

	final public  void chessAssignWhite(Map context){
		//if( logger.isInfoEnabled() ) logger.info("chessAssignWhite [context="+context+"]");
		
    	String tableId = (String )context.get("tableId");
    	String ownerColor = (String )context.get("ownerColor");
    	String ownerPartyId = (String )context.get("ownerPartyId");
    	String rivalPartyId = (String )context.get("rivalPartyId");

		String roleTypeId = "WHITE_PLAYER";
		String partyId = "";

		// Set white party
		if( "white".equals(ownerColor) )
			partyId = ownerPartyId;
		else if( "black".equals(ownerColor) )
			partyId = rivalPartyId;
		else
	        throw new RuntimeException("Unexpected owner color id: "+ownerColor);

		context.put("partyId", partyId);
		
		if( logger.isInfoEnabled() ) logger.info("chessAssignWhite [tableId="+tableId+"][partyId="+partyId+"][roleTypeId="+roleTypeId+"]");
	}

	final public  void chessAssignBlack(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessAssignBlack [context="+context+"]");
		
    	String tableId = (String )context.get("tableId");
    	String ownerColor = (String )context.get("ownerColor");
    	String ownerPartyId = (String )context.get("ownerPartyId");
    	String rivalPartyId = (String )context.get("rivalPartyId");

		String roleTypeId = "BLACK_PLAYER";
		String partyId = "";
		
		// Set black party
		if( "white".equals(ownerColor) )
			partyId = rivalPartyId;
		else if( "black".equals(ownerColor) )
			partyId = ownerPartyId;
		else
	        throw new RuntimeException("Unexpected owner color id: "+ownerColor);
		
		context.put("partyId", partyId);
		
		if( logger.isInfoEnabled() ) logger.info("chessAssignBlack [tableId="+tableId+"][partyId="+partyId+"][roleTypeId="+roleTypeId+"]");
	}

	final public  void chessCreateNew(Map context){
		//if( logger.isInfoEnabled() ) logger.info("chessCreateNew [context="+context+"]");
		
        String ownerColor = (String )context.get("ownerColor");
        String ownerPartyId = (String )context.get("ownerPartyId");
        String rivalPartyId = (String )context.get("rivalPartyId");

        String rivalColor = "white".equals(ownerColor)?"black":"white";

        String tableId = ""+table_id_sequence++;
		context.put("tableId", tableId);
		
		// Create new game
    	ChessGame game = new ChessGame();

    	// Set properties
    	game.setProperty("tableId", tableId);
    	game.setPlayer(ownerColor, WorkflowChessHelper.getPlayerInfo(ownerPartyId));
    	game.setPlayer(rivalColor, WorkflowChessHelper.getPlayerInfo(rivalPartyId));
    	
    	// And save it
    	WorkflowChessHelper.saveGame(game);
    	
		if( logger.isInfoEnabled() ) logger.info("chessCreateNew [tableId="+tableId+"]["+ownerColor+"="+ownerPartyId+"]["+rivalColor+"="+rivalPartyId+"]");
	}

	final public  void chessNotifyWhite(Map context){
		if( logger.isInfoEnabled() ) logger.info("chessNotifyWhite [context="+context+"]");
/*
        String tableId = (String )context.get("tableId");

    	String xmlGame = ChessHelper.selectGame(tableId).populateIds().toXml();
    	
    	if( logger.isInfoEnabled() ) logger.info("chessNotifyWhite [tableId="+tableId+"]\n"+xmlGame);
*/   		
	}

	final public  void chessNotifyBlack(Map context){
		if( logger.isInfoEnabled() ) logger.info("chessNotifyBlack [context="+context+"]");
	}

	final public  void chessAddMove(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessAddMove [context="+context+"]");
		
        String tableId = (String )context.get("tableId");
        ChessMove move = (ChessMove )context.get("move");
        
		// Select and add
		ChessGame game = WorkflowChessHelper.selectGame(tableId);
		game.addMove(move);
		
		// Refresh status 
		String resStatus = game.getState();
		context.put("resStatus", resStatus);
		
		// Save game
		WorkflowChessHelper.saveGame(game);

   		if( logger.isInfoEnabled() ) logger.info("chessAddMove [tableId="+tableId+"][move="+move+"][status="+resStatus+"][captured="+game.getCapturedPiecesAmount()+"][moves="+game.getCurrentMoveNumber()+"]");
	}

	final public  void chessValidateMove(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessValidateMove [context="+context+"]");
		
		String tableId = (String )context.get("tableId");
		String movestr = (String )context.get("movestr");

        // Validate now
		boolean isValid = WorkflowChessHelper.selectGame(tableId).isLegal(movestr);
		context.put("isValid", Boolean.toString(isValid));
		
   		if( logger.isInfoEnabled() ) logger.info("chessValidateMove [tableId="+tableId+"][movestr="+movestr+"][isValid="+isValid+"]");
	}

	final public  void chessNextMove(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessNextMove [context="+context+"]");
		
       	String tableId = (String )context.get("tableId");
    	String ownerColor = (String )context.get("ownerColor");
    	String wichTurn = (String )context.get("wichTurn");

    	// Refresh turn
		if( WorkflowChessHelper.selectGame(tableId).isBlackSide() )
			wichTurn = ("white".equals(ownerColor))? "rival": "owner";
		else
			wichTurn = ("white".equals(ownerColor))? "owner": "rival";

		context.put("wichTurn", wichTurn);
		
   		if( logger.isInfoEnabled() ) logger.info("chessNextMove [tableId="+tableId+"][wichTurn="+wichTurn+"]");
	}

	final public  void chessPosition(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessPosition [context="+context+"]");
		
        String tableId = (String )context.get("tableId");
        ChessGame game = WorkflowChessHelper.selectGame(tableId);
        
   		if( logger.isInfoEnabled() ) logger.info(game.getScores()+"\n"+game.toString());
    	
	}

	final public  void chessCheckUser(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessCheckUser [context="+context+"]");
		
        String tableId = (String )context.get("tableId");
	   	Object userId = context.get("userId");
        Object ownerPartyId = context.get("ownerPartyId");
        Object rivalPartyId = context.get("rivalPartyId");
        
        // Computer or human?
		if( userId.equals(ownerPartyId) || userId.equals(rivalPartyId) ) {
			context.put("isPlayer", "true");
			context.put("playerId", userId);
			if( logger.isInfoEnabled() ) logger.info("chessCheckUser [tableId="+tableId+"][isPlayer=true][playerId="+userId+"]");
		} else {
			context.put("isPlayer", "false");
			if( logger.isInfoEnabled() ) logger.info("chessCheckUser [tableId="+tableId+"][isPlayer=false]");
		}

	}

	final public  void chessGenerateMove(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessGenerateMove [context="+context+"]");
		
        String tableId = (String )context.get("tableId");
        String wichTurn = (String )context.get("wichTurn");
        String ownerEngine = (String )context.get("ownerEngine");
        String rivalEngine = (String )context.get("rivalEngine");

		// Select game
		ChessGame game = WorkflowChessHelper.selectGame(tableId);

        ChessMove move = null;
		
        // Select engine {chessRandomEngine, chessSwampEngine} and generate next move
        String engine = "owner".equals(wichTurn)? ownerEngine: rivalEngine;
        if( "chessRandomEngine".equals(engine) )
    		move = WorkflowChessHelper.getRandomChessEngine(tableId).generateMove();
        else if( "chessSwampEngine".equals(engine) )
    		move = WorkflowChessHelper.getSwampChessEngine(tableId).generateMove();
        else
	        throw new RuntimeException("Unknown engine: "+engine);
        	
        /*
       	if( "owner".equals(wichTurn) )
    		return dispatcher.runSync(ownerEngine, UtilMisc.toMap("tableId", tableId));
    	else
    		return dispatcher.runSync(rivalEngine, UtilMisc.toMap("tableId", tableId));
    	*/
        	
		context.put("reqStatus", "move");
		context.put("move", move);

		if( logger.isInfoEnabled() ) logger.info("chessGenerateMove [tableId="+tableId+"][move="+move+"][turn="+wichTurn+"][ownerEngine="+ownerEngine+"][rivalEngine="+rivalEngine+"]");
	}

	
	
	final public  void chessResign(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessResign [context="+context+"]");
		
        String tableId = (String )context.get("tableId");
        Object playerId = context.get("playerId");
        Object ownerColor = context.get("ownerColor");
        Object rivalPartyId = context.get("rivalPartyId");
        Object ownerPartyId = context.get("ownerPartyId");
        
    	// Set resign
    	String isResigned = "false";
		if( "white".equals(ownerColor) ) {
			if( playerId.equals(ownerPartyId) ){
				ChessGame game = WorkflowChessHelper.selectGame(tableId);
		    	ChessGameInfo info = game.getGameInfo();
    			info.setResult(new ChessResult(ChessResult.BLACK_WIN));
    			WorkflowChessHelper.saveGame(game);

    			isResigned = "true";
			} else if( playerId.equals(rivalPartyId) ){
				ChessGame game = WorkflowChessHelper.selectGame(tableId);
		    	ChessGameInfo info = game.getGameInfo();
				info.setResult(new ChessResult(ChessResult.WHITE_WIN));
    			WorkflowChessHelper.saveGame(game);

    			isResigned = "true";
			}
		} else if( "black".equals(ownerColor) ) {
			if( playerId.equals(ownerPartyId) ){
				ChessGame game = WorkflowChessHelper.selectGame(tableId);
		    	ChessGameInfo info = game.getGameInfo();
				info.setResult(new ChessResult(ChessResult.WHITE_WIN));
    			WorkflowChessHelper.saveGame(game);

    			isResigned = "true";
			} else if( playerId.equals(rivalPartyId) ){
				ChessGame game = WorkflowChessHelper.selectGame(tableId);
		    	ChessGameInfo info = game.getGameInfo();
    			info.setResult(new ChessResult(ChessResult.BLACK_WIN));
    			WorkflowChessHelper.saveGame(game);

    			isResigned = "true";
			}
		}
		
		context.put("isResigned", isResigned);

		if( logger.isInfoEnabled() ) logger.info("chessResign [tableId="+tableId+"][ownerColor="+ownerColor+"][playerId="+playerId+"][isResigned="+isResigned+"]");
	}

	final public  void chessDraw(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessDraw [context="+context+"]");
		
        String tableId = (String )context.get("tableId");
        
    	// Set draw
		ChessGame game = WorkflowChessHelper.selectGame(tableId);
    	ChessGameInfo info = (ChessGameInfo )game.getGameInfo();
		info.setResult(new ChessResult(ChessResult.DRAW));
		WorkflowChessHelper.saveGame(game);
		
		String isDrawed = "true";
		context.put("isDrawed", isDrawed);

		if( logger.isInfoEnabled() ) logger.info("chessDraw [tableId="+tableId+"][isDrawed="+isDrawed+"]");
	}
	
	final public  void chessCheckmate(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessCheckmate [context="+context+"]");
		
        String tableId = (String )context.get("tableId");

    	ChessGame game = WorkflowChessHelper.selectGame(tableId);
        
    	// Set if checkmate
    	String isCheckmated = "";
    	if( game.isCheckmate() ){

    		ChessGameInfo info = (ChessGameInfo )game.getGameInfo();
        	if( game.isBlackSide() )
				info.setResult(new ChessResult(ChessResult.BLACK_WIN));
			else
				info.setResult(new ChessResult(ChessResult.WHITE_WIN));
        	WorkflowChessHelper.saveGame(game);

        	isCheckmated = "true";
		} else {
			isCheckmated = "false";
		}

		context.put("isCheckmated", isCheckmated);

		if( logger.isInfoEnabled() ) logger.info("chessCheckmate [tableId="+tableId+"][isCheckmated="+isCheckmated+"]");
	}
	
	final public  void chessStalemate(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessStalemate [context="+context+"]");
		
        String tableId = (String )context.get("tableId");

    	ChessGame game = WorkflowChessHelper.selectGame(tableId);

    	// Set if stalemate
    	String isStalemated = "";
    	if( game.isStalemate() ){

   			ChessGameInfo info = (ChessGameInfo )game.getGameInfo();
			info.setResult(new ChessResult(ChessResult.DRAW));
			WorkflowChessHelper.saveGame(game);

			isStalemated = "true";
		} else {
			isStalemated = "false";
		}

		context.put("isStalemated", isStalemated);

		if( logger.isInfoEnabled() ) logger.info("chessCheckmate [tableId="+tableId+"][isStalemated="+isStalemated+"]");
	}

	final public  void chessScores(Map context){
		// if( logger.isInfoEnabled() ) logger.info("chessScores [context="+context+"]");
		
        String tableId = (String )context.get("tableId");

        ChessGame game = WorkflowChessHelper.selectGame(tableId);

        // get scores
		String gameScores = game.getScores();
		context.put("gameScores", gameScores);

		int captured = game.getCapturedPiecesAmount();
		int moves = game.getCurrentMoveNumber();

		if( logger.isInfoEnabled() ){
			logger.info("chessScores [tableId="+tableId+"][gameScores="+gameScores+"][captured="+captured+"][moves="+moves+"]");
		}
	}
	
}
