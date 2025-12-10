package ca.mss.rd.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import ca.mss.rd.util.io.UtilIO;

public class UtilHttp {

	final static public String className = UtilHttp.class.getName();
	final static public long serialVersionUID = className.hashCode();

	static public int HTTP_BUFFER_KB = 1024 * 22;
	
	static public boolean HTTP_PROXY = false;
	static public String HTTP_PROXY_HOST = ""; 
	static public int HTTP_PROXY_PORT = 0; 
	static public String HTTP_PROXY_SCHEME = "http";

	static public boolean HTTP_AUTH = false;
	static public String HTTP_AUTH_HOST = ""; 
	static public int HTTP_AUTH_PORT = 0; 

	static public boolean HTTP_NT_CREDENTIALS = false;
	static public String HTTP_NT_CREDENTIALS_USERNAME = "";
	static public String HTTP_NT_CREDENTIALS_PASSWORD = "";
	static public String HTTP_NT_CREDENTIALS_WORKSTATION = "";
	static public String HTTP_NT_CREDENTIALS_DOMAIN = "";
			
	static {
		UtilProperty.readConstants(UtilHttp.class);
	}
	
	final static public void writeGET(String targetURL, OutputStream os) {
		write(targetURL, null, RequestType.GET, os, UtilHttp.RequestInputType.TEXT);
	}

	final static public void write(String targetURL, RequestType requestType, OutputStream os) {
		write(targetURL, null, requestType, os, UtilHttp.RequestInputType.TEXT);
	}
	
	final static public void write(String targetURL, Map<String,String> parameters, RequestType requestType, OutputStream os, 
			RequestInputType inputType) {
		write(targetURL, parameters, null, requestType, os, inputType, 0);
	}

	final static public void write(String targetURL, Map<String,String> parameters,  Map<String,String> header, RequestType requestType, OutputStream os, 
			RequestInputType inputType) {
		write(targetURL, parameters, header, requestType, os, inputType, 0);
	}

	final static public void write(String targetURL, Map<String,String> parameters, RequestType requestType, OutputStream os) {
		write(targetURL, parameters, requestType, os, UtilHttp.RequestInputType.TEXT);
	}

	final static public void write(String targetURL, Map<String,String> parameters,  Map<String,String> header, RequestType requestType, OutputStream os) {
		write(targetURL, parameters, header, requestType, os, UtilHttp.RequestInputType.TEXT);
	}

	final static public void write(String targetURL, Map<String,String> parameters, RequestType requestType, OutputStream os, 
			RequestInputType inputType,
			int itemNumber) {
		write(targetURL, parameters, null, requestType, os, inputType, itemNumber);
	}

	final static public void write(String targetURL, Map<String,String> parameters, Map<String,String> header, RequestType requestType, OutputStream os, 
			RequestInputType inputType,
			int itemNumber) {
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
        	if( HTTP_AUTH && HTTP_NT_CREDENTIALS)
	            httpclient.getCredentialsProvider().setCredentials(
	                    new AuthScope(HTTP_AUTH_HOST, HTTP_AUTH_PORT),
	                    new NTCredentials(HTTP_NT_CREDENTIALS_USERNAME, HTTP_NT_CREDENTIALS_PASSWORD, HTTP_NT_CREDENTIALS_WORKSTATION, HTTP_NT_CREDENTIALS_DOMAIN) );

        	HTTPRequest targetHttpRequest = new UtilHttp.HTTPRequest(targetURL); 
            
            if( HTTP_PROXY )
            	httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, 
            			new HttpHost(HTTP_AUTH_HOST, HTTP_PROXY_PORT, HTTP_PROXY_SCHEME));

        	HttpHost targetHost = new HttpHost(targetHttpRequest.hostname, targetHttpRequest.port, targetHttpRequest.scheme);

            HttpResponse response;
            
            if( requestType == RequestType.GET ){
	            HttpGet http;
	            if( parameters != null ){
	                List<NameValuePair> qparams = new ArrayList<NameValuePair>();
	            	for( Iterator<Entry<String, String>>  iter=parameters.entrySet().iterator(); iter.hasNext(); ){
	            		Entry<String, String> entry = iter.next();
	                    qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	            	}
	                URI uri = URIUtils.createURI(targetHttpRequest.scheme, targetHttpRequest.hostname, targetHttpRequest.port,
	                		targetHttpRequest.uri, URLEncodedUtils.format(qparams, "UTF-8"), null);
	                http = new HttpGet(uri);
	            } else {
	                http = new HttpGet(targetHttpRequest.uri);
	            }
	            
	            if( header != null ){
	            	for( Iterator<Entry<String, String>>  iter=header.entrySet().iterator(); iter.hasNext(); ){
	            		Entry<String, String> entry = iter.next();
	            		http.setHeader(entry.getKey(), entry.getValue());
	            	}
	            }

	            response = httpclient.execute(targetHost, http);
            } else if( requestType == RequestType.POST ){
	            HttpPost http = new HttpPost(targetHttpRequest.uri);
	            
	            if( header != null ){
	            	for( Iterator<Entry<String, String>>  iter=header.entrySet().iterator(); iter.hasNext(); ){
	            		Entry<String, String> entry = iter.next();
	            		http.setHeader(entry.getKey(), entry.getValue());
	            	}
	            }

	            if( parameters != null ){
	                List<NameValuePair> qparams = new ArrayList<NameValuePair>();
	            	for( Iterator<Entry<String, String>>  iter=parameters.entrySet().iterator(); iter.hasNext(); ){
	            		Entry<String, String> entry = iter.next();
	                    qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	            	}
	                http.setEntity(new UrlEncodedFormEntity(qparams));
	            }
	        
	            response = httpclient.execute(targetHost, http);
            } else {
            	throw new RuntimeException("Non implemented request type ["+requestType+"]");
            }
            
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            
            switch( inputType ){
            case TEXT:
    			is = new BufferedInputStream(is);
    			break;
            case ZIP:
    			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
    			while( itemNumber-- > 0) 
    				zis.getNextEntry();
    			is = zis;
    			break;
            }
            
            UtilIO.write(is, os);

            is.close();

            EntityUtils.consume(entity);
            
        }catch(Throwable t){
        	throw new RuntimeException("Failed do http get from ["+targetURL+"]", t);
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
	}

	
	static public class HTTPRequest {
		
		final public String hostname;
		final public int port;
		final public String scheme;
		final public String uri;

		/**
		 * @param hostname
		 */
		public HTTPRequest(String surl) throws Throwable {
			URL url = new URL(surl);
			this.hostname = url.getHost();
			this.scheme = url.getProtocol();
			this.uri = url.getPath();
			this.port = url.getPort()>0? url.getPort(): ("https".equals(this.scheme)? 443: 80);
			url = null;
		}
		
		
	}
	
	static public enum RequestType {
		GET,
		POST;
	}
	
	static public enum RequestInputType {
		TEXT,
		ZIP;
	}
	
	static public Map<String,String> toMapRequest(String request){
		Map<String,String> map = new HashMap<String,String>();

		String[] arr = request.split("\\?");
		
		if( arr.length < 2 )
			arr = arr[0].split("&");
		else
			arr = arr[1].split("&");
		
		for(int i=0; i<arr.length; i++){
			String[] par = arr[i].split("=");
			if( par.length < 2 )
				map.put(par[0], "");
			else	
				map.put(par[0], par[1]);
		}
		
		return map;
	}
	
	
	static public String toFilenameRequest(String request, Map<String, String> parameters, String ext){
		String fileName = "";
		
		if( request != null && parameters != null){
			Map<String, String> removekeys = toMapRequest(request);
			for(Iterator<String> iter=removekeys.keySet().iterator(); iter.hasNext(); ){
				parameters.remove(iter.next());
			}
		} else if( parameters == null )
			parameters = toMapRequest(request);

		for(Iterator<String> iter=parameters.values().iterator(); iter.hasNext(); ){
			String val = iter.next();
			fileName += "="+UtilString.getIdentifier(val);
		}
		
		fileName = UtilString.getHttpFilename(request) + fileName;
		
		return fileName+"."+ext;
	}
	
	
	static public void main(String[] args){
		System.out.println(toFilenameRequest("http://wefwef/wefwef/wefwef.do?wewef=)(*#$)&epirgje=rghh", UtilMisc.toMap(
				"23r2",")(#H)#*(",
				"tjt","_(@*NJDF@^*"), "html"));
	}
	
}
