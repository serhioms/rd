package ca.mss.rd.tools.mom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.mss.rd.util.runnable.RdRunnable;

public class RdRealtime extends RdRunnable {

	final private Map<String,List<String>> workflow;
	private List<RdAgent> agents;
	
	final public String start;
	final public Map<String, Object> context;

	public RdRealtime(String start, Map<String, List<String>> workflow) {
		super(start);
		this.workflow = workflow;
		this.start = start;
		this.context = new HashMap<String, Object>();
	}
	
	final public Iterator<RdAgent> iterator(){
		if( agents == null ){
			synchronized( this ){
				if( agents == null ){
					
					final Map<String,Set<String>> reversed = new HashMap<String,Set<String>>(); 
					
					for( Iterator<Map.Entry<String,List<String>>> iter = workflow.entrySet().iterator(); iter.hasNext(); ){
						Map.Entry<String,List<String>> activity = iter.next();
						String head = activity.getKey();
						List<String> value = activity.getValue();
						if( value != null ){
							for(Iterator<String> iter2=value.iterator(); iter2.hasNext(); ){
								String teil = iter2.next();
								
								Set<String> headlist = reversed.get(teil);
								if( headlist == null ){
									headlist = new HashSet<String>();
									reversed.put(teil, headlist);
								}
								headlist.add(head);
							}
						}
					}

					Set<String> waits = new LinkedHashSet<String>();
					List<String> sequence = new ArrayList<String>();
					
					for( Iterator<Map.Entry<String,List<String>>> iter = workflow.entrySet().iterator(); iter.hasNext(); ){
						
						Map.Entry<String,List<String>> body = iter.next();
						
						String head = body.getKey();
						List<String> teils = body.getValue();

						if( !sequence.contains(head) )
							sequence.add(head);
						
						if( teils != null && !sequence.containsAll(teils) ){
							for(String teil: teils ) { 
								if( !sequence.containsAll(reversed.get(teil)) )
									waits.add(teil);
								else if( !sequence.contains(teil) ){
									sequence.add(teil);
									for(String wait: waits) {
										if( sequence.contains(wait) ){
											waits.remove(wait);
										} else if( sequence.containsAll(reversed.get(wait)) ){
											sequence.add(wait);
											waits.remove(wait);
										}
									}
								}
							}
						}
					}
					
					agents = new ArrayList<RdAgent>();
					for(String task: sequence){
						agents.add(new RdAgent(start+":"+task));
					}
				}
			}
		}
		return new ArrayList<RdAgent>(agents).iterator();
	}
	
	@SuppressWarnings("unchecked")
	final static public Map<String,List<String>> toMap(Object... args){
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0].toString(), (List<String> )args[i + 1]);
		}

		return map;
	}

	@Override
	public void runThreadHandler() {
		for(Iterator<RdAgent> iter=iterator(); iter.hasNext(); ){
			RdAgent agent = iter.next();
			agent.start(context);
		}
	}
	
	public boolean isDone() {
		for(Iterator<RdAgent> iter=iterator(); iter.hasNext(); )
			if( iter.next().isRunning() )
				return false;
		return true;
	}
	
	
}
