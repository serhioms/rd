/**
 * 
 */
package ca.mss.rd.workflow.reader.xpdl;


/**
 * @author smoskov
 *
 */
public class FileReaderXPDL extends WkfReaderXPDL {

	private String fileXPDL;

	/**
	 * @param fileXPDL
	 */
	public FileReaderXPDL(String fileXPDL) {
		setFileXPDL(fileXPDL);
	}

	/**
	 * @return the fileXPDL
	 */
	public final String getFileXPDL() {
		return fileXPDL;
	}

	/**
	 * @param fileXPDL the fileXPDL to set
	 */
	public final void setFileXPDL(String fileXPDL) {
		this.fileXPDL = fileXPDL;
		parseWorkflow(fileXPDL);
	}

}
