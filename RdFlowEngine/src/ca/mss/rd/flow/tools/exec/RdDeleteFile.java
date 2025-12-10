package ca.mss.rd.flow.tools.exec;

import java.util.ArrayList;
import java.util.List;

import ca.mss.rd.flow.prop.RdExecDefault;
import ca.mss.rd.flow.tools.prop.UriResource;


public class RdDeleteFile extends RdExecDefault {

	private String count;
	private List<UriResource> resource = new ArrayList<UriResource>(0);

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		getOutSet().add(count);
		this.count = count;
	}

	public void addResource(UriResource resource) {
		this.resource.add(resource);
		getOutSet().add(resource.getMatchUriOutPro());
		getValSet().add(resource.getUri());
		getValSet().add(resource.getSearchPattern());
	}

	@Override
	public String toString() {

		String res = "";
		for(UriResource param: resource){
			res += String.format("[pattern=%s,uri=%s,out=%s]", param.getSearchPattern(), param.getUri(), param.getMatchUriOutPro());
		}

		return String.format("DeleteUri[count=%s]Resource[%s]", count, res);
	}
}
