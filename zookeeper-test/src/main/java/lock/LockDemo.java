package lock;

import configDemo.ZkUtil;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sergei
 * @create 2020-02-25
 */
public class LockDemo {
    ZooKeeper zk;

    @Test
    public void lock(){
       for (int i=0;i<10;i++){
            new Thread(){
                @Override
                public void run() {
                    WatchCallBack watchCallBack = new WatchCallBack();
                    watchCallBack.setZk(zk);
                    watchCallBack.setThreadName(Thread.currentThread().getName());

                    watchCallBack.tryLock();
                    System.out.println("线程工作种。");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    watchCallBack.unLock();
                }
            }.start();
           try {
               Thread.sleep(500);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    @Before
    public void before(){
        zk = ZkUtil.getZk();
    }

    @After
    public void adter() throws InterruptedException {
        zk.close();
    }
}
