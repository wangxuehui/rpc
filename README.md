 a simple rpc framework for java
=================================================
集成 jdk,jackson,fasterxml,protostuff,kryo,protobuf,作为序列化工具,可以轻松转化序列化方式,使用 4.0.24 作为 NIO。
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
snrpc.http.host=localhost
#server port
snrpc.http.port=8080
#Serialization 0 for jdk,1 for jackson,2 for fasterxml,3 for protostuff,4 for kryo,5 for protobuf
snrpc.serializataion.type = 0
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
			SnRpcConnectionFactory factory = new SnNettyRpcConnectionFactory(
		                "localhost", 8080);
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
```

5.要求
+ jdk1.6 以上
+ maven 3 以上

