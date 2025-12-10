package ca.mss.rd.flow.tools.exec;

import java.util.ArrayList;
import java.util.List;

import ca.mss.rd.flow.prop.RdExecDefault;
import ca.mss.rd.flow.tools.prop.UriResource;


public class RdTransferFile extends RdExecDefault {

	private String count;
	private List<UriResource> source = new ArrayList<UriResource>(0);
	private List<UriResource> target = new ArrayList<UriResource>(0);

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
		getOutSet().add(count);
	}

	public void addSource(UriResource source) {
		this.source.add(source);
		getOutSet().add(source.getMatchUriOutPro());
		getValSet().add(source.getUri());
		getValSet().add(source.getSearchPattern());	}

	public void addTarget(UriResource target) {
		this.target.add(target);
		getOutSet().add(target.getMatchUriOutPro());
		getValSet().add(target.getUri());
		getValSet().add(target.getSearchPattern());	}

	@Override
	public String toString() {

		String src = "";
		for(UriResource param: source){
			src += String.format("[pattern=%s,uri=%s,out=%s]", param.getSearchPattern(), param.getUri(), param.getMatchUriOutPro());
		}

		String trg = "";
		for(UriResource param: target){
			trg += String.format("[pattern=%s,uri=%s,out=%s]", param.getSearchPattern(), param.getUri(), param.getMatchUriOutPro());
		}

		return String.format("DeleteUri[count=%s]Source[%s]Target[%s]", count, src, trg);
	}
}
