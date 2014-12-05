package org.skyim.snrpc.serializer;

import java.io.Serializable;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月2日 下午1:56:11
 * 类说明
 */
public class SnRpcResponse  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5640687689268052762L;
	private String requestID;
	private Throwable exception;
	private Object result;
	public SnRpcResponse(){
		
	}
	public SnRpcResponse(String requestID) {
		// TODO Auto-generated constructor stub
		this.requestID = requestID;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
