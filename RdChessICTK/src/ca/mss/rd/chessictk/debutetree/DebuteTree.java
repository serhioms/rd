/**
 * 
 */
package ca.mss.rd.chessictk.debutetree;

import ictk.boardgame.Game;
import ictk.boardgame.GameInfo;
import ictk.boardgame.History;
import ictk.boardgame.Player;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.io.PGNReader;
import ictk.boardgame.chess.io.SAN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;



/**
 * @author smoskov
 * 
 */
public class DebuteTree {

	public static final String module = DebuteTree.class.getName();
	static final long serialVersionUID = module.hashCode();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public int MAX_GAMES = 100000; 
	static public int MAX_DEPTH = 10; 
	
	private String pgnPath;
	private String debutePath;
	private int gameCount;
	private Map<String, Map> debuteTree;

	/**
	 * @return the pgnPath
	 */
	public final String getPgnPath() {
		return pgnPath;
	}

	/**
	 * @param pgnPath the pgnPath to set
	 */
	public final void setPgnPath(String pgnPath) {
		this.pgnPath = pgnPath;
	}

	/**
	 * @return the debutePath
	 */
	public final String getDebutePath() {
		return debutePath;
	}

	/**
	 * @param debutePath the debutePath to set
	 */
	public final void setDebutePath(String debutePath) {
		this.debutePath = debutePath;
	}

	/**
	 * @return the debuteTree
	 */
	public final Map<String, Map> getDebuteTree() {
		return debuteTree;
	}

	public void loadPGN() {
		
		debuteTree = new TreeMap<String, Map>();

		try {
			Game game = null;
			History history = null;
			PGNReader in = null;
			SAN san = new SAN();
			GameInfo info = null;
			Player[] players = null;
			
			logger.info("Reading file: " + pgnPath);
			in = new PGNReader(new FileReader(new File(pgnPath)));
			
			try {
				for(gameCount=1 ; (game = in.readGame()) != null && gameCount <= MAX_GAMES; gameCount++) {

					info = game.getGameInfo();
					players = info.getPlayers();

					if( gameCount%1000 == 0)
						logger.info("#"+gameCount+" "+info.getYear()+" "+players[0]+" vs "+players[1] );
					
					history = game.getHistory();
	
					Map<String, Map> temp = debuteTree;
					
					int moveCount;
					for(moveCount=1; moveCount <= MAX_DEPTH/2 && history.hasNext(); moveCount++) {
						ChessMove white = (ChessMove )history.next();
	
						String strmove = san.moveToString(white);
						Map<String, Map> temp2 = (Map<String, Map> )temp.get(strmove);
						if( temp2 == null ){
							temp2 = new TreeMap<String, Map>();
							temp.put(strmove, temp2);
						}
	
						ChessMove black = null;
						if( history.hasNext() ){
							black = (ChessMove )history.next();
	
							strmove = san.moveToString(black);
							Map<String, Map> temp3 = (Map<String, Map> )temp2.get(strmove);
							if( temp3 == null ){
								temp3 = new TreeMap<String, Map>();
								temp2.put(strmove, temp3);
							}
							
							temp = temp3;
							
							if( logger.isDebugEnabled() ) logger.debug("\t"+moveCount+") "+white.toString()+"\t\t"+black.toString());
							if( !history.hasNext() ){
								if( logger.isDebugEnabled() ) logger.debug("\t"+moveCount+") "+game.getResult().toString());
							}
						} else {
							if( logger.isDebugEnabled() ) logger.debug("\t"+moveCount+") "+white.toString()+"\t\t"+game.getResult().toString());
						}
					}
					game = null;
				}
				
				if( gameCount%1000 != 0)
					logger.info("#"+gameCount+" "+info.getYear()+" "+players[0]+" vs "+players[1] );
				
			}catch(java.lang.IllegalStateException e){
				logger.error(e.getMessage()+" #"+gameCount+" "+info.getYear()+" "+players[0]+" vs "+players[1], e);
			}catch(Exception e){
				logger.error(e.getMessage()+" #"+gameCount+" "+info.getYear()+" "+players[0]+" vs "+players[1], e);
			}
			//logger.info("debuteTree = "+debuteTree);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	
	public void loadTree() {
		
		debuteTree = new TreeMap<String, Map>();
		
		try {

			logger.info("Reading file: " + debutePath);
			BufferedReader in = new BufferedReader(new FileReader(new File(debutePath)));
			char[] cb = new char[1];

			Map<String, Map>[] stack = (Map<String, Map>[] )new Map[MAX_DEPTH*2];
			
			Map<String, Map> temp = debuteTree;
			Map<String, Map> temp2 = new TreeMap<String, Map>();
			
			String strmove = null;
			
			int depth = -1;
			for(int len=in.read(cb); len!=-1; len=in.read(cb)){
				switch(cb[0]){
				case ',':
					len=in.read(cb);
					strmove = "";
					break;
				case '{':
					depth++;
					strmove = "";
					break;
				case '=':
					temp2 = (Map<String, Map> )temp.get(strmove);
					if( temp2 == null ){
						temp2 = new TreeMap<String, Map>();
						temp.put(strmove, temp2);
					}
					stack[depth] = temp;
					temp = temp2;
					break;
				case '}':
					depth--;
					if( depth >= 0 ){
						temp = stack[depth];
					}
					break;
				default:
					strmove += cb[0];
					break;
				}
			}
			
			stack = null;
			
			//logger.info("debuteTree = "+debuteTree);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

}
