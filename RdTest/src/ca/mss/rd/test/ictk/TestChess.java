package ca.mss.rd.test.ictk;

import ictk.boardgame.chess.ChessGameInfo;
import ictk.boardgame.chess.ChessMove;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ca.mss.rd.chessictk.engine.ChessEngine;
import ca.mss.rd.chessictk.engine.impl.DebuteChessEngine;
import ca.mss.rd.chessictk.engine.impl.RandomChessEngine;
import ca.mss.rd.chessictk.engine.impl.SwampChessEngine;
import ca.mss.rd.chessictk.game.ChessGame;
import ca.mss.rd.chessictk.game.WorkflowChessHelper;
import ca.mss.rd.chessictk.wkfrepo.ChessWorkflow;
import ca.mss.rd.chessictk.wkfrepo.DynaChessContextFactory;
import ca.mss.rd.chessictk.wkfrepo.HardChessContextFactory;
import ca.mss.rd.test.workflow.interceptors.JUnitContextFactory;
import ca.mss.rd.test.workflow.interceptors.JUnitInterceptor;
import ca.mss.rd.test.workflow.interceptors.JUnitProcessor;
import ca.mss.rd.util.Debug;
import ca.mss.rd.workflow.dynamic.DynaContext;
import ca.mss.rd.workflow.expression.BshExprEngine;
import ca.mss.rd.workflow.proc.WkfProcessor;
import ca.mss.rd.workflow.proc.inthread.WkfProcessorImpl;
import ca.mss.rd.workflow.reader.WkfReader;
import ca.mss.rd.workflow.reader.xpdl.FileReaderXPDL;

public class TestChess extends TestCase {

	final public static String module = TestChess.class.getName();
	static final long serialVersionUID = module.hashCode();
	private final static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	private static Random random = new Random();

	long start;
	int total;
	long finish;
	long time;
	double rate;
	long exceptions;

	WkfProcessor processor;
	WkfReader reader;
	ChessWorkflow chessDyna;
	ChessWorkflow chessDynaJEL;
	ChessWorkflow chessHard;

	
	
	/**
	 * 
	 */
	public TestChess() {
		super();
	}

	/**
	 * @param name
	 */
	public TestChess(String name) {
		super(name);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("ChessGame Flow Test");
/*
		//suite.addTest(new TestChess("chessDynaFlowJU"));
		suite.addTest(new TestChess("chessSimulatorFastest"));
		suite.addTest(new TestChess("simulaRandomICTK"));

		suite.addTest(new TestChess("simulaRandomChessEngine"));

		suite.addTest(new TestChess("simulaSwampChessEngine"));
		suite.addTest(new TestChess("simulaSwampChessEngineAB"));
		suite.addTest(new TestChess("simulaDebuteChessEngine"));
		suite.addTest(new TestChess("simulaDebuteChessEngineFazzy"));

		suite.addTest(new TestChess("chessHardFlow"));
		suite.addTest(new TestChess("chessDynaFlowJEL"));
		suite.addTest(new TestChess("chessDynaFlowBSH"));
*/

		suite.addTest(new TestChess("simulaSwampChessEngine"));
		
		return suite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		processor = new WkfProcessorImpl();

		// processor= new JUnitProcessor(new WkfProcessorImpl());
		// processor.setWkfInterceptor(new JUnitInterceptor());

		reader = new FileReaderXPDL(
				"../WkfRepository/repo/chess/triplechess1.xpdl");

		chessDyna = new ChessWorkflow();
		chessDyna.setWkfReader(reader);
		DynaChessContextFactory factory = new DynaChessContextFactory(reader);
		factory.setExpressionEngine(new BshExprEngine());
		chessDyna.setWkfContextFactory(factory);
		chessDyna.buildWorkflow();

		chessDynaJEL = new ChessWorkflow();
		chessDynaJEL.setWkfReader(reader);
		chessDynaJEL.setWkfContextFactory(new DynaChessContextFactory(reader));
		chessDynaJEL.buildWorkflow();

		chessHard = new ChessWorkflow();
		chessHard.setWkfReader(reader);
		chessHard.setWkfContextFactory(new HardChessContextFactory(reader));
		// chessHard.setWkfContextFactory(new JUnitContextFactory(new
		// HardChessContextFactory(reader)));
		chessHard.buildWorkflow();

		start = System.currentTimeMillis();
		total = 0;
		exceptions = 0;

		if( Debug.isVerboseEnabled ) logger.info("*** Start [Name=" + getName() + "]");
	}

	public void tearDown() {
		finish = System.currentTimeMillis();
		time = (finish - start);
		rate = (time * 1.0) / (total * 1.0);

		if( Debug.isVerboseEnabled ) logger.info("*** Finish[Name=" + getName() + "][total=" + total
				+ "][time=" + time + "][rate=" + rate + "][exceptions="
				+ exceptions + "]");
	}

	public void chessDynaFlowJU() {

		WkfProcessor processor = new JUnitProcessor(new WkfProcessorImpl());
		processor.setWkfInterceptor(new JUnitInterceptor());
		WkfReader reader = new FileReaderXPDL(
				"../WkfRepository/repo/chess/triplechess1.xpdl");

		ChessWorkflow wkf = new ChessWorkflow();
		wkf.setWkfReader(reader);
		wkf.setWkfContextFactory(new JUnitContextFactory(
				new DynaChessContextFactory(reader)));

		for (int j = 0; j < 10; j++) {
			try {
				processor.startWorkflow(wkf);
				for (int i = 0; i < 2 && !processor.isWorkflowFinished(); i++) {
					processor.resumeWorkflow();
				}
			} catch (Exception e) {
				exceptions += 1;
				e.printStackTrace();
			}

			DynaContext context = (DynaContext) processor.getWkfContext();
			String tableId = (String) context.getData().get("tableId");
			ChessGame game = WorkflowChessHelper.selectGame(tableId);

			total += game.goToEnd().getCurrentMoveNumber();
		}
	}

	public void chessSimulatorFastest() throws Exception {
		String movestr;
		ChessGame game;

		for (int i = 0; i < 100; i++) {
			game = new ChessGame();
			ChessGameInfo info = new ChessGameInfo();
			info.setAuxilleryProperties(new Properties());
			game.setGameInfo(info);
			// chess.getHistory().goToEnd();

			while (game.getCapturedPiecesAmount() < 28 && !game.isCheckmate()
					&& !game.isStalemate()) {
				List<ChessMove> legalMoves = game.getLegalMoves();
				int moveCount = legalMoves.size();

				if (moveCount > 0) {

					int moveIndex = random.nextInt(moveCount);
					ChessMove move = legalMoves.get(moveIndex);

					game.addMove(move);

				} else
					break;
			}

			// if( Debug.isVerboseEnabled ) logger.info("[scores="+getScores()+"][captured="+chess.getCapturedPiecesAmount()+"][moves="+chess.getCurrentMoveNumber()+"] \n"+chess.toString());
			total += game.getCurrentMoveNumber();
		}
	}

	public void chessDynaFlowJEL() {
		for (int j = 0; j < 1; j++) {
			try {
				processor.startWorkflow(chessDynaJEL);
				for (int i = 0; i < 2 && !processor.isWorkflowFinished(); i++) {
					processor.resumeWorkflow();
				}

			} catch (Exception e) {
				exceptions += 1;
				e.printStackTrace();
			}

			DynaContext context = (DynaContext) processor.getWkfContext();
			String tableId = (String) context.getData().get("tableId");
			ChessGame game = WorkflowChessHelper.selectGame(tableId);
			total += game.goToEnd().getCurrentMoveNumber();
		}
	}

	public void chessHardFlow() {
		for (int j = 0; j < 100; j++) {
			try {
				processor.startWorkflow(chessHard);
				for (int i = 0; i < 2 && !processor.isWorkflowFinished(); i++) {
					processor.resumeWorkflow();
				}

			} catch (Exception e) {
				exceptions += 1;
				e.printStackTrace();
			}

			DynaContext context = (DynaContext) processor.getWkfContext();
			String tableId = (String) context.getData().get("tableId");
			ChessGame game = WorkflowChessHelper.selectGame(tableId);
			total += game.goToEnd().getCurrentMoveNumber();
		}
	}

	public void simulaRandomICTK() throws Exception {
		String movestr;
		ChessGame game;

		for (int i = 0; i < 100; i++) {
			game = new ChessGame();
			ChessGameInfo info = new ChessGameInfo();
			info.setAuxilleryProperties(new Properties());
			game.setGameInfo(info);
			// chess.getHistory().goToEnd();

			try {
				while (game.getCapturedPiecesAmount() < 28
						&& !game.isCheckmate() && !game.isStalemate()) {
					List<ChessMove> legalMoves = game.getLegalMoves();
					int moveCount = legalMoves.size();

					if (moveCount > 0) {

						int moveIndex = random.nextInt(moveCount);
						ChessMove move = legalMoves.get(moveIndex);

						game.addMove(move);
						String pgn = game.moveToString(move);
						game.removeMove();
						// chess.getHistory().prev();

						ChessMove move2 = ((ChessMove) game.stringToMove(pgn));
						game.addMove(move2);
					} else
						break;
				}
			} catch (Exception e) {
				exceptions++;
			}
			// if( Debug.isVerboseEnabled ) logger.info("[scores="+getScores()+"][captured="+chess.getCapturedPiecesAmount()+"][moves="+chess.getCurrentMoveNumber()+"] \n"+chess.toString());
			total += game.getCurrentMoveNumber();
		}
	}

	public void simulaRandomChessEngine() throws Exception {
		String movestr;
		ChessGame game;
		ChessEngine engine;

		for (int i = 0; i < 100; i++) {

			game = new ChessGame();
			engine = new RandomChessEngine(game);

			try {
				while (game.getCapturedPiecesAmount() < 28 && !game.isCheckmate() && !game.isStalemate()) {
					ChessMove move = engine.generateMove();
					game.addMove(move);
				}
			} catch (Exception e) {
				exceptions++;
			}
			// if( Debug.isVerboseEnabled ) logger.info("[scores="+getScores()+"][captured="+chess.getCapturedPiecesAmount()+"][moves="+chess.getCurrentMoveNumber()+"] \n"+chess.toString());
			total += game.getCurrentMoveNumber();
		}
	}

	public void simulaSwampChessEngine() throws Exception {
		ChessGame game;
		ChessEngine white, black;

		for (int i=0; i < 1; i++) {

			game = new ChessGame();
			white = new SwampChessEngine(game);
			black = new SwampChessEngine(game);

			try {
				while( !game.isEndOfGame()) {
					
					ChessMove move;
					
					if( game.isBlackSide() ){
						move = black.generateMove();
					} else {
						move = white.generateMove();
					}
					
					if( move != null ){
						if( logger.isInfoEnabled() ) logger.info("[captured="+game.getCapturedPiecesAmount()+"]["+game.sideColor()+"][move#="+game.getCurrentMoveNumber()+"][move="+move+"]");
						
						game.addMove(move);
						if( logger.isInfoEnabled() ) logger.info("\n"+game.toString());
						
						if( game.getTotalMoveNumbers2() == 100)
							break;
						
					} else {
						break; // 
					}
				}
				if( Debug.isVerboseEnabled ) logger.info("[scores="+game.getScores()+"][captured="+game.getCapturedPiecesAmount()+"][moves="+game.getTotalMoveNumbers()+"] \n"+game.toString());
			} catch (Exception e) {
				exceptions++;
				logger.error(e.getMessage(), e);
			}
			total += game.getCurrentMoveNumber2()-1;
		}
	}

	
	public void simulaDebuteChessEngine() throws Exception {
		ChessGame game;
		ChessEngine white, black;

		for (int i=0; i < 1; i++) {

			game = new ChessGame();
			white = new SwampChessEngine(game);
			black = new DebuteChessEngine(game);

			try {
				while (game.getCapturedPiecesAmount() < 30 && !game.isCheckmate() && !game.isStalemate()) {
					ChessMove move = null;
					if( game.isBlackSide() ){
						move = black.generateMove();
					} else {
						move = white.generateMove();
					}
					if( move == null )
						break;
					game.addMove(move);
					if( logger.isInfoEnabled() ) logger.info("[scores="+game.getScores()+"][captured="+game.getCapturedPiecesAmount()+"][moves="+game.getCurrentMoveNumber()+"] \n"+game.toString());
					if( game.getCurrentMoveNumber() == 1000 )
						break;
				}
			} catch (Exception e) {
				exceptions++;
				logger.error(e.getMessage(), e);
			}
			if( Debug.isVerboseEnabled ) logger.info("[scores="+game.getScores()+"][captured="+game.getCapturedPiecesAmount()+"][moves="+game.getCurrentMoveNumber()+"] \n"+game.toString());
			total += game.getCurrentMoveNumber();
		}
	}

	public void chessDynaFlowBSH() {
		for (int j = 0; j < 5; j++) {
			try {
				processor.startWorkflow(chessDyna);
				for (int i = 0; i < 2 && !processor.isWorkflowFinished(); i++) {
					processor.resumeWorkflow();
				}

			} catch (Exception e) {
				exceptions += 1;
				e.printStackTrace();
			}

			DynaContext context = (DynaContext) processor.getWkfContext();
			String tableId = (String) context.getData().get("tableId");
			ChessGame game = WorkflowChessHelper.selectGame(tableId);
			total += game.goToEnd().getCurrentMoveNumber();
		}
	}

	/*
[Name=chessSimulatorFastest][total=49343][time=4479][rate=0.09077275398739436][exceptions=0]
[Name=simulaRandomICTK][total=51282][time=13924][rate=0.2715182715182715][exceptions=0]
[Name=simulaRandomChessEngine][total=48988][time=13289][rate=0.27127051522821916][exceptions=0]
[Name=chessHardFlow][total=45631][time=15411][rate=0.33773092853542547][exceptions=0]
[Name=chessDynaFlowJEL][total=45106][time=15890][rate=0.35228129295437416][exceptions=0]
[Name=chessDynaFlowBSH][total=523][time=6001][rate=11.474187380497131][exceptions=0]
	 */
}
