package org.skyim.snrpc.server;

import java.util.List;

import org.skyim.snrpc.conf.ConfigureParse;
import org.skyim.snrpc.conf.RpcService;
import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.conf.XmlConfigureParse;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * 类说明
 */
public class ParseXmlToService {

		public void parse(){
			String configFile = SnRpcConfig.getInstance().getPropertiesFile();
			ConfigureParse parse =new XmlConfigureParse(configFile);
			List<RpcService> serviceList = parse.parseService();
			for(RpcService service:serviceList){
				SnNettyRpcServerHandler.putService(service);
			}
		}
}
