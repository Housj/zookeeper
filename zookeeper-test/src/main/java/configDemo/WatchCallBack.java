package configDemo;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author sergei
 * @create 2020-02-25
 */
public class WatchCallBack implements Watcher, AsyncCallback.StatCallback,AsyncCallback.DataCallback {

    public ZooKeeper zk;
    public MyConf myConf;
    public static CountDownLatch countDownLatch = new CountDownLatch(1);

    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
        if(bytes != null){
            String str = new String(bytes);
            myConf.setConf(str);
            countDownLatch.countDown();
        }
    }

    public void processResult(int i, String s, Object o, Stat stat) {
        if (stat != null){
            zk.getData("/a",this,this,"aaa");
        }
    }

    public void aWait(){
        zk.exists("/a",this,this,"ABC");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()){

            case None:
                break;
            case NodeCreated:
                zk.getData("/a",true,this,"aaa");
                break;
            case NodeDeleted:
                myConf.setConf("");
                countDownLatch = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                zk.getData("/a",true,this,"aaa");
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public MyConf getMyConf() {
        return myConf;
    }

    public void setMyConf(MyConf myConf) {
        this.myConf = myConf;
    }
}
