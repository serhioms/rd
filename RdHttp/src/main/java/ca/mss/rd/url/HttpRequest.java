package ca.mss.rd.url;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class HttpRequest {

	final static public String module = HttpRequest.class.getName();
	final static public long serialVersionUID = module.hashCode();
	
	public String protocol; 
	public String host; 
	public int port; 
	public String path; 
	public String www; 
	public Map<String,String> query = new HashMap<String,String>();
	
	public String proxyProtocol; 
	public String proxyHost; 
	public int proxyPort; 
	public String proxy; 
	
	public HttpRequestType requestType = HttpRequestType.POST; 
	
	public HttpRequest(String www) {
		
		this.www = canonized(www);
		
		try {
			URL url = new URL(this.www);
			path = url.getPath();
			host = url.getHost();
			protocol = url.getProtocol();
			port = url.getPort()>0? url.getPort(): ("https".equals(protocol)? 443: 80);
			addQuery(url.getQuery());
		}catch(Throwable t){
			new RuntimeException("Can not parse url ["+www+"]", t);
		}
	}

	public HttpRequest(String www, Map<String,String> param) {
		this(www);
	}

	public HttpRequest(String www, Map<String,String> param, HttpRequestType requestType) {
		this(www, param);
		this.requestType = requestType;
	}

	public HttpRequest(String www, String proxy) {
		this(www);
		this.proxy = canonized(proxy);
		try {
			URL url = new URL(this.proxy);
			proxyHost = url.getHost();
			proxyProtocol = url.getProtocol();
			proxyPort = url.getPort()>0? url.getPort(): ("https".equals(protocol)? 443: 80);
		}catch(Throwable t){
			new RuntimeException("Can not parse proxy URL ["+www+"]", t);
		}
	}

	public HttpRequest(String www, String proxy, Map<String,String> query) {
		this(www, proxy);
		addQuery(query);
	}

	public HttpRequest(String www, String proxy, Map<String,String> query, HttpRequestType requestType) {
		this(www, proxy, query);
		this.requestType = requestType;
	}

	public HttpRequest(String www, String proxy, HttpRequestType requestType) {
		this(www, proxy);
		this.requestType = requestType;
	}

	final public String getWWW(){
		if( query.size() > 0 )
			return www+"?"+query.toString().replaceAll(", ","&").replaceAll("[{}]","");
		else
			return www;
	}
	
	final public void addQuery(String str){
		if( str != null ){
			String[] arr = str.replaceFirst("?","").split("&");
			if( arr != null ){
				for(int i=0; i<arr.length; i++){
					String[] param = arr[i].split("=");
					if( param.length == 2 ){
						query.put(param[0], param[1]);
					}
				}
			}
		}
	}
	
	final public void addQuery( Map<String,String> param){
		if( param != null ){
			query.putAll(param);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProxyURL [protocol=" + protocol + ", host=" + host + ", port=" + port + ", path=" + path + ", query=" + query
				+ ", proxyProtocol=" + proxyProtocol + ", proxyHost=" + proxyHost + ", proxyPort=" + proxyPort + "]";
	}


	public enum HttpRequestType {
		GET, POST;
	}
	
	final static public String canonized(String www){
		if( www.split("^.*://.*").length == 1)
			return "http://"+www;
		else 
			return www;
	}
}


