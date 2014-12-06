package org.skyim.snrpc.zookeeper.provider;
 
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
public class ServiceProvider {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProvider.class);
    private SnRpcConfig snRpcConfig = SnRpcConfig.getInstance();
    // 用于等待 SyncConnected事件触发后继续执行当前线程
    private CountDownLatch latch = new CountDownLatch(1);
 
    // 发布服务并注册 host,port 地址到 ZooKeeper中
    public void publish(String host, int port) {
        String url = publishService(host, port);
        if (url != null) {
            ZooKeeper zk = connectServer(); 
            if (zk != null) {
                createNode(zk, url); 
            }
        }
    }
 
    // 发布服务
    private String publishService(String host, int port) {
        String url = null;
        url = String.format("skyim:%s:%d", host, port);
        LOGGER.debug("publish service (url: {})", url);
        return url;
    }
 
    // 连接 ZooKeeper服务器
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(snRpcConfig.getProperty("snrpc.zookeeper.ip"), Const.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown(); // 唤醒当前正在执行的线程
                    }
                }
            });
            latch.await(); // 使当前线程处于等待状态
        } catch (IOException | InterruptedException e) {
            LOGGER.error("", e);
        }
        return zk;
    }
 
    // 创建 ZNode
    private void createNode(ZooKeeper zk, String url) {
        try {
            byte[] data = url.getBytes();
            String path = zk.create(Const.ZK_PROVIDER_PATH, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL); // 创建一个临时性且有序的 ZNode
            LOGGER.debug("create zookeeper node ({} => {})", path, url);
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("", e);
        }
    }
}