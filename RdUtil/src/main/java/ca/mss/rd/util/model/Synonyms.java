package ca.mss.rd.util.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Synonyms implements ISynonyms {

	private Map<String, List<String>> synonymsPool = new HashMap<String, List<String>>(); 
	
	
	public void addSynonyms(String w1, String w2){
		
		if( w1 == null || w2 == null )
			throw new RuntimeException("Can not make synonyms from ["+w1+","+w2+"] ");
			
		String s1=w1.toUpperCase(), s2=w2.toUpperCase();
		List<String> syn;
		
		if( !isSynonyms(w1,w2) ){
			if( (syn=synonymsPool.get(s1)) != null ){
				syn.add(w2);
				synonymsPool.put(s2, syn);
			} else if( (syn=synonymsPool.get(s2)) != null ){
				syn.add(w1);
				synonymsPool.put(s1, syn);
			} else {
				syn = new ArrayList<String>();
				syn.add(w1);
				syn.add(w2);
				synonymsPool.put(s1, syn);
				synonymsPool.put(s2, syn);
			}
		}
	}
	
	private List<String> getSynonyms(String w){
		if( w != null )
			return synonymsPool.get(w.toUpperCase());
		else
			throw new RuntimeException("Can not get synonym for NULL");
	}
	
	public boolean isSynonyms(String w1, String w2){
		
		if( w1 == null || w2 == null )
			throw new RuntimeException("Can not check synonyms for["+w1+","+w2+"] ");
		
		List<String> syn1 = getSynonyms(w1);
		List<String> syn2 = getSynonyms(w2);
		if ( syn1 != null && syn2 != null && syn1 == syn2 )
			return true;
		
		
		return w1.equalsIgnoreCase(w2);
	}

	
}
