 a simple rpc framework for java
=================================================
集成 jdk,jackson,fasterxml,protostuff,kryo,protobuf,作为序列化工具,可以轻松转化序列化方式,使用 4.0.24 作为 NIO,使用zookeeper 3.4.6来做注册中心,容错性及可用性
--------------------------------------------------
1. server class;
```ruby
//define an interface:
public interface SnRpcInterface {
   public String getMessage(String param);
}
```
```ruby
// implement interface
public class SnRpcImpl implements SnRpcInterface {
	public String getMessage(String param) {
		return "hi,it is message from server...param+" + param;
	}
}
```

2.start server
```ruby
public class ServerDemo {
	public static void main(String[] args) {
		SnRpcInterface inter = new SnRpcImpl();
		SnRpcServer server = new SnNettyRpcServer(new Object[] { inter });		
		SnRpcConfig snRpcConfig = SnRpcConfig.getInstance();
		ServiceProvider provider = new ServiceProvider();
        provider.publish(snRpcConfig.getProperty("snrpc.http.host", "127.0.0.1"), Integer.parseInt(snRpcConfig.getProperty("snrpc.http.port","8080")));		
		try {
			server.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
```

3.配置文件
snrpcserver.properties
------------------------------------------
```ruby
#ISDEBUG
snrpc.dev=false
#server host
snrpc.http.host=127.0.0.1
#server port
snrpc.http.port=8081
#Serialization 0 for jdk,1 for jackson,2 for fasterxml,3 for protostuff,4 for kryo,5 for protobuf
snrpc.serializataion.type = 1
#zookeeper ip 192.168.1.182:2181,192.168.1.193:2181
snrpc.zookeeper.ip=192.168.1.192:2181
```

snrpcclient.properties
------------------------------------------
```ruby
#ISDEBUG
snrpc.dev=false
#Serialization 0 for jdk,1 for jackson,2 for fasterxml,3 for protostuff,4 for kryo,5 for protobuf
snrpc.serializataion.type = 1
#zookeeper ip 192.168.1.182:2181,192.168.1.193:2181
snrpc.zookeeper.ip=192.168.1.192:2181
```

log4j.properties
------------------------------------------
```ruby
log4j.rootLogger=DEBUG, myConsoleAppender
# settings for the console appender
log4j.appender.myConsoleAppender=org.apache.log4j.ConsoleAppender
log4j.appender.myConsoleAppender.layout=org.apache.log4j.PatternLayout
log4j.appender.myConsoleAppender.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:s} %-4r [%t] %-5p %c %x - %m%n
```

config.xml
------------------------------------------
```ruby
<?xml version="1.0" encoding="UTF-8"?>
<application>
 <!-- rpc interface services -->    
 <rpcServices>
        <rpcService name="SnRpcInterface" interface="test.client.SnRpcInterface" overload="true">
           <rpcImplementor  class="test.client.SnRpcImpl"/> 
        </rpcService>
    </rpcServices>
</application>
```

4.客户端调用
```ruby
public class ClientDemo {
		public static void main(String[] args) {
	        ServiceConsumer consumer = new ServiceConsumer();
	        String provider = consumer.lookup();
			String[] providers = provider.split(":");
			
			if(providers.length == 3) {
				System.out.println(providers);
				SnRpcConnectionFactory factory = new SnNettyRpcConnectionFactory(
						providers[1], Integer.parseInt(providers[2]));
				SnRpcClient client = new CommonSnRpcClient(factory);
			    try {
			        SnRpcInterface clazz = client.proxy(SnRpcInterface.class);
			        String message = clazz.getMessage("come on");
			        System.out.println("client receive message .... : " + message);
			    } catch (Throwable e) {
			        e.printStackTrace();
			    }
			}
		
		}
	
}


```



6.要求
+ jdk1.7 以上的
+ maven 3 以上
+ zookeeper 官方安装就行
注意：我们首先需要使用 ZooKeeper 的客户端工具创建一个持久性 ZNode，名为“/skyim”，该节点是不存放任何数据的，可使用如下命令：
create /skyim null


