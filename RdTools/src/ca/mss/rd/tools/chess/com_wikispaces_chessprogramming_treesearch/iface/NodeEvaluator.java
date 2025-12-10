/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface;

/**
 * @author smoskov
 *
 */
public interface NodeEvaluator {

	final static public long MAXIMUM = Long.MAX_VALUE;

	public int getMaxDepth();

	public long evaluate(int depth);
	public long negaluate(int depth);

	public boolean maximize();

	public boolean isNegaluated();
}
