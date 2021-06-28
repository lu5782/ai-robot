package com.cyp.robot.zk;

import org.apache.zookeeper.*;

/**
 * @Author :      luyijun
 * @Date :        2021/5/11 21:06
 * @Description :
 */
public class Zk {
    private static String ipAddress = "192.168.1.34:2181";
    private static int sessionTimeout = 999999;

    public static void main(String[] args) throws Exception {

        Watcher watcher = new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("监听到的事件：" + event);
            }
        };

        final ZooKeeper zookeeper = new ZooKeeper(ipAddress, sessionTimeout, watcher);
        System.out.println("获得连接：" + zookeeper);

//        String cr = zookeeper.create("/zk1", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println("创建节点：" + cr);

        final byte[] data = zookeeper.getData("/zk1", watcher, null);
        System.out.println("读取的值：" + new String(data));
        zookeeper.close();
    }


}
