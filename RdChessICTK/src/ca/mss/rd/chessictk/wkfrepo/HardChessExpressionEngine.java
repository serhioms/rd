/**
 * 
 */
package ca.mss.rd.chessictk.wkfrepo;

import java.util.Map;



 import ca.mss.rd.workflow.dynamic.expression.WkfExpressionEngine;

import org.apache.log4j.Logger;


/**
 * @author smoskov
 *
 */
public class HardChessExpressionEngine implements WkfExpressionEngine {

	final public static String module = HardChessExpressionEngine.class.getName();
	static final long serialVersionUID = module.hashCode();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);


	/* (non-Javadoc)
	 * @see ca.mss.workflow.dynamic.expression.WkfExpressionEngine#evaluate(java.lang.String, java.util.Map)
	 */
	@Override
	public boolean evaluate(String expression, Map context) {

		String ownerColor = (String )context.get("ownerColor");
    	String rivalType = (String )context.get("rivalType");
    	String inviteStatus = (String )context.get("inviteStatus");
    	String isWhitePlay = (String )context.get("isWhitePlay");
    	String isBlackPlay = (String )context.get("isBlackPlay");
    	String wichTurn = (String )context.get("wichTurn");
    	String ownerType = (String )context.get("ownerType");
       	String reqStatus = (String )context.get("reqStatus");
       	String resStatus = (String )context.get("resStatus");

    	boolean result = true;
		
		if( "ownerColor.equals(\"black\") && rivalType.equals(\"human\")".equals(expression) )
			result = ownerColor.equals("black") && rivalType.equals("human");
		else if( "ownerColor.equals(\"white\") && rivalType.equals(\"human\")".equals(expression) )
			result = ownerColor.equals("white") && rivalType.equals("human");
		else if( "inviteStatus.equals(\"accepted\")".equals(expression) )
			result = inviteStatus.equals("accepted");
		else if( "isWhitePlay.equals(\"true\") && isBlackPlay.equals(\"true\")".equals(expression) )
			result = isWhitePlay.equals("true") && isBlackPlay.equals("true");
		else if( "(wichTurn.equals(\"rival\") && rivalType.equals(\"human\")) || (wichTurn.equals(\"owner\") && ownerType.equals(\"human\"))".equals(expression) )
			result = (wichTurn.equals("rival") && rivalType.equals("human")) || (wichTurn.equals("owner") && ownerType.equals("human"));
		else if( "reqStatus.equals(\"move\")".equals(expression) )
			result = reqStatus.equals("move");
		else if( "reqStatus.equals(\"resign\")".equals(expression) )
			result = reqStatus.equals("resign");
		else if( "reqStatus.equals(\"draw\")".equals(expression) )
			result = reqStatus.equals("draw");
		else if( "resStatus.equals(\"stalemate\")".equals(expression) )
			result = resStatus.equals("stalemate");
		else if( "resStatus.equals(\"draw\")".equals(expression) )
			result = resStatus.equals("draw");
		else if( "resStatus.equals(\"checkmate\")".equals(expression) )
			result = resStatus.equals("checkmate");
//		else if( "".equals(expression) )
//			result = ;
		else	
			logger.warn("Missing Evaluator [expression="+expression+"]");
		
		return result;
	}
}
