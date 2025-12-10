package ca.mss.rd.util.io;

import java.util.ArrayList;

import ca.mss.rd.util.UtilMisc;

public class UtilTextFile {

	public static int[][] readTableInt(String filePath) {
		
		ArrayList<int[]> arr = new ArrayList<int[]>();
		
		TextFileReader tfr = new TextFileReader(filePath);
		
		tfr.open();
		for(String row=tfr.readRow(); row != null; row=tfr.readRow()){
			arr.add(UtilMisc.toInt(row.split(",")));
		}
		int[][] result = new int[arr.size()][];
		
		return arr.toArray(result);
	}

}
