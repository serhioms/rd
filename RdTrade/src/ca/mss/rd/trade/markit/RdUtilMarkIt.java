package ca.mss.rd.trade.markit;

import java.util.Map;

import ca.mss.rd.trade.markit.RdMetadataMarkIt.Family;
import ca.mss.rd.trade.markit.RdMetadataMarkIt.Format;
import ca.mss.rd.trade.markit.RdMetadataMarkIt.Report;
import ca.mss.rd.trade.markit.RdMetadataMarkIt.Type;
import ca.mss.rd.trade.markit.RdMetadataMarkIt.Version;
import ca.mss.rd.trade.markit.RdMetadataMarkIt.ZipItem;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.io.UtilIO;

public class RdUtilMarkIt {

	final static public String module = RdUtilMarkIt.class.getName();
	final static public long serialVersionUID = module.hashCode();

	
	static public String LTraxComOp(String date, String folderPath){
		return writeToFile( UtilMisc.toMap(
				RdMetadataMarkIt.MARKIT_REQUEST_DATE, 		date,
				RdMetadataMarkIt.MARKIT_REQUEST_REPORT, 	Report.Composites.val,
				RdMetadataMarkIt.MARKIT_REQUEST_USERNAME, 	UtilProperty.getProperty("markit.username1"), 
				RdMetadataMarkIt.MARKIT_REQUEST_PASSWORD, 	UtilProperty.getProperty("markit.password1"),
				RdMetadataMarkIt.MARKIT_REQUEST_TYPE, 		Type.CredIndex.val,
				RdMetadataMarkIt.MARKIT_REQUEST_FAMILY, 	Family.ITRAXX_EUROPE.val,
				RdMetadataMarkIt.MARKIT_REQUEST_VERSION, 	Version.four.val,
				RdMetadataMarkIt.MARKIT_REQUEST_FORMAT, 	Format.CSV.val
			),
			folderPath,
			ZipItem.two.val);
	}
	

	static public String ABXIndexComposite(String date, String folderPath){
		return writeToFile( UtilMisc.toMap(
				RdMetadataMarkIt.MARKIT_REQUEST_URL, 		"https://sf.markit.com/export.jsp",
				RdMetadataMarkIt.MARKIT_REQUEST_DATE, 		date,
				RdMetadataMarkIt.MARKIT_REQUEST_REPORT, 	Report.XComposites.val,
				RdMetadataMarkIt.MARKIT_REQUEST_USERNAME, 	UtilProperty.getProperty("markit.username1"), 
				RdMetadataMarkIt.MARKIT_REQUEST_PASSWORD, 	UtilProperty.getProperty("markit.password1"),
				RdMetadataMarkIt.MARKIT_REQUEST_TYPE, 		Type.AbsCredIdx.val,
				RdMetadataMarkIt.MARKIT_REQUEST_FAMILY, 	Family.ABX_HE.val,
				RdMetadataMarkIt.MARKIT_REQUEST_VERSION, 	Version.five.val,
				RdMetadataMarkIt.MARKIT_REQUEST_FORMAT, 	Format.CSV.val
			),
			folderPath,
			ZipItem.two.val);
	}
	

	static public String CDSLiquidityMetrics(String date, String folderPath){
		return writeToFile( UtilMisc.toMap(
				RdMetadataMarkIt.MARKIT_REQUEST_DATE, 		date,
				RdMetadataMarkIt.MARKIT_REQUEST_REPORT, 	Report.LiquidityMetrics.val,
				RdMetadataMarkIt.MARKIT_REQUEST_USERNAME, 	UtilProperty.getProperty("markit.username1"), 
				RdMetadataMarkIt.MARKIT_REQUEST_PASSWORD, 	UtilProperty.getProperty("markit.password1"),
				RdMetadataMarkIt.MARKIT_REQUEST_TYPE, 		Type.CDS.val,
				RdMetadataMarkIt.MARKIT_REQUEST_FAMILY, 	Family.CDX.val,
				RdMetadataMarkIt.MARKIT_REQUEST_VERSION, 	Version.six.val,
				RdMetadataMarkIt.MARKIT_REQUEST_FORMAT, 	Format.XML.val
			),
			folderPath,
			ZipItem.two.val);
	}
	

	static public String AllContributions(String date, String folderPath){
		return writeToFile( UtilMisc.toMap(
				RdMetadataMarkIt.MARKIT_REQUEST_DATE, 		date,
				RdMetadataMarkIt.MARKIT_REQUEST_REPORT, 	Report.AllContributions.val,
				RdMetadataMarkIt.MARKIT_REQUEST_USERNAME, 	UtilProperty.getProperty("markit.username2"), 
				RdMetadataMarkIt.MARKIT_REQUEST_PASSWORD, 	UtilProperty.getProperty("markit.password2"),
				RdMetadataMarkIt.MARKIT_REQUEST_TYPE, 		Type.CDS.val,
				RdMetadataMarkIt.MARKIT_REQUEST_VERSION, 	Version.two.val,
				RdMetadataMarkIt.MARKIT_REQUEST_FORMAT, 	Format.XML.val
			),
			folderPath);
	}
	


	
	/*
	 * Implementation
	 * 
	 */
	
	static private String getFilePath(Map<String,String> request, String folder){
		
		String filePath = folder + "markit", val;
		
		if( (val=request.get(RdMetadataMarkIt.MARKIT_REQUEST_DATE)) != null )
			filePath += "="+val;
		if( (val=request.get(RdMetadataMarkIt.MARKIT_REQUEST_FAMILY)) != null )
			filePath += "="+val;
		if( (val=request.get(RdMetadataMarkIt.MARKIT_REQUEST_TYPE)) != null )
			filePath += "="+val;
		if( (val=request.get(RdMetadataMarkIt.MARKIT_REQUEST_REPORT)) != null )
			filePath += "="+val;
		if( (val=request.get(RdMetadataMarkIt.MARKIT_REQUEST_VERSION)) != null )
			filePath += "="+val;
		if( (val=request.get(RdMetadataMarkIt.MARKIT_REQUEST_FORMAT)) != null )
			filePath += "."+val;

		return filePath;
	}
	
	static private String writeToFile(Map<String,String> request, String folder, int zipItemNum){
		String markitUrl = request.containsKey(RdMetadataMarkIt.MARKIT_REQUEST_URL)? request.get(RdMetadataMarkIt.MARKIT_REQUEST_URL): RdMetadataMarkIt.MARKIT_REQUEST_URL;
		UtilHttp.write(markitUrl
				,request
				,UtilHttp.RequestType.POST
				,UtilIO.getOutputStreamFile(folder = getFilePath(request, folder))
				,UtilHttp.RequestInputType.ZIP
				,zipItemNum);
		
		return folder;
	}
	
	static private String writeToFile(Map<String,String> request, String folder){
		String markitUrl = request.containsKey(RdMetadataMarkIt.MARKIT_REQUEST_URL)? request.get(RdMetadataMarkIt.MARKIT_REQUEST_URL): RdMetadataMarkIt.MARKIT_REQUEST_URL;
		UtilHttp.write(markitUrl
				,request
				,UtilHttp.RequestType.POST
				,UtilIO.getOutputStreamFile(folder = getFilePath(request, folder))
				,UtilHttp.RequestInputType.TEXT);
		
		return folder;
	}
	
}
