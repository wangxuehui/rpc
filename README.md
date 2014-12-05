 a simple rpc framework for java
=================================================
集成 jdk,jackson,fasterxml,protostuff,kryo,protobuf,作为序列化工具,可以轻松转化序列化方式,使用 4.0.24 作为 NIO。
--------------------------------------------------
1. server class;
{% highlight java linenos %}
//define an interface:
public interface SnRpcInterface {
   public String getMessage(String param);
}
{% endhighlight %}
