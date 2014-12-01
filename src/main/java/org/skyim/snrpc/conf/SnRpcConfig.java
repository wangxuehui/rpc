package org.skyim.snrpc.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.skyim.snrpc.exception.SnRpcException;
import org.skyim.snrpc.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年11月29日 下午3:52:55
 * 类说明
 */
public class SnRpcConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(SnRpcConfig.class);
	
	private static final SnRpcConfig snRpcConfig = new SnRpcConfig();
	
	private Properties properties = new  Properties();
	
	private SnRpcConfig(){}
	
	
	public static SnRpcConfig getInstance(){
		return snRpcConfig;
	}

	public void loadProperties(String fileName) {
		if (StringUtil.isEmpty(fileName))
			throw new SnRpcException("snRpcConfig name is null...");
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName);
			properties.load(inputStream);
		} catch (IOException e) {
			throw new SnRpcException(" snRpcConfig file load failed... "
					+ fileName);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		if (properties == null)
			throw new RuntimeException("Properties file loading failed: "
					+ fileName);
	}

	public Properties getProperties() {
		return properties;
	}

	public String getProperty(String key) {
		return properties.getProperty(key).trim();
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue.trim());
	}
	/**
	 * get the server's HTTP port,default is -1
	 * 
	 * @return
	 */
	public int getHttpPort(){
		String port =properties.getProperty("snrpc.http.port","-1");
		return Integer.parseInt(port);
	}
	
	public String getPropertiesFile(){
		String f = properties.getProperty("properties.file","config.xml");
		return f.trim();
	}

}
