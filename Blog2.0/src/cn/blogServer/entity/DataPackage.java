package cn.blogServer.entity;

import java.io.Serializable;

public class DataPackage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5676899452824349223L;
	private String request;
	private Object obj;
	
	public DataPackage(String request, Object obj) {
		this.request = request;
		this.obj = obj;
	}
	public String getRequest() {
		return request;
	}
	public Object getObj() {
		return obj;
	}
	
}
