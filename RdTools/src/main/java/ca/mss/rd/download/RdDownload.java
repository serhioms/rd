package ca.mss.rd.download;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Map;


import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.http.UtilHttp;
import ca.mss.rd.http.UtilHttp.RequestType;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.UtilRand;
import ca.mss.rd.util.cache.RdCache;
import ca.mss.rd.util.exceptions.FileNotExists;
import ca.mss.rd.util.io.UtilFile;
import ca.mss.rd.util.io.UtilIO;
import ca.mss.rd.util.runnable.RdRunnableSecundomer;

abstract public class RdDownload extends RdRunnableSecundomer {

	final static public String module = RdDownload.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public String DATE_FORMATTER = "mm:ss.SSS";

	static public int DOWNLOAD_WAIT_BEFORE_REQUEST_MLS = 5000; 

	static public String 	DOWNLOAD_CHARSET = "UTF8";
	static public boolean 	DOWNLOAD_TO_RAM = false;
	static public boolean 	DOWNLOAD_TO_FILE = true;
	static public String	DOWNLOAD_FOLDER = "download";

	static {
		UtilProperty.readConstants(RdDownload.class);
	}
	
	final public String requestURL;
	final public Map<String, String> requestParameters;
	final public RequestType requestType;

	public String filePath;
	public String fileContent;
	public int oldContentHash = 0;

	private boolean isNew;

	protected RdCache<String, String> cache;

	public RdDownload(String requestURL) {
		this(requestURL, null, RequestType.GET);
	}
	
	public boolean doCache(){
		return true;
	}
	
	public RdDownload(String requestURL, Map<String, String> requestParameters, RequestType requestType) {
		super(module);
		this.requestURL = requestURL;
		this.requestParameters = requestParameters;
		this.requestType = requestType;
		this.setThresholdSec(UtilRand.getRandInt(DOWNLOAD_WAIT_BEFORE_REQUEST_MLS));
		try {
			cache = new RdCache<String, String>(module);
			cache.read();
		}catch(FileNotExists e){
			cache = null;
		}
	}

	public RdDownload(String requestURL, RequestType requestType) {
		this(requestURL, null, requestType);
	}

	final public String getDownloadFileCharset(){
		return DOWNLOAD_CHARSET;
	}
	
	public boolean isDownloadToFile(){
		return DOWNLOAD_TO_FILE;
	}
		
	public boolean isDownloadToRAM(){
		return DOWNLOAD_TO_RAM;
	}
		
	final public String getDownloadFolder(){
		return DOWNLOAD_FOLDER;
	}
		
	public String getDownloadFile(){
		if( filePath == null ){
			filePath = getDownloadFolder()+File.separator+UtilHttp.toFilenameRequest(requestURL, requestParameters, "html");
		}
		return filePath;
	}
		
	final private String getDownloadFileTmp(){
		return getDownloadFile()+".tmp";
	}
		
	final public boolean isNew() {
		return isNew;
	}

	final public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	final public String getFileContent() {
		return fileContent;
	}

	protected void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	final public boolean isExists(){
		if( isDownloadToFile() ){
			return UtilFile.isFileExist(getDownloadFile()); 
		} else {
			return true; 
		}
	}

	public void download() throws InterruptedException {
		download(requestParameters);
	}
	
	final public void download(Map<String,String> requestParameters) throws InterruptedException {

		try {
			OutputStream os = null;
			
			if( isDownloadToRAM() ){
				os = new ByteArrayOutputStream();
			} else if( isDownloadToFile() ) {
				os = UtilIO.getOutputStreamFile(getDownloadFileTmp());
			} else {
				setNew(false);
				return;
			}
		
			UtilHttp.write(
					requestURL,
					requestParameters,
					requestType,
					os);

			if( isDownloadToRAM() ){
				String newContent = new String(((ByteArrayOutputStream )os).toByteArray(), getDownloadFileCharset());
				int newContentHash = newContent.hashCode();
				if( oldContentHash != newContentHash ){
					oldContentHash = newContentHash;
					setNew(true);
					setFileContent(newContent);
					if( isDownloadToFile() ){
						UtilIO.write(getFileContent(), UtilIO.getOutputStreamFile(getDownloadFile()));
					}
				} else {
					setNew(false);
				}
			} else if( isDownloadToFile() ){
				if (UtilFile.isSame(getDownloadFile(), getDownloadFileTmp())) {
					UtilFile.delete(getDownloadFileTmp());
					setNew(false);
				} else {
					UtilFile.delete(getDownloadFile());
					UtilFile.rename(getDownloadFileTmp(), getDownloadFile());
					setNew(true);
				}
			}

			if( cache != null ){ 
				String hashKey = Integer.toString(fileContent.hashCode());
				String prevHashKey = cache.get(getDownloadFile());
				
				if( prevHashKey == null ){
					cache.put(getDownloadFile(), hashKey);
					cache.save();
					setNew(true);
				} else if( hashKey.equals(prevHashKey) ){
					setNew(false);
				} else {
					setNew(true);
					cache.put(getDownloadFile(), hashKey);
					cache.save();
				}
			}
		} catch(Throwable t) {
			throw new InterruptedException("Can not download from ["+requestURL+"] as [charset="+getDownloadFileCharset()+"]");
		}

	}

	@Override
	final public void secundomer() throws InterruptedException {
		download();
	}

	@Override
	public void postSecundomer() {
		if( logger.isDebugEnabled()) logger.debug("["+module+"@"+this.toString()+"][start="+UtilDateTime.format(getStart(), DATE_FORMATTER)+"][delay="+getDelay()+" mls][startDownload="+UtilDateTime.format(getWorkStart(), DATE_FORMATTER)+"][duration="+getWorkDuration()+" mls][finishDownload="+UtilDateTime.format(getWorkEnd(), DATE_FORMATTER)+"]["+getDownloadFile()+"]");
	}

	/*
     * Property change support
     */
    final protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    final public void addPropertyChangeListener(PropertyChangeListener listener) {
    	pcs.addPropertyChangeListener(listener);
    }

}
