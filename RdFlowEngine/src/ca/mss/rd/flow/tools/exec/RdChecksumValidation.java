package ca.mss.rd.flow.tools.exec;

import ca.mss.rd.flow.prop.RdExecDefault;
import ca.mss.rd.flow.tools.prop.ChecksumAlgoritm;
import ca.mss.rd.flow.tools.prop.UriResource;



public class RdChecksumValidation extends RdExecDefault {

	private ChecksumAlgoritm algoritm;
	private UriResource markerResource, sourceResource;

	public ChecksumAlgoritm getAlgoritm() {
		return algoritm;
	}

	public void setAlgoritm(ChecksumAlgoritm algoritm) {
		this.algoritm = algoritm;
	}

	public UriResource getMarkerResource() {
		return markerResource;
	}

	public void setMarkerResource(UriResource markerResource) {
		this.markerResource = markerResource;
		getOutSet().add(markerResource.getMatchUriOutPro());
		getValSet().add(markerResource.getUri());
		getValSet().add(markerResource.getSearchPattern());
	}

	public UriResource getSourceResource() {
		return sourceResource;
	}

	public void setSourceResource(UriResource sourceResource) {
		this.sourceResource = sourceResource;
		getOutSet().add(sourceResource.getMatchUriOutPro());
		getValSet().add(sourceResource.getUri());
		getValSet().add(sourceResource.getSearchPattern());
	}

	@Override
	public String toString() {

		String src = sourceResource==null?"": String.format("[pattern=%s,uri=%s,out=%s]", sourceResource.getSearchPattern(), sourceResource.getUri(), sourceResource.getMatchUriOutPro());
		String mrk = markerResource==null?"": String.format("[pattern=%s,uri=%s,out=%s]", markerResource.getSearchPattern(), markerResource.getUri(), markerResource.getMatchUriOutPro());

		return String.format("ChecksumValidation[algoritm=%s]Source[%s]Marker[%s]", algoritm, src, mrk);
	}
}
