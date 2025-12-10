package ca.mss.rd.parser.text;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.parser.impl.AbstractParser;
import ca.mss.rd.parser.impl.ParserRow;

abstract public class TextParserUniversal <Row extends ParserRow> extends AbstractParser<Row> {


	final static public String module = TextParserUniversal.class.getName();
	final static public long serialVersionUID = module.hashCode();

	abstract public String getHeaderKey();
	abstract public String[][] getLex();
	abstract public String[][] getSkip();
	abstract public String[] getHeader();

	final public String PARSER_HEADER; 
	final public String[][] PARSER_LEX;
	final public String[][] PARSER_SKIP;
	final public String[] HEADER;
	
	// Header/Trailer
	final static protected Map<String, String[]> header = new HashMap<String, String[]>(1);
	final static protected String[][][] trailer = new String[][][]{{{""}, {""}}};

	// A little privacy
	protected String[] _row;
	protected String title, _next;

	// You can override title to be constant rather then header   
	public String getTitle(){
		return title;
	}

	public TextParserUniversal(String path) {
		super(path);
		this.PARSER_HEADER = getHeaderKey();
		this.PARSER_LEX = getLex();
		this.PARSER_SKIP = getSkip();
		this.HEADER = getHeader();
		this.title = PARSER_HEADER;
		this._row = new String[HEADER.length];
	}

	public TextParserUniversal() {
		this(null);
	}

	@Override
	public Map<String, String[]> readHeader() {
		for (_next=getFirstRow(); _next != null; _next = getNextRow()) {
			if( _next.contains(PARSER_HEADER) ){
				return header;
			}
		}
		throw new RuntimeException(module+" can not find header ["+PARSER_HEADER+"] in source");
	}

	@Override
	final public boolean hasNext() {
		if( _next == null )
			return false;
		for(int index=0; index<_row.length && _next!=null; index++ ){ 
	 		while( !_next.contains(PARSER_LEX[index][0]) )
				if( (_next = getNextRow()) == null )
					if( index == (_row.length-1) )
						return false;
					else
						break;
	 		if( _next == null )
	 			break;
	 		else
	 			parseRow(index);
		}
		return populate(_row);
	}

	/*
	 * Here is actual parsing
	 */
	final private void parseRow(int index) {
		String[] mid = new String[1];
		_row[index] = null;

		if( PARSER_LEX[index][6] != null ){
			// Skip some lines
			for(int skip = Integer.parseInt(PARSER_LEX[index][6]); skip > 0; skip--){
				if( PARSER_LEX[index][8] != null ){
					if( _next.contains(PARSER_LEX[index][8]) ){
						_row[index] = PARSER_LEX[index][8];
						return;
					}
				}
				_next = getNextRow();
			}
		}
				
		for(; PARSER_LEX[index+1][0].equals(PARSER_LEX[index][0]) || !_next.contains(PARSER_LEX[index+1][0]); _next=getNextRow()){

			// Skip some blocks
	 		for(int i=0; i<PARSER_SKIP.length; i++){
	 			if( _next.contains(PARSER_SKIP[i][0])){
	 				_next=getNextRow();
	 				if( PARSER_SKIP[i][1] != null ){
		 		 		while( !_next.contains(PARSER_SKIP[i][1]) ){
		 		 			_next = getNextRow();
		 		 		}
			 			_next=getNextRow();
	 				}
	 			}
	 		}
			
	 		// Place to trim
	 		_next = _next.trim();
	 		
			if( _next.length()>0 && (PARSER_LEX[index][1] == null || _next.contains(PARSER_LEX[index][1])) ){

		 		// Universal parsing
				if( PARSER_LEX[index][1] != null )
					_next = _next.split(PARSER_LEX[index][1])[1];

				if( PARSER_LEX[index][2] != null )
					mid = _next.split(PARSER_LEX[index][2]);
				else if( PARSER_LEX[index][3] != null )
					mid[0] = _next.split(PARSER_LEX[index][3])[0];
				else 
					mid[0] = _next;
				
				if( mid.length > 1 ){
					_next = mid[0] + PARSER_LEX[index][4] + (mid[1].split(PARSER_LEX[index][3])[0]);
				} else
					_next = mid[0];

				if( _next.length() == 0 )
					continue;
				
				if( _row[index] == null )
					_row[index] = _next;
				else
					_row[index] += PARSER_LEX[index][5]+_next;
				
				if( PARSER_LEX[index][7] == null )
					return;
			}
		}
	}

	@Override
	final public String[][][] readTrailer() {
		return trailer;
	}

	@Override
	final public boolean isValid() {
		return true; // In fact can not be validated cause it is universal!
	}

}
