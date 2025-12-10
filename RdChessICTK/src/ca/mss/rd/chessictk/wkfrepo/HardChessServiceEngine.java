/**
 * 
 */
package ca.mss.rd.chessictk.wkfrepo;

import java.util.Map;


import ca.mss.rd.workflow.def.WkfData;
import ca.mss.rd.workflow.def.WkfTool;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.dynamic.DynaData;
import ca.mss.rd.workflow.dynamic.service.WkfProcedureDelegator;
import ca.mss.rd.workflow.dynamic.service.WkfServiceEngine;
import ca.mss.rd.workflow.impl.WkfConditionImpl;


/**
 * @author smoskov
 *
 */
public class HardChessServiceEngine implements WkfServiceEngine {

	final public static String module = HardChessServiceEngine.class.getName();
	static final long serialVersionUID = module.hashCode();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	/* (non-Javadoc)
	 * @see ca.mss.workflow.dynamic.service.WkfServiceEngine#runProcedure(ca.mss.workflow.dynamic.service.WkfProcedureDelegator, ca.mss.workflow.def.WkfTool, ca.mss.workflow.def.WkfData)
	 */
	@Override
	public void runProcedure(WkfProcedureDelegator delegateObject, WkfTool tool, WkfData data) {
		ChessProcedure chessProcedure = (ChessProcedure )delegateObject;
		Map context = (Map )data;
		
		String serviceName = tool.getDefinition().get("serviceName").value;
		
		switch( tool.getType() ){
		case Procedure:
			if( "chessAddMove".equals(serviceName) )
				chessProcedure.chessAddMove(context);
			else if( "chessAssignBlack".equals(serviceName) )
				chessProcedure.chessAssignBlack(context);
			else if( "chessAssignOwner".equals(serviceName) )
				chessProcedure.chessAssignOwner(context);
			else if( "chessAssignWhite".equals(serviceName) )
				chessProcedure.chessAssignWhite(context);
			else if( "chessCheckmate".equals(serviceName) )
				chessProcedure.chessCheckmate(context);
			else if( "chessCheckUser".equals(serviceName) )
				chessProcedure.chessCheckUser(context);
			else if( "chessCreateNew".equals(serviceName) )
				chessProcedure.chessCreateNew(context);
			else if( "chessDraw".equals(serviceName) )
				chessProcedure.chessDraw(context);
			else if( "chessGenerateMove".equals(serviceName) )
				chessProcedure.chessGenerateMove(context);
			else if( "chessNextMove".equals(serviceName) )
				chessProcedure.chessNextMove(context);
			else if( "chessNotifyBlack".equals(serviceName) )
				chessProcedure.chessNotifyBlack(context);
			else if( "chessNotifyWhite".equals(serviceName) )
				chessProcedure.chessNotifyWhite(context);
			else if( "chessPosition".equals(serviceName) )
				chessProcedure.chessPosition(context);
			else if( "chessResign".equals(serviceName) )
				chessProcedure.chessResign(context);
			else if( "chessScores".equals(serviceName) )
				chessProcedure.chessScores(context);
			else if( "chessStalemate".equals(serviceName) )
				chessProcedure.chessStalemate(context);
			else if( "chessValidateMove".equals(serviceName) )
				chessProcedure.chessValidateMove(context);
			else 
				logger.warn("Missing Tool [tool="+tool.getId()+"][serviceName="+serviceName+"]");
			break;
		default:
			throw new RuntimeException("Unknown tool type [type="+tool.getType()+"][tool="+tool+"]");
		}
	}		

	/* (non-Javadoc)
	 * @see ca.mss.workflow.hard.HardContext#evaluateTransition(ca.mss.workflow.def.WkfTransition, ca.mss.workflow.dynamic.DynaData)
	 */
	final public boolean evaluateTransition(WkfTransition transition, DynaData context){
		WkfConditionImpl condition = (WkfConditionImpl )transition.getCondition();
		String expression = condition.getExpression();

    	String ownerColor = (String )context.get("ownerColor");
    	String rivalType = (String )context.get("rivalType");
    	String inviteStatus = (String )context.get("inviteStatus");
    	String isWhitePlay = (String )context.get("isWhitePlay");
    	String isBlackPlay = (String )context.get("isBlackPlay");
    	String wichTurn = (String )context.get("wichTurn");
    	String ownerType = (String )context.get("ownerType");
       	String reqStatus = (String )context.get("reqStatus");
       	String resStatus = (String )context.get("resStatus");

    	String tId = transition.getId();

    	boolean result = true;
		
		if( "FR_Invite_Player_TO_White_Invitation".equals(tId) )
			result = ownerColor.equals("black") && rivalType.equals("human");
		else if( "FR_Invite_Player_TO_Black_Invitation".equals(tId) )
			result = ownerColor.equals("white") && rivalType.equals("human");
		else if( "FR_Computer_Invitation_TO_Game_Approval".equals(tId) )
			result = inviteStatus.equals("accepted");
		else if( "FR_Game_Approval_TO_Start_Game".equals(tId) )
			result = isWhitePlay.equals("true") && isBlackPlay.equals("true");
		else if( "FR_Which_Turn_TO_User_Request".equals(tId) )
			result = (wichTurn.equals("rival") && rivalType.equals("human")) || (wichTurn.equals("owner") && ownerType.equals("human"));
		else if( "FR_Computer_TO_Accept_Move".equals(tId) )
			result = reqStatus.equals("move");
		else if( "FR_Computer_TO_Resign".equals(tId) )
			result = reqStatus.equals("resign");
		else if( "FR_Computer_TO_Draw_Request".equals(tId) )
			result = reqStatus.equals("draw");
		else if( "FR_Accept_Move_TO_Stale_Mate".equals(tId) )
			result = resStatus.equals("stalemate");
		else if( "FR_Accept_Move_TO_Draw".equals(tId) )
			result = resStatus.equals("draw");
		else if( "FR_Accept_Move_TO_Check_Mate".equals(tId) )
			result = resStatus.equals("checkmate");
//		else if( "".equals(tId) )
//			result = ;
		else	
			logger.warn("Missing Evaluator [transitionId="+transition.getId()+"][expression="+expression+"]");
		
		return result;
	}
}
