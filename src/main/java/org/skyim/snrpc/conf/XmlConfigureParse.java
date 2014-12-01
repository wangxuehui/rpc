package org.skyim.snrpc.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月1日 上午10:06:51 类说明
 */
public class XmlConfigureParse {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SnRpcConfig.class);

	private String configFile = null;
	private Document document = null;
	private Element root = null;

	public XmlConfigureParse(String configFile) {
		super();
		this.configFile = configFile;
		this.root = getRoot();
	}

	@SuppressWarnings("unchecked")
	private Element getRoot() {
		// TODO Auto-generated method stub
		Document doc = getDocument();
		List<Element> list= doc.selectNodes("//application");
		if(list.size()>0){
			Element aroot =list.get(0);
			return aroot;
		}
		return null;
	}

	private Document getDocument() {
		// TODO Auto-generated method stub
		InputStream is = getFileStream();
		try {
			if (document == null) {
				SAXReader sr = new SAXReader();
				sr.setValidation(false);
				if (is == null) {
					throw new RuntimeException("can not find config File..."
							+ configFile);
				}
				document = sr.read(is);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("get xml file failed");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private InputStream getFileStream() {
		// TODO Auto-generated method stub
		return getFileStream(configFile);
	}

	private InputStream getFileStream(String fileName) {
		// TODO Auto-generated method stub
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(fileName);
		return is;
	}
}
