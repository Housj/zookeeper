import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author sergei
 * @create 2020-02-25
 */
public class Demo {
    final static CountDownLatch cd = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2282,127.0.0.1:2383", 3000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                Event.KeeperState state = watchedEvent.getState();
                Event.EventType type = watchedEvent.getType();
                String path = watchedEvent.getPath();
                System.out.println(watchedEvent.toString());
                switch (state){

                    case Unknown:
                        break;
                    case Disconnected:
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected:
                        cd.countDown();
                        System.out.println("connected...");
                        break;
                    case AuthFailed:
                        break;
                    case ConnectedReadOnly:
                        break;
                    case SaslAuthenticated:
                        break;
                    case Expired:
                        break;
                }

                switch (type){

                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
                        break;
                    case NodeDataChanged:
                        break;
                    case NodeChildrenChanged:
                        break;
                    case DataWatchRemoved:
                        break;
                    case ChildWatchRemoved:
                        break;
                }
            }
        });

        cd.await();
        ZooKeeper.States states = zk.getState();
        switch (states){

            case CONNECTING:
                System.out.println("ing。。。");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("ed。。。");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }

        //zk.create("/test1","aaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.setData("/test1","aab".getBytes(),-1);
        zk.getData("/test1", new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watcher="+watchedEvent.getState());
            }
        }, new AsyncCallback.DataCallback() {
            public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
                System.out.println("test1=" + new String(bytes));
            }
        }, "aaa");
        //zk.close();
    }
}
