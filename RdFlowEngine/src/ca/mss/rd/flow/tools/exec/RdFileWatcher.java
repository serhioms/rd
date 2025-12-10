package ca.mss.rd.flow.tools.exec;

import java.util.ArrayList;
import java.util.List;

import ca.mss.rd.flow.prop.RdExecDefault;
import ca.mss.rd.flow.tools.prop.UriResource;


public class RdFileWatcher extends RdExecDefault {

	private String count;
	private Boolean allowFileNotFound;
	private List<UriResource> resource = new ArrayList<UriResource>(0);

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
		getOutSet().add(count);
	}

	public Boolean isAllowFileNotFound() {
		return allowFileNotFound;
	}

	public void setAllowFileNotFound(Boolean allowFileNotFound) {
		this.allowFileNotFound = allowFileNotFound;
	}

	public void addResource(UriResource resource) {
		this.resource.add(resource);
		getOutSet().add(resource.getMatchUriOutPro());
		getValSet().add(resource.getUri());
		getValSet().add(resource.getSearchPattern());
	}

	@Override
	public String toString() {

		String wtch = "";
		for(UriResource param: resource){
			wtch += String.format("[pattern=%s,uri=%s,out=%s]", param.getSearchPattern(), param.getUri(), param.getMatchUriOutPro());
		}

		return String.format("FileWatcher[count=%s][isAllowFileNotFound=%b]Watched[%s]", count, allowFileNotFound, wtch);
	}
}
