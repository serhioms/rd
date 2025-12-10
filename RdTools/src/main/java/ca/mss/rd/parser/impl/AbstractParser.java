package ca.mss.rd.parser.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.mss.rd.util.UtilReflection;
import ca.mss.rd.util.UtilString;
import ca.mss.rd.util.io.InputReader;
import ca.mss.rd.util.io.TextFileReader;
import ca.mss.rd.util.io.TextFileWriter;
import ca.mss.rd.util.runnable.RdRunnable;

abstract public class AbstractParser<Row extends ParserRow> extends RdRunnable implements Iterator<Row>, ParserRow  {

	final static public String module = AbstractParser.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	abstract public Map<String, String[]> readHeader();
	abstract public String[][][] readTrailer();
	
	final public String srcFile;
	private InputReader input;
	
	public String getSubClassName(){
		return (String) UtilReflection.getStaticProperty(this, "module");
	}

	public AbstractParser() {
		this(null);
	}

	public AbstractParser(String srcFile) {
		super(module);
		this.srcFile = srcFile;
	}

	public InputReader instantiateInputReader(){
		return new TextFileReader(srcFile);
	}
	
	/*
	 * Check file validity
	 */
	public boolean isValid() {

		Map<String, String[]> header = readHeader();
		Map<String, String[][]> sdef = headerDefinition();
		Map<String, Class<?>> cdef = headerClasses();

		for (Iterator<Entry<String, String[]>> hiter = header.entrySet().iterator(); hiter.hasNext();) {
			Entry<String, String[]> hitem = hiter.next();

			String key = hitem.getKey();

			if ( sdef != null) {

				String[][] def =  sdef.get(key);

				if( def == null )
					throw new RuntimeException("Header [" + key + "] is not defined as metadata");

				String[] dfields = def[1];
	
				if( dfields == null )
					throw new RuntimeException("Header [" + key + "] is not defined as metadata");
	
				// Check Array implementation
				String[] fields = hitem.getValue();
				for (int j = 0; j < fields.length; j++) {
					if( !fields[j].equals(dfields[j]) ) {
						throw new RuntimeException("Field [" + fields[j] + "] is not defined in metadata");
					}
				}
			}
			
			if ( cdef != null) {

				@SuppressWarnings("rawtypes")
				Class<?> clazz =  cdef.get(key);

				if (clazz == null)
					throw new RuntimeException("Header [" + key + "] is not implemented as subclass [" + key + "] of class ["
							+ getSubClassName() + "]");
	
				String[] fields = hitem.getValue();
				// Check Object implementation
				for (int j = 0; j < fields.length; j++) {
					String method = "get" + UtilString.buildIdentifier(fields[j]);
					if (!UtilReflection.isStaticMethodExist(method, clazz)) {
						throw new RuntimeException("Field [" + fields[j] + "] is not implemented as method [" + method
								+ "] for class [" + key + "]");
					}
				}
			}

		}
		return true;
	}

	/*
	 * No remove implementation by default
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new RuntimeException("Can not remove item from read-only collection");
	}

	/*
	 * Implement hidden file reader interface
	 */
	
	protected String getFirstRow() {
		if( input != null ){
			input.close();
		}
		input = instantiateInputReader();
		return input.open().readRow();
	}

	protected String getNextRow() {
		return input.readRow();
	}

	protected String generatePackage(){
		return this.getClass().getPackage().getName();
	}
	
	protected String generateSubClassSimpleName(){
		String[] className = getSubClassName().split("\\.");
		return  className[className.length-1]+ "Row";
	}
	
	/*
	 * Generate data row source code
	 */
	public void generateCode() {

		Map<String, String[]> header = readHeader();

		String genClass = generateSubClassSimpleName();
		String genFile = "src\\"+generatePackage().replace('.','\\')+ "\\" + genClass + ".java";
		try {
			TextFileWriter file = new TextFileWriter(genFile).open();

			file.writeln("package "+generatePackage()+";");
			file.writeln("import java.util.HashMap;");
			file.writeln("import java.util.Map;");
			file.writeln("import ca.mss.rd.parser.impl.DefaultRow;");
			file.writeln("import ca.mss.rd.parser.impl.GenericRecord;");
			file.writeln("import ca.mss.rd.parser.impl.ParserRow;");
			file.writeln();

			file.writeln("public class " + genClass + " extends DefaultRow implements ParserRow {");
			file.writeln();
			file.writeln("final static public String module = " + genClass + ".class.getName();");
			file.writeln("final static public long serialVersionUID = module.hashCode();");
			file.writeln();

			file.writeln("static private String map(Map<String,Integer> map, String name, int index){");
			file.writeln("map.put(name, index);");
			file.writeln("return name;");
			file.writeln("}");
			file.writeln();

			List<String[]> genConstants = new ArrayList<String[]>();

			for (Iterator<Entry<String, String[]>> hiter = header.entrySet().iterator(); hiter.hasNext();) {
				Entry<String, String[]> hitem = hiter.next();

				String hederIdent = hitem.getKey();
				String[] hederFields = hitem.getValue();

				String genSubClass = UtilString.capitolize(hederIdent);
				String subHeader = "HEADER_" + genSubClass.toUpperCase();
				String subData = "DATA_" + genSubClass.toUpperCase();
				String subDataMapper = subData+"_MAPPER";
				String subDataHeader = subData+"_HEADER";

				file.writeln("static private Map<String,Integer> "+subDataMapper+" = new HashMap<String,Integer>();"); 
				file.writeln();

				file.write("static private int index=0;");
				file.write("static private String[] " + subDataHeader + " = new String[]{");
				for (int i = 0; i < hederFields.length; i++) {
					if (i > 0)
						file.write(",");
					file.write("map("+subDataMapper+",\"" + hederFields[i]+ "\",index++)");
				}
				file.writeln("};");
				file.writeln();
				
				file.write("private String[][] " + subData + " = new String[][]{null, "+subDataHeader+"};");
				file.writeln();

				genConstants.add(new String[] { subHeader, genSubClass, subData });

				file.writeln("final public " + genSubClass + " " + genSubClass.toLowerCase() + " = new " + genSubClass + "();");
				file.writeln();
				file.writeln();

				file.writeln("public class " + genSubClass + " implements GenericRecord {");
				for (int i = 0; i < hederFields.length; i++) {
					file.writeln("public String get" + UtilString.buildIdentifier(hederFields[i]) + "(){");
					file.writeln("return " + subData + "[0][" + i + "];");
					file.writeln("}");
				}

				file.writeln("@Override");
				file.writeln("public String getValue(String name) {");
				file.writeln("return "+subData+"[0]["+subDataMapper+".get(name)];");
				file.writeln("}");

				file.writeln("@Override");
				file.writeln("public String[] getHeaders() {");
				file.writeln("return "+subDataHeader+";");
				file.writeln("}");
				
				file.writeln("}");
			}

			if( genConstants.size() > 1 ){
				for (int i = 0, size = genConstants.size(); i < size; i++) {
					String[] constant = genConstants.get(i);
					file.writeln("final public static String " + constant[0] + " = \"" + constant[1] + "\";");
				}
				file.writeln();
			}
			
			file.writeln();

			file.writeln("public boolean populate(String[] data) {");
			if (genConstants.size() > 1) {
				file.writeln();
				for (int i = 0, size = genConstants.size(); i < size; i++) {
					String[] constant = genConstants.get(i);
					if (i == 0) {
						if (size == 1)
							file.writeln("// TODO: Choose right header somehow here");
						file.writeln("if( \"CONSTANT_" + i + "\".equals(data[" + i + "]) ){");
					} else
						file.writeln("} else if( \"CONSTANT_" + i + "\".equals(data[" + i + "]) ) {");

					file.writeln("this." + genClass.toUpperCase() + " = " + constant[0] + ";");
					file.writeln("this.data = " + constant[2] + ";");
					file.writeln("this.data[0] = data;");
				}
				file.writeln("}");
				file.writeln();
			} else {
				file.writeln("this.data[0] = data;");
			}

			file.writeln("return true;");
			file.writeln("}");
			file.writeln();

			if( genConstants.size() > 1 ){
				file.writeln("private String " + genClass.toUpperCase() + ";");
				file.writeln();
	
				for (int i = 0, size = genConstants.size(); i < size; i++) {
					String[] constant = genConstants.get(i);
	
					file.writeln("public boolean is" + constant[1] + "() {");
					file.writeln("return " + constant[0] + ".equals(" + genClass.toUpperCase() + ");");
					file.writeln("}");
					file.writeln();
				}
			}

			file.writeln("public "+genClass+"() {");
			file.writeln("headerTitles = new HashMap<String, String[][]>();");
			for (int i = 0, size = genConstants.size(); i < size; i++) {
				String[] constant = genConstants.get(i);
				if( size == 1 )
					file.writeln("headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, " + constant[2] + ");");
				else
					file.writeln("headerTitles.put(" + constant[0] + ", " + constant[2] + ");");
			}
			if (genConstants.size() == 1) {
				file.writeln();
				for (int i = 0, size = genConstants.size(); i < size; i++) {
					String[] constant = genConstants.get(i);
					file.writeln("data = " + constant[2] + ";");
				}
			}
			file.writeln();
			for (int i = 0, size = genConstants.size(); i < size; i++) {
				String[] constant = genConstants.get(i);
				if( size == 1 )
					file.writeln("headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, " + constant[1] + ".class);");
				else
					file.writeln("headerClasses.put(" + constant[0] + ", " + constant[1] + ".class);");
			}
			file.writeln("}");
			file.writeln();

			file.writeln("}");

			file.close();
			
			if( logger.isDebugEnabled() ) logger.debug("Successfully generate parser sub interface source [file=" + genFile + "]");
		} catch (Exception e) {
			logger.error("Failed to generate [file=" + genClass + "]", e);
		}
	}

	public void test() {

		if (isValid()) {

			logger.debug("File is valid");
			logger.debug("[class=" + getSubClassName() + "]\n");

			logger.debug("Header(s):");
			Map<String, String[]> header = readHeader();
			
			for (Iterator<Map.Entry<String,String[]>> iter=header.entrySet().iterator(); iter.hasNext(); ) {
				Entry<String, String[]>  entry = iter.next();
				logger.debug(" #"+entry.getKey());
				String[] title = entry.getValue();
				for (int i=0; i<title.length; i++) {
					logger.debug("\t" + "[" + title[i]+ "]");
				}
			}

			logger.debug("Data (first 100):");
			
			int firstTen = 100;
			for(int n=1; hasNext(); ){
				String[][] data = next().toArray();

				if( data[0].length > 0 )
					if ( firstTen-- > 0){
						logger.debug("\t record #" + n++);
						for (int i = 0; i < data[0].length; i++) {
								logger.debug("\t" + (data[1] == null ? "" : "[" + data[1][i] + "] = ") + "[" + data[0][i] + "]");
						}
					}
			}

			logger.debug("Trailer:");
			String[][][] trailer = readTrailer();
			for (int i=0; i < trailer.length; i++) {
				for (int j=0; j < trailer[i][1].length; j++) {
					logger.debug("\t" + "[" + trailer[i][1][j] + "] = [" + (trailer[i][0]!=null?trailer[i][0][j]:"N/A") + "]");
				}
			}
		} else {
			logger.error(getSubClassName() + " file is not valid");
		}
	}

	/*
	 *  Generic data row interface
	 */
	@SuppressWarnings("unchecked")
	final protected Row row = (Row )instantiateRow(); 
	
	public Object instantiateRow(){
		try {
			return UtilReflection.instantiateObject(generatePackage()+"."+generateSubClassSimpleName());
		}catch(Throwable t){
			return new DefaultRow();
		}
	}
	
	@Override
	final public Map<String, String[][]> headerDefinition() {
		return row.headerDefinition();
	}

	@Override
	final public Map<String, Class<?>> headerClasses() {
		return row.headerClasses();
	}

	@Override
	final public String[][] toArray() {
		return row.toArray();
	}

	@Override
	final public Row next() {
		return row;
	}

	@Override
	final public boolean populate(String[] data) {
		return row.populate(data);
	}

	@Override
	final public boolean populate(String[] data, String line) {
		return row.populate(data, line);
	}

	
	@Override
	public void runThreadHandler() {
		// Nothing here. Reserved for multithreded usage
	}


}
