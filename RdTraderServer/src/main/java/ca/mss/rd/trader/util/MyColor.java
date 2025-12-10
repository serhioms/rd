package ca.mss.rd.trader.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mss.rd.util.UtilProperty;

public class MyColor {

	final static public Map<String, Color> colorMap = new HashMap<String, Color>();
	final static public List<String> colorList = new ArrayList<String>();
	
	static private void addColor(String name, Color color){
		colorList.add(name);
		colorMap.put(name, color);
	}
	
	static int nextColor = 0;
	static { 
		UtilProperty.readConstants(MyColor.class);
		addColor("white", Color.white);
		addColor("yellow", Color.yellow);
		addColor("red", Color.red.brighter());
		addColor("cyan", Color.cyan);
		addColor("magenta", Color.magenta.brighter());
		addColor("pink", Color.pink);
		addColor("orange", Color.orange);
		addColor("blue", Color.blue);
		addColor("green", Color.green);
		addColor("deepyellow", Color.yellow.darker().darker());
		addColor("deepred", Color.red.darker());
		addColor("deepcyan", Color.cyan.darker().darker());
		addColor("deepmagenta", Color.magenta.darker());
		addColor("deepblue", Color.blue.darker().darker());
		addColor("deepgreen", Color.green.darker().darker());
		addColor("deeppink", Color.pink.darker().darker());
		addColor("deepprange", Color.orange.darker().darker());
	}

	final static public String getNextColor(){
		if( ++nextColor >= colorList.size())
			nextColor = 1;
		return colorList.get(nextColor);
	}

	final static public Color getColor(String name){
		return colorMap.get(name);
	}
	
	final static public Color COLOR_BSB = Color.RED;
	final static public Color COLOR_SBB = Color.GREEN;

}
