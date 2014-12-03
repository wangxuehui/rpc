package test.client;

// implement interface
public class SnRpcImpl implements SnRpcInterface {
	public String getMessage(String param) {
		return "hi,it is message from server...param+" + param;
	}
}