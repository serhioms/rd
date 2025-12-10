package ca.mss.rd.flow.tools.prop;

public class UriResource {
	
	private String searchPattern, uri, matchUriOutPro;

	
	public UriResource(String searchPattern, String matchUriOutPro, String uri) {
		super();
		this.searchPattern = searchPattern;
		this.matchUriOutPro = matchUriOutPro;
		this.uri = uri;
	}

	public String getSearchPattern() {
		return searchPattern;
	}

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMatchUriOutPro() {
		return matchUriOutPro;
	}

	public void setMatchUriOutPro(String matchUriOutPro) {
		this.matchUriOutPro = matchUriOutPro;
	}
	
	

}
