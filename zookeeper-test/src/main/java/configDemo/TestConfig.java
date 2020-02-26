package configDemo;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sergei
 * @create 2020-02-25
 */
public class TestConfig {

    ZooKeeper zk;

    @Test
    public void getConfig(){
        WatchCallBack watchCallBack = new WatchCallBack();
        watchCallBack.setZk(zk);
        MyConf myConf = new MyConf();
        watchCallBack.setMyConf(myConf);

        watchCallBack.aWait();

        while (true){

            if (myConf.getConf().equals("")){
                System.out.println("config无数据...");
                watchCallBack.aWait();
            }else {
                System.out.println(myConf.getConf());
            }

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
