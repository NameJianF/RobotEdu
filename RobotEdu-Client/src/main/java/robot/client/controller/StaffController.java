package robot.client.controller;

import robot.client.api.staff.StaffApi;
import robot.client.model.staff.EduStaffInfo;
import robot.client.observer.Subject;

/**
 * Created by Feng on 2017/5/18.
 */
public class StaffController extends Subject {

    public StaffController() {
        this.attach(new StaffApi());
    }

    /**
     * 保存数据到数据库
     */
    private void insert() {
        EduStaffInfo info = new EduStaffInfo();
        this.nodifyObservers(info);
    }
}
