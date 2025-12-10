package ca.mss.rd.trader.deprecated;

import java.beans.PropertyChangeSupport;
import java.io.File;


import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.io.UtilFile;
import ca.mss.rd.util.runnable.RdRunnable;

abstract public class RecordDataRunnable extends RdRunnable {

	final static public String module = RecordDataRunnable.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public String DATA_ROOT_FOLDER = "data";
	
	static public String DATE_FORMAT_DEFAULT = "yyyyMMdd=kkmmss";
	
	static { 
		UtilProperty.readConstants(RecordDataRunnable.class); 
	}

	private boolean isRecoderOn;

	public RecordDataRunnable(String threadName) {
		super(threadName);
		this.isRecoderOn = false;
	}

	public final boolean isRecoderOn() {
		return isRecoderOn;
	}

	public void setRecoderOn() {
		this.isRecoderOn = true;
	}

	public void setRecoderOff() {
		this.isRecoderOn = false;
	}
	
	
	private File file = null;

	protected void dumpCreate(String row, String ident){
		file = new File(DATA_ROOT_FOLDER+File.separator+UtilDateTime.format(UtilDateTime.now(), DATE_FORMAT_DEFAULT)+"="+ident+".txt");
		if( UtilFile.isFileExist(file) ){
			UtilFile.delete(file);
		}
		UtilFile.createFile(file);
		UtilFile.appendRow(file, row);
		if( logger.isDebugEnabled()){
			logger.debug("Dump to [file="+file+"]");
			logger.debug("Add [header="+row+"]");
		}
	}
	
	protected void dumpAppend(String row){
		UtilFile.appendRow(file, row);
		if( logger.isDebugEnabled()){
			logger.debug("Add [row="+row+"]");
		}
	}
	
	
	/*
     * Property change support
     */
    final protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
}
