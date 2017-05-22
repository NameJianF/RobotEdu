package robot.client.controller.customer;

import robot.client.api.customer.CustomerApi;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.observer.Subject;

/**
 * Created by Feng on 2017/5/18.
 */
public class ManagerContorller extends Subject {
    public ManagerContorller() {
        this.attach(new CustomerApi());
    }

    /**
     * 保存数据到数据库
     */
    private void insert() {
        EduCustomerInfo customerInfo = new EduCustomerInfo();
        this.nodifyObservers(customerInfo);
    }
}
