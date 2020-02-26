
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author sergei
 * @create 2020-02-25
 */
public class ZkDemo {

    public static void main(String[] args) throws Exception{
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                countDownLatch.countDown();
                System.out.println("连接上了...");
            }
        });
        countDownLatch.await();
        zk.create("/demo","data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


        zk.close();
//        zk.getData("/test2", new Watcher() {
//            public void process(WatchedEvent watchedEvent) {
//
//            }
//        }, ZoomEvent.ZOOM_FINISHED);
    }
}
