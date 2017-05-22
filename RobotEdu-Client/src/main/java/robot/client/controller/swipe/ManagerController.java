package robot.client.controller.swipe;

import robot.client.api.swipe.SwipeCardApi;
import robot.client.model.swipe.EduSwipeCardRecords;
import robot.client.observer.Subject;

/**
 * Created by Feng on 2017/5/18.
 */
public class ManagerController extends Subject {

    public ManagerController() {
        this.attach(new SwipeCardApi());
    }

    /**
     * 保存数据到数据库
     */
    private void insert() {
        EduSwipeCardRecords swipeCardRecords = new EduSwipeCardRecords();
        this.nodifyObservers(swipeCardRecords);
    }
}
