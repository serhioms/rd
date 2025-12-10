
package games.checkers;

import java.util.List;

public interface NodeInterface {

  public List<NodeInterface> children();

  // should really be done by a separate evaluator
  public int score();

  // note that addChild is not strictly necessary..
  // it is only here because we are adding the nodes
  // manually.

   public void addChild(NodeInterface child);

  // what then becomes clear is that the maximize part is
  // nothing to do with the node - it is a property of the
  // search algorithm instead!
  
}
