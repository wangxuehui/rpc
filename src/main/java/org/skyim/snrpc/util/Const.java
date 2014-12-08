package org.skyim.snrpc.util;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * 类说明
 */
public interface Const {
	String SERIALIZATION_PROTOBUF = "5";
	String SERIALIZATION_KRYO     = "4";
	String SERIALIZATION_PROTOSTUFF="3";
	String SERIALIZATION_FASTERXML ="2";
	String SERIALIZATION_JACKSON   ="1";
	String SERIALIZATION_JDK       ="0";
	
    int ZK_SESSION_TIMEOUT = 5000;
    String ZK_REGISTRY_PATH = "/skyim";
    String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
}
