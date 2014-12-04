package org.skyim.snrpc.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.skyim.snrpc.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月1日 上午10:06:51 类说明
 */
public class XmlConfigureParse implements ConfigureParse{
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
		return document;
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

	@SuppressWarnings({ "unchecked", "unused" })
	public List<RpcService> parseService() {
		// TODO Auto-generated method stub
		List<RpcService> slist =new ArrayList<RpcService>();
		Node serviceRoot =root.selectSingleNode("//rpcServices");
		List<Element> serviceList =serviceRoot.selectNodes("//rpcService");
		
		int i=0;
		for(Element serviceNode:serviceList){
			String name        = serviceNode.attributeValue("name"); //service name;
			String interfaceStr= serviceNode.attributeValue("interface");
			String overloadStr = serviceNode.attributeValue("overload");
			if(StringUtil.isEmpty(name)) {
				LOGGER.warn(configFile + ":a rpcservice's name is empty");
				continue;
			}
			if(StringUtil.isEmpty(interfaceStr)){
				LOGGER.warn(configFile+":rpcservice["+name+"] has an empty interface configure" );
				continue;
			}
			Class<?> type = null;
			try {
				type = Class.forName(interfaceStr);
			} catch(ClassNotFoundException e){
				LOGGER.error(e.getMessage());
				throw new RuntimeException("can't find rpc Interface:"+interfaceStr);
			}
			RpcService service = new RpcService(""+i,name);
			if(StringUtil.isNotEmpty(overloadStr)&&"true".equals(overloadStr.trim())){
				service.setOverload(true);
			}
			Element rpcImplementor = serviceNode.element("rpcImplementor");
			String processor = rpcImplementor.attributeValue("class");
			Class<?> providerClass = null;
			try {
				providerClass = Class.forName(processor);
			}catch(ClassNotFoundException e){
				LOGGER.error(e.getMessage());
				throw new RuntimeException(" can't find rpcImplementor class:"+processor);
			}
			RpcImplementor sv = new RpcImplementor(providerClass);
			service.setRpcImplementor(sv);
			slist.add(service);
			i++;	
		}
		return slist;
	}
}
