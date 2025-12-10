package ca.mss.rd.trader.deprecated.model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

import ca.mss.rd.util.UtilMisc;

public enum SelectedData {
	INSTANCE;

    final public Map<String,Set<String>> selectedQuotes = new HashMap<String,Set<String>>();
    final public Map<String,List<String>> selectedPipes = new HashMap<String,List<String>>();
    
    
	private SelectedData() {
		
		selectedQuotes.putAll(selectSet(
				"Oanda-File", UtilMisc.toSet("EURUSD")
			));

		selectedPipes.putAll(selectList(
				"Oanda-File", UtilMisc.toList("0.25=>yellow", "1=>red")
				// "Oanda-File", UtilMisc.toList("0.25=>yellow","1=>red","2=>cyan","3=>magenta","4=>blue","5=>green","6=>deepyellow","7=>deepred","8=>deepcyan")
				// "Oanda-File", UtilMisc.toList("0.25","1","2","3","4","5","6","7","8")
				// "Oanda-File", UtilMisc.toList("1","2","5","10","17")
				// "Oanda-File", UtilMisc.toList("1", "16", "17", "21")
				// "Oanda-File", UtilMisc.toList("1", "2", "4", "8", "16", "17")
			));
	}
	
	
	

	@SuppressWarnings("unchecked")
	static private Map<String,List<String>> selectList(Object... args) {
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0].toString(), (List<String> )args[i + 1]);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	static private Map<String,Set<String>> selectSet(Object... args) {
		Map<String,Set<String>> map = new HashMap<String,Set<String>>();
		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0].toString(), (Set<String> )args[i + 1]);
		}
		return map;
	}

}
