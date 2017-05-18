package robot.client.controller;

import robot.client.api.swipe.SwipeCardApi;
import robot.client.model.swipe.EduSwipeCardRecords;
import robot.client.observer.Subject;

/**
 * Created by Feng on 2017/5/18.
 */
public class SwipeController extends Subject {

    public SwipeController() {
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
