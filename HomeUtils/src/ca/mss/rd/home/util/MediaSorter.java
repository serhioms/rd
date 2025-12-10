package ca.mss.rd.home.util;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.io.UtilFile;

public class MediaSorter {
	final public static String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	final public static String DATE_FORMAT_YYYY = "yyyy";
	
	static public boolean isTest = true;

	static public String TARGET_FOLDER_PHOTO_3D = "D:\\media\\ARHIVE\\3D\\PHOTO";
	static public String TARGET_FOLDER_MOVIE_3D = "D:\\media\\ARHIVE\\3D\\VIDEO";

	static public String TARGET_FOLDER_PHOTO_2D = "D:\\media\\ARHIVE\\2D\\PHOTO";
	static public String TARGET_FOLDER_MOVIE_2D = "D:\\media\\ARHIVE\\2D\\VIDEO";
	
	static public String TARGET_FOLDER_ANDROID = "D:\\media\\zandroid\\memo-all";
	
	static public boolean IS_IN_YEAR_SUBFOLDER = true;
	
	static public Set<String> SCAN_FOLDER_WILDCARD = UtilMisc.toSet(
			"^.*\\\\DCIM\\\\[0-9][0-9][0-9].....", 
			"^.*\\\\[0-9][0-9][0-9]_FUJI", 
			"^.*\\\\PRIVATE\\\\AVCHD\\\\BDMV\\\\STREAM",
			"^.*\\\\android"
			);

	static public Set<String> MOVE_WHOLE_FOLDER_SPLITTER = UtilMisc.toSet(
			"\\BDMV\\");

	static public Set<String> EXT_MOVIE = UtilMisc.toSet("AVI", "MTS");
	static public Set<String> EXT_PHOTO = UtilMisc.toSet("JPG", "MPO");
	static public Set<String> EXT_3D = UtilMisc.toSet("MPO");

	static public String ANDROID_CONTENT = "android";
	
	static private int scanLevel = 0;
	

	public static void sort3D(File sortDir) {
		
		String sourceFolder = sortDir.getAbsolutePath();
		String sortDirName = (new File(sourceFolder)).getName();
		
		while( ".".equals(sortDirName) ){
			sourceFolder = sourceFolder.replaceAll("\\\\\\.", "");
			sortDirName = (new File(sourceFolder)).getName();
		}
		
		Set<String> photo2DNoExt = new HashSet<String>();
		Map<String, String> photo2D = new HashMap<String, String>();
		
		Date modified = null;

		String[] files = UtilFile.scanDirectory(sortDir);
		for (String fileName : files) {
			File file = new File(sourceFolder+File.separator+fileName);
			
			if( file.isDirectory() ){
				continue;
			}

			if( modified == null ){
				modified = new Date(file.lastModified());
			}
			
			String fileExtension = UtilFile.getExtension(fileName).toUpperCase();
			
			if( !EXT_3D.contains(fileExtension) && EXT_PHOTO.contains(fileExtension)){
				String fileNoExt = UtilFile.getNameWithoutExtension(fileName);
				photo2DNoExt.add(fileNoExt);
				photo2D.put(fileNoExt, fileName);
			}
		}

		if( photo2D.isEmpty() ){
			System.out.println("Nothing to do!");
			System.exit(0);
		}
		
		String target2DFolder = TARGET_FOLDER_PHOTO_2D+File.separator+UtilDateTime.format(modified, DATE_FORMAT_YYYY);
		createFolder(target2DFolder);

		target2DFolder = target2DFolder+File.separator+sortDirName;
		createFolder(target2DFolder);

		String deleteFolder = sourceFolder+File.separator+".deleted";
		createFolder(deleteFolder);

		for (String fileName : files) {
			File file = new File(sourceFolder+File.separator+fileName);
			
			if( file.isDirectory() ){
				continue;
			}

			
			String fileExtension = UtilFile.getExtension(fileName).toUpperCase();
			
			if( EXT_3D.contains(fileExtension) && EXT_PHOTO.contains(fileExtension)){
				String photo3DNoExt = UtilFile.getNameWithoutExtension(fileName);
				
				if( photo2DNoExt.contains(photo3DNoExt) ){
					String file2D = photo2D.get(photo3DNoExt);
					System.out.println("Move 2D ["+file2D+"] to ["+target2DFolder+"]");
					if( !isTest )
						UtilFile.move(sourceFolder+File.separator+file2D, target2DFolder);
				} else {
					System.out.println("Move 3D ["+fileName+"] to ["+deleteFolder+"]");
					if( !isTest )
						UtilFile.rename(file, deleteFolder+File.separator+fileName);
				}
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String directory = ".";
		if( args.length > 0 ){
			directory = args[0];
			for(int i=1,max=args.length-1; i<max; i++){
				directory = directory+"="+args[i];
			}
			if( !UtilFile.isFileExist(directory) )
				throw new RuntimeException("Directory does not exists ["+directory+"]["+toString(args)+"]");
		} else {
			System.out.println("Media file sorter usage:");
			System.out.println("MediaSorter [do]");
			System.out.println();
			System.out.println("Looking for folders: "+SCAN_FOLDER_WILDCARD.toString());
			System.out.println("Looking for extensions: "+EXT_MOVIE.toString()+EXT_PHOTO.toString()+EXT_3D.toString()+"]");
			System.out.println("Move 2D to ["+TARGET_FOLDER_PHOTO_2D.toString()+"]["+TARGET_FOLDER_MOVIE_2D.toString()+"]");
			System.out.println("Move 3D to ["+TARGET_FOLDER_PHOTO_3D.toString()+"]["+TARGET_FOLDER_MOVIE_3D.toString()+"]");
			System.out.println("Move ANDROID to ["+TARGET_FOLDER_ANDROID.toString()+"]");
			System.exit(1);
		}
		
		if( args.length > 1 ){
			if( "do".equalsIgnoreCase(args[args.length-1]))
				isTest = false;
		}
		
		File sortDir = new File(directory);
		if( isSort3D(sortDir) ){
			System.out.println("Sort 3D photo ["+directory+"]"+(isTest?" test!":" do!"));
			System.out.println();
			
			sort3D(sortDir);
		} else {
			System.out.println("Media scan ["+directory+"]"+(isTest?" test!":" do!"));
			System.out.println();
			
			mediaScan(sortDir);
		}
		
	}

	public static String getDescription(String path){
		if( path.contains("=") ){
			String[] descr = path.split("=");
			String description = descr[descr.length-1];
			if( description.contains(File.separator) )
				return description.substring(0, description.indexOf(File.separator));
			return description;
		}
		return "description";
	}
	
	public static boolean isSort3D(File sortDir) {

		String[] files = UtilFile.scanDirectory(sortDir);

		boolean is3D = false;
		boolean hasMovie = false;
		boolean hasPhoto = false;
		int hasDirectory = 0;

		String sourceFolder = sortDir.getAbsolutePath();
		
		for (String fileName : files) {
			File file = new File(sourceFolder+File.separator+fileName);
			
			if( file.isDirectory() ){
				hasDirectory++;
				continue;
			}

			String fileExtension = UtilFile.getExtension(fileName);
			
			if( EXT_3D.contains(fileExtension) ){
				is3D = true;
			}
			
			if( EXT_MOVIE.contains(fileExtension) ){
				hasMovie = true;
			}
			
			if( EXT_PHOTO.contains(fileExtension) ){
				hasPhoto = true;
			}
		}
		
		return hasDirectory <=1 && hasPhoto && is3D && !hasMovie;
	}

	public static void mediaSort(File sortDir) {

		String[] files = UtilFile.scanDirectory(sortDir);

		boolean is3D = false;
		boolean hasMovie = false;
		boolean hasPhoto = false;
		String targetFolder = null;

		String sourceFolder = sortDir.getAbsolutePath();
		
		String description = getDescription(sourceFolder);
		
		Set<String> photo3DSet = new HashSet<String>();
		Set<String> photo2DSet = new HashSet<String>();
		
		for (String fileName : files) {
			File file = new File(sourceFolder+File.separator+fileName);
			
			if( file.isDirectory() )
				continue;

			String fileExtension = UtilFile.getExtension(fileName);
			
			if( EXT_3D.contains(fileExtension) ){
				is3D = true;
				if( EXT_PHOTO.contains(fileExtension) ){
					photo3DSet.add(fileName);
				} else {
					photo2DSet.add(fileName);
				}
			}
			
			if( EXT_MOVIE.contains(fileExtension) ){
				hasMovie = true;
			}
			
			if( EXT_PHOTO.contains(fileExtension) ){
				hasPhoto = true;
			}

			if( hasMovie || hasPhoto ){
				String targetParent = sourceFolder; // would be override

				if( hasMovie )
					targetParent = is3D? TARGET_FOLDER_MOVIE_3D: TARGET_FOLDER_MOVIE_2D;
					
				if( hasPhoto )
					targetParent = is3D? TARGET_FOLDER_PHOTO_3D: (sourceFolder.toLowerCase().contains(ANDROID_CONTENT)?TARGET_FOLDER_ANDROID: TARGET_FOLDER_PHOTO_2D);
					
				if( hasMovie || hasPhoto ){
					Date modified = new Date(file.lastModified());
		
					String subfolder = UtilDateTime.format(modified, DATE_FORMAT_YYYY);
					targetFolder = targetParent+File.separator+subfolder;
	
					subfolder = UtilDateTime.format(modified, DATE_FORMAT_YYYYMMDD)+"="+description+scanLevel;
					targetFolder = targetFolder+File.separator+subfolder;
				}
			}
		}
		
		boolean isMoveWholeFolder = false;
		String wholeFolderSplitter = null;
		
		for( String splitter: MOVE_WHOLE_FOLDER_SPLITTER){
			if( isMoveWholeFolder=sourceFolder.contains(splitter) )
				wholeFolderSplitter = splitter;
				break;
		}

		if( isMoveWholeFolder ){
			if( targetFolder != null ){ // if source is not empty
				// Move whole folder 
				String[] splits = sourceFolder.split(wholeFolderSplitter.replace("\\", "\\\\"));
				File moveDir = new File(splits[0]);
				moveFolderTo(moveDir, targetFolder, moveDir.getName());
			}
		} else {
			Set<String> target2DPhoto = new HashSet<String>();
			
			// move file by file
			for (String fileName : files) {
				File file = new File(sortDir+File.separator+fileName);
				
				if( file.isDirectory() )
					continue;
	
				String fileExtension = UtilFile.getExtension(fileName);
				
				String parent = sourceFolder;

				boolean isPhoto = false;
				if( EXT_MOVIE.contains(fileExtension) )
					parent = is3D? TARGET_FOLDER_MOVIE_3D: TARGET_FOLDER_MOVIE_2D;
					
				if( isPhoto=EXT_PHOTO.contains(fileExtension) )
					parent = is3D? TARGET_FOLDER_PHOTO_3D: (sourceFolder.toLowerCase().contains(ANDROID_CONTENT)?TARGET_FOLDER_ANDROID: TARGET_FOLDER_PHOTO_2D);
					
				Date modified = new Date(file.lastModified());
	
				String subfolder = UtilDateTime.format(modified, DATE_FORMAT_YYYY);
				createFolder(parent, subfolder);
				targetFolder = parent+File.separator+subfolder;
				
				subfolder = UtilDateTime.format(modified, DATE_FORMAT_YYYYMMDD)+"="+description+scanLevel;
				createFolder(targetFolder, subfolder);
				targetFolder = targetFolder+File.separator+subfolder;

				moveFileTo(file, targetFolder, fileName);
				
				if( is3D && isPhoto && !target2DPhoto.contains(targetFolder)){
					target2DPhoto.add(targetFolder); 
				}
			}
			
			// Run auto 3D sorting if original 3D folders get revised i.e. some 2D photo were removed.
			if( is3D && hasPhoto && !targetFolder.isEmpty() && photo3DSet.size() > photo2DSet.size() ){
				for (String sort3Dir : target2DPhoto) {
					sort3D(new File(sort3Dir));
				}
			}
		}
	}
	
	private static Set<String> createdColdersCach = new HashSet<String>();
	
	static public void moveFileTo(File file, String target, String fileName){
		createFolder(target);
		System.out.println("Move file ["+file.getAbsolutePath()+"] to ["+target+File.separator+fileName+"]");
		if( !isTest )
			file.renameTo(new File(target+File.separator+fileName));
	}

	static public void moveFolderTo(File folder, String target, String subFolder){
		createFolder(target);
		System.out.println("Move folder ["+folder.getAbsolutePath()+"] to ["+target+File.separator+subFolder+"]");
		if( !isTest )
			folder.renameTo(new File(target+File.separator+subFolder));
	}

	static public void createFolder(String target){
		if( !UtilFile.isFileExist(target)){
			if( !createdColdersCach.contains(target) ){ 
				System.out.println("Create folder ["+target+"]");
				createdColdersCach.add(target);
			}
			if( !isTest )
				UtilFile.createFolder(new File(target));
		}

	}

	static public void createFolder(String parent, String folderName){
		createFolder(parent+File.separator+folderName);
	}

	private static Map<String, Pattern> cachePattern = new HashMap<String, Pattern>();
	public static Pattern getPattern(String wildcard){
		if( !cachePattern.containsKey(wildcard) ){
			cachePattern.put(wildcard, Pattern.compile(wildcard));
		}
		return cachePattern.get(wildcard);

	}
	
	private static String correctMatcher(String path){
		return path.replaceAll("=", "--");
	}
	
	public static void mediaScan(File directory) {
		System.out.println("Scaner ["+directory.getAbsolutePath()+"]");

		scanLevel++;
		String[] folders = UtilFile.scanDirectory(directory);
		
		if( folders.length > 0 )
			for (String name : folders) {
				File folder = new File(directory+File.separator+name);
				
				if( folder.isFile() ){
					continue;
				}

				boolean isMatches = false;
				for( String wildcard: SCAN_FOLDER_WILDCARD){
					if( isMatches=getPattern(wildcard).matcher(correctMatcher(folder.getAbsolutePath())).matches() )
						break;
				}
				
				if( isMatches ){
					mediaSort(folder);
				} else {
					mediaScan(folder);
				}
			}
	}

	final static public String toString(String[] args){
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<args.length; i++){
			if( i > 0 ) buf.append(",");
			buf.append(args[i]);
		}
		return buf.toString();
	}
	
}
