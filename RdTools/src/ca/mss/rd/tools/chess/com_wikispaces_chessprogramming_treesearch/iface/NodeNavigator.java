/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface;



/**
 * @author smoskov
 *
 */
public interface NodeNavigator {

	public void startSearch(boolean maximize);
	public boolean isMaxDepth(int depth);

	public boolean hasNext(int depth, int i);
	public void makeNext(int depth, int i);
	
	public long alphaCutoff(int depth, int i, long alpha);
	public long betaCutoff(int depth, int i, long beta);
	public boolean isCutoff();

	public boolean hasQuiescence(int depth, int i);
	public void makeQuiescence(int depth, int i);

	public void takeBack(int depth, long score, boolean isCutoff);

}
