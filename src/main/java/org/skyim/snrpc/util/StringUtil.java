package org.skyim.snrpc.util;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * 类说明
 */
public class StringUtil {
	public static boolean isEmpty(String str){
		return str == null || "".equals(str.trim()) ? true : false;
	}
	public static boolean isNotEmpty(String str){
		return str == null || "".equals(str.trim()) ? false : true;
	}
}
