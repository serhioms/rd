package ca.mss.rd.test.http;

import org.apache.log4j.Logger;


import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilHttp.RequestInputType;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.io.UtilIO;

public class TestAmex {

	final static public String module = TestAmex.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String FILE_PATH = "results/http/amex.html";
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		System.out.println("Start...");
		
		Timing timing = new Timing();

		UtilHttp.write( UtilProperty.getProperty("amex.url"), 
				UtilMisc.toMap(
						"request_type","LogLogonHandler",
						"DestPage","https://global.americanexpress.com/myca/intl/acctsumm/canlac/accountSummary.do?request_type=&Face=en_CA&inav=ca_utility_login&page=CM&omnlogin=ca-lilo-myca",
						"Face","en_CA",
						"PWD",UtilProperty.getProperty("amex.password"),
						"brandname","",
						"TARGET","https://global.americanexpress.com/myca/intl/acctsumm/canlac/accountSummary.do?request_type=&Face=en_CA&inav=ca_utility_login&page=CM&omnlogin=ca-lilo-myca",
						"USERID",UtilProperty.getProperty("amex.user"),
						"CHECKBOXSTATUS","1",
						"Logon","Logon",
						"ReqSource",null,
						"devicePrint","",
						"acctSelected","Cards - Your Account",
						"Cards - Your Account","https://global.americanexpress.com/myca/intl/acctsumm/canlac/accountSummary.do?request_type=&Face=en_CA&inav=ca_utility_login&page=CM&omnlogin=ca-lilo-myca",
						"act","soa",
						"errMsgValueInPage","false",
						"errMsgValue","",
						"isDestFound","destFound",
						"devicePrint","",
						"checkboxValueID","checked",
						"mskUIDField","ser*****",
						"UserID","serhioms",
						"Password","Hunter2013",
						"REMEMBERME","on"
				), 
				UtilMisc.toMap(
						"Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
						"Accept-Encoding","gzip,deflate,sdch",
						"Accept-Language","en-US,en;q=0.8",
						"Cache-Control","max-age=0",
						"Connection","keep-alive",
						//"Content-Length","960",
						"Content-Type","application/x-www-form-urlencoded",
						//"Cookie","homepage=ca; SaneID=207.219.69.233, 198.173.2.147-1375904448958596; gctrac=gctvid=2013-08-07/15:40:48:213-6398471f-6db0-21ae-4d61-cc9deb28f8c5&lno=0; BIGipServerme3-w-gl-cl-lgon-b=1951469578.25784.0000; BIGipServerme3-w-gl-cl-acctsum-b=2370899978.25272.0000; profile=muid#ser*****|rmf#1|kid#1|ed#A4D2FFDC861D8E7A245176CBB2603733F7D9279D0B527C40DD4AA76E8860567487FBD1DE81662CFCAC25DD6D3B10A6B4|; keymaster=PMV53EKCepHwiBxDhLIKuUKspbeIeuJDUH7jQSZduw0FxzyIEdCibRXSBkM7hkg8vOQn7tFKDpmcWNmb8CG6Z%2F%2B0JhGw%3D%3D; blueboxpublic=de00d58aca23f5b9a71d9f9634adbc60; MATFSI=BBV::4f6a51ed-d2b08b29-1139ebf2-4c61bb72~FSI::true~; cemcookie=emailIndicator|; JSESSIONID=0000p_yX1prCsNdUyAKaomczFML:17kgtdq1i; sroute=2707467668.47873.0000; s_sess=%20evar69%3Dca-lilo-myca%3B%20evar8%3Dca_utility_logout%3B%20s_cc%3Dtrue%3B%20s_dedupeCM%3Dundefinedundefinedundefined%3B%20s_sq%3D%3B; s_vi=[CS]v1|29015162051D115C-40000142C0004F4D[CE]; s_pers=%20s_visit%3D1%7C1375906249416%3B%20s_vnum%3D1%7C1533584449431%3B%20s_uvid%3D1375904449425404%7C1533584481461%3B%20s_invisit%3Dtrue%7C1375906281461%3B%20gpv_v41%3DCA%257CAMEX%257CSer%257CEnterpriseLogin_en%7C1375906445294%3B",
						"Cookie","SaneID=207.219.69.233, 198.173.2.147-1375904448958596; gctrac=gctvid=2013-08-07/15:40:48:213-6398471f-6db0-21ae-4d61-cc9deb28f8c5&lno=0; BIGipServerme3-w-gl-cl-lgon-b=1951469578.25784.0000; BIGipServerme3-w-gl-cl-acctsum-b=2370899978.25272.0000; cemcookie=emailIndicator|; profile=muid#ser*****|rmf#1|kid#1|ed#A4D2FFDC861D8E7A245176CBB2603733F7D9279D0B527C40DD4AA76E8860567487FBD1DE81662CFC8436E74A58997A20|; keymaster=PMV53E1nQRlg5hbj2mpZDW86GCUoeuJDUH7jQSZduw0FxzyIGpSzZqF%2BvLBI0Qb5S8SqKYQX%2Fqs6t9ffZRIIMHrIHP8A%3D%3D; blueboxpublic=de00d58aca23f5b9a71d9f9634adbc60; MATFSI=BBV::03eaacff-210b5b16-9d411743-871f2313~FSI::true~; JSESSIONID=0000Q-tEDefcmuV-m8c18oLtdLG:17kgtdq1i; homepage=ca; xaxis=1; sroute=1342903306.58660.0000; s_sess=%20evar69%3Dca-lilo-myca%3B%20evar8%3Dca_utility_logout%3B%20s_cc%3Dtrue%3B%20s_dedupeCM%3Dundefinedundefinedundefined%3B%20s_sq%3D%3B; s_vi=[CS]v1|29015162051D115C-40000142C0004F4D[CE]; s_pers=%20s_visit%3D1%7C1375971164067%3B%20s_vnum%3D2%7C1533649364082%3B%20s_uvid%3D1375904449425404%7C1533649370910%3B%20s_invisit%3Dtrue%7C1375971170912%3B%20gpv_v41%3DCA%257CAMEX%257CSer%257CEnterpriseLogin_en%7C1375971214084%3B",
						"Host","global.americanexpress.com",
						"Origin","https://global.americanexpress.com",
						"Referer","https://global.americanexpress.com/myca/logon/canlac/action?request_type=LogonHandler&DestPage=https%3A%2F%2Fglobal.americanexpress.com%2Fmyca%2Fintl%2Facctsumm%2Fcanlac%2FaccountSummary.do%3Frequest_type%3D%26Face%3Den_CA%26inav%3Dca_utility_login%26page%3DCM&Face=en_CA",
						"User-Agent","Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36"
				), 
				UtilHttp.RequestType.POST,
				System.err, //UtilIO.getOutputStreamFile(FILE_PATH),
				RequestInputType.TEXT);

		UtilHttp.write( UtilProperty.getProperty("amex.query.url"), 
				null, 
				UtilMisc.toMap(
						"Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
						"Accept-Encoding","gzip,deflate,sdch",
						"Accept-Language","en-US,en;q=0.8",
						"Cache-Control","max-age=0",
						"Connection","keep-alive",
						"Cookie","SaneID=207.219.69.233, 198.173.2.147-1375904448958596; gctrac=gctvid=2013-08-07/15:40:48:213-6398471f-6db0-21ae-4d61-cc9deb28f8c5&lno=0; BIGipServerme3-w-gl-cl-lgon-b=1951469578.25784.0000; BIGipServerme3-w-gl-cl-acctsum-b=2370899978.25272.0000; homepage=ca; xaxis=1; cemcookie=emailIndicator|; JSESSIONID=0000ma4tlvwe8brSpIvXcTqsUgB:17kgtdq1i; s_vi=[CS]v1|29015162051D115C-40000142C0004F4D[CE]; s_sess=%20evar69%3Dca-lilo-myca%3B%20s_cc%3Dtrue%3B%20s_dedupeCM%3Dundefinedundefinedundefined%3B%20s_sq%3D%3B%20evar8%3Dca_utility_logout%3B; s_pers=%20s_visit%3D1%7C1375971164067%3B%20s_vnum%3D2%7C1533649364082%3B%20s_uvid%3D1375904449425404%7C1533649370910%3B%20s_invisit%3Dtrue%7C1375971170912%3B%20gpv_v41%3DCA%257CAMEX%257CSer%257CEnterpriseLogout_en%7C1375973079878%3B; dateoflastlogon=2013-08-08T14:11:55Z; profile=muid#ser*****|rmf#1|kid#1|ed#A4D2FFDC861D8E7A245176CBB2603733CC27C929BD041117DD4AA76E886056746FAA38AF7F1564D628C1AB7DB53A9198|; keymaster=PMV53EtJ8hloH58rAS8yu4LuYBfs9Gf5A4t604Tmb9Z6KtH3DYoUQzGMSAoXz5DAZGWfKNTKEJYBEvFOu%2BbCH3seJW4Q%3D%3D; gatekeeper=98FF91089A15BE8E61A36394017359059CFE7A90BBD6FFCE97EF82307A96FDC1437A98A7561C3A144B9DC448F8E436DC58F90B3BFB9D8902275A04D936F11DBD04EC560DB795A40FB3D2F6F7CAF4CFC670044CF997CFD4722459BE9C1D5390956972F83772D9301DBCE1071EFB82C87C1DCA7618912C54208B23231377B46DC7F65A170F716B62EB0F59992CD11DBE672BCD07B1A35F5E47C5789B4EECE7827CE301036A629A5F17FE6C528DD81AA4A312A2CDFC72869B9EE693F281E7599990; blueboxvalues=c8843bb1-33b8f086-f4a46b7e-8ee0d528; blueboxpublic=de00d58aca23f5b9a71d9f9634adbc60; amexsessioncookie=easc=7BBDB68E22E60199F961215B109178C38458E3F64C6960A5FA6EF2941004FEF5F2048E64B7101A293DAA4E44B4BC5556DED01481275D429B24F5DC280A420CB43E11467D4029311A66A8B8479F5AE39701C002C3DE3189B1D08D986B662867CF8A8FCA9AF4F7C9DFC027ECE0A3C3C7569CEFF6CB364F00C9D9CD31ED5A8AA32F7823CB1F3BCC3D90081D205786A77CFAB24E09EA672393794ED964C57D7AEB1FFFFA9CF014BDE85D5643CCDAB3AF3BDE9C08C9B8C62A0E478C041CAD39F091734D172F29FEF30E9CDA1F3E915D957E9054DEEE3FBD0D5E62249795C40AC06E6026|BBV=c8843bb1-33b8f086-f4a46b7e-8ee0d528|uts=1375971284219|chv=2F25FA63E8DDA55EFFCF64FC16A73DA46029F44ED31B686F3FDB5433120937D1|; MATFSI=BBV::c8843bb1-33b8f086-f4a46b7e-8ee0d528~FSI::true~; sroute=2707467668.47873.0000",
						"Host","global.americanexpress.com",
						"Referer","https://global.americanexpress.com/myca/logon/canlac/action?request_type=LogLogoffConfirmHandler&Face=en_CA&BUnit=null&page=CM&inav=ca_utility_logout",
						"User-Agent","Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36"
				), 
				UtilHttp.RequestType.GET,
				UtilIO.getOutputStreamFile(FILE_PATH),
				RequestInputType.TEXT);
		
		logger.debug("Done [buffer="+Timing.formatBytes(UtilHttp.HTTP_BUFFER_KB)+"][time="+timing.total()+"]");
	}
	

}


