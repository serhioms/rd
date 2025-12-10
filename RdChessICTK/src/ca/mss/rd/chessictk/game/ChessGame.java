package ca.mss.rd.chessictk.game;

import ictk.boardgame.chess.ChessGameInfo;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessPiece;
import ictk.boardgame.chess.ChessPlayer;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;






import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeEvaluator;
import ca.mss.rd.tools.sorter.FastQSortAlgorithm;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilValidate;



public class ChessGame extends ChessICTK {
	
	final private static int CHESS_SET_SIZE = 32;
	
	final private static FastQSortAlgorithm<ChessMove> SORTER = new FastQSortAlgorithm<ChessMove>(); 
	
	/**
	 * 
	 */
	public ChessGame() {
		super();
	}

	public ChessGame(String pgn) {
		super(pgn);
	}

	// Standard valuations					  K, Q, R, B, N, P
	final protected static int COST[] = new int[] { 0, 9, 5, 3, 3, 1};
	
	final private int getIndex(byte index){
		return index >= ChessPiece.BLACK_OFFSET? index - ChessPiece.BLACK_OFFSET: index; 
	}
	
	final public int getCost(ChessPiece piece){
		return COST[getIndex(piece.getIndex())];
	}
	     
	final private int getMobility(ChessPiece piece){
		return piece.getLegalDests().size();
	}
	     
	final private int getMinMobility(ChessPiece[] pieces){
		int min = Integer.MAX_VALUE;
		int index = -1;
		for(int i=0; i<pieces.length; i++){
			if( pieces[i] != null ){
				int mobility = getMobility(pieces[i]);
				if( mobility < min ){
					min = mobility;
					index = i;
				}
			}
		}
		pieces[index] = null;
		return min;
	}
	     
	final private boolean hasMoreTreats(ChessPiece[] treats){
		for(int i=0; i<treats.length; i++)
			if( treats[i] != null )
				return true;
		return false;
	}
	     
	final public int getQuiescence(ChessPiece piece){
		int mobility = getMobility(piece);

		ChessPiece[] guards = getBoard().getGuards(piece);
		ChessPiece[] treats = getBoard().getThreats(piece);
		
    	if( treats == null ){
			// mobility = mobility;
		} else if( guards == null ){
			mobility = -mobility;
		} else {
			mobility = -mobility;
			for(int j=0; j<treats.length && j<guards.length; j++ ){
				mobility += getMinMobility(treats);
				if( hasMoreTreats(treats) )
					mobility -= getMinMobility(guards);
			}
		}
		return mobility;
	}
	     
    final public  String getScores(){
		if( getResult().isBlackWin() )
			return "0-1";
		else if( getResult().isWhiteWin() )
			return "1-0";
		else if( getResult().isDraw() )
			return "1/2-1/2";
		else 
			return "N/A";
   }
	
    final public  String getState(){
		if( isCheckmate() )
			return "checkmate";
		else if( isStalemate() )
			return "stalemate";
		else if( isDraw() )
			return "draw";
		else 
			return "nextmove";
    }

    final public boolean isLegal(String move) {
    	StringTokenizer st = new StringTokenizer(move, " .,;\n\t");
        while (st.hasMoreTokens()) {
        	String oneMove = st.nextToken();
        	if( !UtilValidate.isEmpty(oneMove) ){
            	if( !UtilValidate.isInteger(oneMove) )
                    return getBoard().isLegalMove(stringToMove(oneMove));
            	else
            		continue;
        	} else
        		continue;
        }
        return false;
    }
	
	final public int sideSign() {
		return isBlackSide()?-1: 1;
	}
 
	final public String sideColor() {
		return isBlackSide()?"Black": "White";
	}
 
	final public String color(ChessMove move) {
		return move.isBlackMove()?"Black": "White";
	}
 
    final public void setPlayer(String color, Map<String,Object> pros){
    	if( "white".equals(color) ){
    		getGameInfo().setWhite(populatePlayer(pros));
    	} else if( "black".equals(color) ){
    		getGameInfo().setBlack(populatePlayer(pros));
    	} else {
    		throw new RuntimeException("Can not set player due to wrong team specified [color="+color+"]");
    	}
    }

    final public static ChessPlayer populatePlayer(Map<String,Object> pros){
    	
    	String partyId = (String )pros.get("partyId");
    	
    	String firstName = (String )pros.get("firstName");
    	String lastName = (String )pros.get("lastName");
    	String rating = (String )pros.get("rating");
    	String title = (String )pros.get("title");
    	
    	if( UtilValidate.isEmpty(firstName) ){
    		firstName = partyId.toString().substring(0 ,1).toUpperCase()+".";
    	}
    		
    	if( UtilValidate.isEmpty(lastName) ){
    		lastName = partyId.toString().substring(0 ,1).toUpperCase()+partyId.toString().substring(1);
    	}
    		
		ChessPlayer player = new ChessPlayer();
		player.setFirstName(firstName);
		player.setLastName(lastName);

		if( UtilValidate.isNotEmpty(rating) ){
			player.setRating(Integer.parseInt(rating));
		}
		
		if( UtilValidate.isNotEmpty(title) ){
			player.setTitle(Integer.parseInt(title));
		}
		
		return player;
    }

    final public void populateInfo(Map<String,Object> param){
    	
    	ChessGameInfo info = getGameInfo();
    	
		// populate current date
    	Calendar now = UtilDateTime.toCalendar(UtilDateTime.nowTimestamp());
		info.setDay(now.get(Calendar.DAY_OF_MONTH));
		info.setMonth(now.get(Calendar.MONTH));
		info.setYear(now.get(Calendar.YEAR));
    	//info.setDate(now); // does not work...

		
		// populate auxiliary properties
		Iterator<?> params = param.entrySet().iterator();
		while( params.hasNext() ){
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry )params.next();
			setProperty((String )entry.getKey(), (String )entry.getValue());

		}	    	
    }

    final public void setProperty(String key, String value){
		if( "event".equals(key) )
			getGameInfo().setEvent(value);
		else if( "site".equals(key) )
			getGameInfo().setSite(value);
		else if( "round".equals(key) )
			getGameInfo().setRound(value);
		else if( "subround".equals(key) )
			getGameInfo().setSubRound(value);
		else if( "initial".equals(key) )
			getGameInfo().setTimeControlInitial(Integer.parseInt(value));
		else if( "increment".equals(key) )
			getGameInfo().setTimeControlIncrement(Integer.parseInt(value));
		else if( "whiteRating".equals(key) )
			getGameInfo().setBlackRating(Integer.parseInt(value));
		else if( "blackRating".equals(key) )
			getGameInfo().setWhiteRating(Integer.parseInt(value));
		else if( "ECO".equals(key) )
			getGameInfo().setECO(value);
		else
			getGameInfo().getAuxilleryProperties().put(key, value);
	}
    
    final public String getProperty(String key){
		if( "event".equals(key) )
			return getGameInfo().getEvent();
		else if( "site".equals(key) )
			return getGameInfo().getSite();
		else if( "round".equals(key) )
			return getGameInfo().getRound();
		else if( "subround".equals(key) )
			return getGameInfo().getSubRound();
		else if( "initial".equals(key) )
			return Integer.toString(getGameInfo().getTimeControlInitial());
		else if( "increment".equals(key) )
			return Integer.toString(getGameInfo().getTimeControlIncrement());
		else if( "white".equals(key) )
			return Integer.toString(getGameInfo().getBlackRating());
		else if( "black".equals(key) )
			return Integer.toString(getGameInfo().getWhiteRating());
		else if( "ECO".equals(key) )
			return getGameInfo().getECO();
		else
			return getGameInfo().getAuxilleryProperties().getProperty(key);
	}
    
    final public boolean isStartTheGame() {
        return getHistory().isEmpty();
    }
    
    final public int getBoardPiecesAmount(){
    	return CHESS_SET_SIZE - getCapturedPiecesAmount();
    }
    
	final public ChessGame clearMoveCounts() {
		boolean isWhiteTeam = false;
		do {
			isWhiteTeam = !isWhiteTeam;
			Iterator<ChessPiece> iter = getTeamIterator(isWhiteTeam);
			while( iter.hasNext() ){
				ChessPiece piece=iter.next();
				piece.moveDetector = piece.moveCount; // for determine which pieces will be moved ...
			}
		} while( isWhiteTeam );
		return this;
    }
	
	final public String toString(List<ChessMove> list) {
		String str = "{";
		for (int i=0, j=0, max = list.size(); i < max; i++) {
			ChessMove move = list.get(i);
			if( move != null && move.score != NodeEvaluator.MAXIMUM && move.score != -NodeEvaluator.MAXIMUM ){
				if (j++ > 0) str += ",";
				str += move.toString();
			}
		}
		str += "}";
		return str;
	}

	final public void sortMoves(List<ChessMove> moves, boolean maximize) {
		ChessGame.SORTER.sort(moves, maximize);
	}
	
	final public void cleanMoveList(List<ChessMove> legalMoves) {
		for (int j=0, max = legalMoves.size(); j < max; j++) {
			List<ChessMove> contList = (List<ChessMove> )legalMoves.get(j).getContinuationList();
			contList.clear();
		}
	}
	
	final public void cleanScores(List<ChessMove> legalMoves) {
		for (int j=0, max = legalMoves.size(); j < max; j++) {
			legalMoves.get(j).score = 0;
		}
	}

	final public void promoteMove(List<ChessMove> moves, boolean maximize) {
		int index = -1;
		if( maximize ){
			long max = -Long.MAX_VALUE;
			for(int i=0, size=moves.size(); i<size; i++){
				if( moves.get(i).score > max ){
					max = moves.get(i).score;
					index = i;
				}
			}
		} else {
			long min = +Long.MAX_VALUE;
			for(int i=0, size=moves.size(); i<size; i++){
				if( moves.get(i).score < min ){
					min = moves.get(i).score;
					index = i;
				}
			}
		}
		ChessMove move0 = moves.get(0);
		moves.set(0, moves.get(index));
		moves.set(index, move0);
	}
	

}
