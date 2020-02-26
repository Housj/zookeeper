package lock;

import configDemo.MyConf;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author sergei
 * @create 2020-02-25
 */
public class WatchCallBack implements Watcher,AsyncCallback.StringCallback,AsyncCallback.ChildrenCallback,AsyncCallback.StatCallback{

    ZooKeeper zk;
    String threadName;
    String pathName;
    public static CountDownLatch countDownLatch = new CountDownLatch(1);


    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()){

            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zk.getChildren("/",false,this,"ctx");
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

    public void tryLock(){

        try {
            zk.create("/lock",threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL,this,"aaa");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void processResult(int i, String s, Object o, String s1) {
        if (s1 != null){
            pathName = s1;
            System.out.println(pathName);
            zk.getChildren("/",false,this,"ctx");
        }
    }

    public void processResult(int i, String s, Object o, List<String> list) {
        Collections.sort(list);
        int j = list.indexOf(pathName.substring(1));
        if (j == 0){
            System.out.println(threadName+"i am first...");
            countDownLatch.countDown();
        }else {
            zk.exists("/"+ list.get(j-1),this,this,"ctx");
        }
    }


    public void processResult(int i, String s, Object o, Stat stat) {

    }

    public void unLock(){
        try {
            zk.delete("/lock",-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

}
