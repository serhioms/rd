package ca.mss.rd.trader.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.io.TextFileReader;
import ca.mss.rd.util.io.UtilFile;

public class RecordDataToFile {

	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(RecordDataToFile.class);

	static public int DATA_MAX_ACCUMULATEED_ROWS = 10;
	
	static public String DATA_ROOT_FOLDER = "data";
	static public String FILE_ROOT_DATE_FORMAT = "yyyy";
	static public String FILE_SUBROOT_DATE_FORMAT = "MM-MMM";
	static public String FILE_SUBFOLDER_DATE_FORMAT = "ww";
	static public String FILE_NAME_DATE_FORMAT = "yyyyMMdd";
	static public String FILE_EXT = ".txt";
	
	static { 
		UtilProperty.readConstants(RecordDataToFile.class); 
	}

	final private String subFolder;
	private String filePostfix;
	private File file = null;
	final private List<String> accumulatedRows = new ArrayList<String>();;
	
	public RecordDataToFile(String subFolder) {
		this.subFolder = DATA_ROOT_FOLDER+File.separator+subFolder;
	}

	public final void setFilePostfix(String filePostfix){
		this.filePostfix = filePostfix;
	}
	
	public final String getLastRow(){
		String lastrow = null;
		if( UtilFile.isFileExist(getFileName()) ){
			TextFileReader tfr = new TextFileReader(getFileName());
			tfr.open();
			for(String row = lastrow = tfr.readRow(); row != null ;row = tfr.readRow()){
				if( !UtilMisc.isEmpty(row) )
					lastrow = row;
			}
		}
		return lastrow;
	}

	public final String getFileName(){
		Date now = UtilDateTime.now();
		return subFolder+File.separator+
				UtilDateTime.format(now, FILE_ROOT_DATE_FORMAT)+File.separator+
				UtilDateTime.format(now, FILE_SUBROOT_DATE_FORMAT)+File.separator+
				UtilDateTime.format(now, FILE_SUBFOLDER_DATE_FORMAT)+File.separator+
				UtilDateTime.format(now, FILE_NAME_DATE_FORMAT)+"="+filePostfix+FILE_EXT;
	}
	
	public final boolean create(){
		file = new File(getFileName());
		if( !UtilFile.isFileExist(file) ){
			if( UtilFile.createFile(file) ){
				if( Logger.WARNING.isOn ) Logger.WARNING.printf("Dump to [file=%s]", file);
				return true;
			}
		}
		return false;
	}
	
	public final void dumpAppend(String row){
		accumulatedRows.add(row);
		assert( Logger.VERBOSE.isOn ? Logger.VERBOSE.printf("Add [   row=%s]", row): true);
		if( accumulatedRows.size() >= DATA_MAX_ACCUMULATEED_ROWS ){
			dumpFlush();
		}
	}

	public final void dumpFlush(){
		int size = accumulatedRows.size();
		if( size > 0 ){
			UtilFile.appendRow(file, accumulatedRows);
		}
		assert( Logger.VERBOSE.isOn ? Logger.VERBOSE.printf("Dump flushed [file=%s][size=%d]", file, size): true);
	}

	public final void dumpClose(){
		dumpFlush();
		if( Logger.WARNING.isOn ) Logger.WARNING.printf("Dump closed [file=%s]", file);
		file = null;
	}

}
