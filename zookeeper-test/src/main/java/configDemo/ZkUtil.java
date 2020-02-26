package configDemo;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author sergei
 * @create 2020-02-25
 */
public class ZkUtil {

    private static ZooKeeper zk;
    private static String address = "127.0.0.1:2181,127.0.0.1:2282,127.0.0.1:2383/test1";
    private static DefaultWatch watch = new DefaultWatch();
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static ZooKeeper getZk() {
        try {
            watch.setCountDownLatch(countDownLatch);
            zk = new ZooKeeper(address,10000,watch);
            countDownLatch.await();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zk;
    }
}
