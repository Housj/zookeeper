package configDemo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;


/**
 * @author sergei
 * @create 2020-02-25
 */
public class ConfigDemo {

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("", 3000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
