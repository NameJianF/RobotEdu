package robot.client.observer;

import robot.client.common.DataOp;

/**
 * Created by Feng on 2017/5/18.
 */
public interface Observer {
    /**
     * 实现观察处理
     */
    void upload(Object object, DataOp dataOp);
}
