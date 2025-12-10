package ca.mss.rd.util.io;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import ca.mss.rd.util.UtilSort;


public class UtilFile {
	final static public String module = UtilFile.class.getName();
	final static public long serialVersionUID = module.hashCode();

	final static public String getNameWithoutExtension(String fileName){
		int index = fileName.lastIndexOf(".");
		if( index >= 0 )
			return fileName.substring(0, index);
		else
			return fileName;
	}
	
	final static public String getExtension(String fileName){
		int index = fileName.lastIndexOf(".");
		if( index >= 0 )
			return fileName.substring(index+1);
		else
			return "";
	}
	
	final static public long fileSize(File file) {
		return file.length();
	}

	final static public long fileSize(String file) {
		return fileSize(new File(file));
	}

	final static public void delete(File file) {
		file.delete();
	}

	final static public void delete(Object file) {
		delete(new File(file.toString()));
	}

	final static public void rename(File from, File to) {
		from.renameTo(to);
	}

	final static public void rename(File from, String to) {
		from.renameTo(new File(to));
	}

	final static public void move(File from, File to) {
		from.renameTo(new File(to.getAbsolutePath()+File.separator+from.getName()));
	}

	final static public void rename(String from, String to) {
		rename(new File(from), new File(to));
	}

	final static public void move(String from, String to) {
		move(new File(from), new File(to));
	}

	final static public void move(File from, String to) {
		move(from, new File(to));
	}

	final static public boolean isSame(String path1, String path2) {
		
		long size1 = fileSize(path1.toString()), size2 = fileSize(path2.toString());

		if( size1 != 0 && size2 == 0 )
			return true;
		else if( size1 == 0 && size2 != 0 )
			return false;
		else if( size1 == 0 && size2 == 0 )
			return true;
		else if( size1 == size2 )
			return true;

		TextFileReader file1 = null;
		TextFileReader file2 = null;

		try {
			file1 = new TextFileReader(path1.toString());
			file2 = new TextFileReader(path2.toString());
			
			String row1 = file1.open().readRow(), row2 = file2.open().readRow();
			
			if( row1 != null && row2 == null )
				return true;
			else if( row1 == null && row2 != null )
				return false;
			else if( row1 == null && row2 == null )
				return true;
			
			for(; row1 != null && row2 != null ; row1=file1.readRow(), row2=file2.readRow())
				if( !row1.equals(row2) )
					return false;
			
		}catch(Throwable t){
			if( file1 != null && file2 == null )
				return true;
			else if( file1 == null && file2 != null )
				return false;
			else if( file1 == null && file2 == null )
				return true;
		}finally{
			if( file1 != null ) file1.close();
			if( file2 != null ) file2.close();
		}
		
		return true;
	}

	final static public boolean isFileExist(File file) {
		return file.exists();
	}

	final static public boolean isDir(File file) {
		return file.isDirectory();
	}

	final static public boolean isFileExist(String path) {
		return isFileExist(new File(path));
	}

	final static public boolean isDir(String path) {
		return isDir(new File(path));
	}

	final static public boolean isFileParentFolderExist(File file) {
		return file.getParentFile().isDirectory();
	}

	final static public void createParentFolders(File file) {
		try {
			for(File parent = file.getParentFile(); !parent.isDirectory(); parent = file.getParentFile())
				createFolder(parent);
		}catch(Throwable t){
			new RuntimeException("Can not create folders for place file ["+file.getAbsolutePath()+"]");
		}
	}

	final static public void createFolder(File folder) {
		if( folder.isDirectory() )
			return;
		else 
			createFolder(folder.getParentFile());
		
		folder.mkdir();
	}

	final static public void createFile(String fileName) {
		File file = new File(fileName);
		if( file.isFile() )
			return;
		try {
			createParentFolders(file);
			file.createNewFile();
		}catch(Throwable t){
			new RuntimeException("Can not create file ["+file.getAbsolutePath()+"]");
		}
	}

	final static public boolean createFile(File file) {
		if( file.isFile() )
			return false;
		
		try {
			createParentFolders(file);
			return file.createNewFile();
		}catch(Throwable t){
			new RuntimeException("Can not create file ["+file.getAbsolutePath()+"]");
		}
		
		return false;
	}

	final static public void appendRow(File file, String row){
	    FileWriter fw = null;
		try {
		    fw = new FileWriter(file, true);
		    fw.write(row);
		    fw.write("\n");
		    fw.flush();
		} catch(IOException ioe) {
		    
		} finally {
			try { 
				if( fw != null ){
					fw.close();
				}
			} catch(Throwable t){}
		}
	}

	final static public void appendRow(File file, List<String> row){
	    FileWriter fw = null;
		try {
		    fw = new FileWriter(file, true);
		    for(int i=0, size=row.size(); i<size; i++){
			    fw.write(row.get(i));
			    fw.write("\n");
		    }
		    fw.flush();
		} catch(IOException ioe) {
		    
		} finally {
			row.clear();
			try { 
				if( fw != null ){
					fw.close();
				}
			} catch(Throwable t){}
		}
	}

	public static File[] scanFiles(File dir) {
		File[] filenameList = dir.listFiles();
		if (filenameList != null) {
			UtilSort.sortArray(filenameList);
			return filenameList;
		} else {
			return new File[0];
		}

	}

	public static String[] scanDirectory(String directory, String filePattern) {
		FilenameFilter fileFilter = new WildcardFilter(filePattern);
		File sourceDirectory = new File(directory);
		String[] filenameList = sourceDirectory.list(fileFilter);
		if (filenameList != null) {
			UtilSort.sortArray(filenameList);
			return filenameList;
		} else {
			return new String[0];
		}
	}

	public static String[] scanDirectory(File directory) {
		String[] filenameList = directory.list();
		if (filenameList != null) {
			UtilSort.sortArray(filenameList);
			return filenameList;
		} else {
			return new String[0];
		}
	}

	public static void scanDirectory(String directory, String filePattern, boolean isRecursively, List<String> files, List<String> dirs) {
		
		File scanDir = new File(directory);
		
		if( scanDir.isDirectory() ){
			for(String fileName: scanDir.list() ){
				String filePath = UtilFile.normalizeSeparators(directory+scanDir.separator+fileName, '/');
				if( UtilFile.isDir(filePath)){
					if( isRecursively ){
						if( dirs != null ){
							dirs.add(filePath);
						}
						scanDirectory(filePath, filePattern, isRecursively, files, dirs);
					}
				} else if( files != null ) {
					if( filePattern == null ){
						files.add(filePath);
					} else if( filePath.matches(filePattern)){
						files.add(filePath);
					}
				}
			}
		}
	}

	// http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
	
	private static String normalizeSeparators(String path, char separator) {
		return path.replace('\\', '/').replace('/', '\\').replace('\\', separator);
	}

	private static final long K = 1024;
	private static final long M = K * K;
	private static final long G = M * K;
	private static final long T = G * K;

	public static String formatSize(final long value){
	    final long[] dividers = new long[] { T, G, M, K, 1 };
	    final String[] units = new String[] { "TB", "GB", "MB", "KB", "B" };
	    if(value < 1)
	        throw new IllegalArgumentException("Invalid file size: " + value);
	    String result = null;
	    for(int i = 0; i < dividers.length; i++){
	        final long divider = dividers[i];
	        if(value >= divider){
	            result = _format(value, divider, units[i]);
	            break;
	        }
	    }
	    return result;
	}

	private static String _format(final long value,
	    final long divider,
	    final String unit){
	    final double result =
	        divider > 1 ? (double) value / (double) divider : (double) value;
	    return new DecimalFormat("#,##0.#").format(result) + " " + unit;
	}

	public static String extractFileName(String filePath) {
		String[] split = filePath.split("[\\\\|/]");
		return split[split.length-1];
	}
	
	public static String cleanUri(String uri) {
		String[] split = uri.split("\\]");
		return split[split.length-1];	
	}
	
	public static void main(String[] args){
		System.out.println(extractFileName("file:///c:/bns/ctr/ctrapp/ctr/ctrftp/csm/inbox/CSM_CM_FXRate_D_20160325_01.csv"));
		System.out.println(extractFileName("file:\\\\\\c:\\bns\\ctr\\ctrapp\\ctr\\ctrftp\\csm\\inbox\\CSM_CM_FXRate_D_20160325_01.csv"));
		System.out.println(cleanUri("[@WARN_DISC:2][@WARN_REJ:1000000]c:/bns/ctr/ctrapp/ctr/ctrconfig/sqlldr/CSM_2625_fx_rate_v1_50.ctl.out"));
	}
}
