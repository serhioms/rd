/**
 * 
 */
package ca.mss.rd.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moskovsk
 * 
 */
public class UtiFile {
	final static public String className = UtiFile.class.getName();
	final static public long serialVersionUID = className.hashCode();
	
	final static public List<String> readTextFile(String path) {
		List<String> result = new ArrayList<String>();

		try {

			FileInputStream fstream = new FileInputStream(path);
			
			DataInputStream in = new DataInputStream(fstream);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			
			for(String strLine = br.readLine(); strLine != null; strLine = br.readLine()) {
				result.add(strLine);
			}
			in.close();
			
		} catch (Exception e) {
			new RuntimeException("Can not read from file ["+path+"]");
		}
		
		return result;
	}
	
	final static public String readTextFile2String(String path) {
		String result = "";

		try {

			FileInputStream fstream = new FileInputStream(path);
			
			DataInputStream in = new DataInputStream(fstream);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			
			for(String strLine = br.readLine(); strLine != null; strLine = br.readLine()) {
				result += strLine + "\n";
			}
			in.close();
			
		} catch (Exception e) {
			new RuntimeException("Can not read from file ["+path+"]");
		}
		
		return result;
	}
}
