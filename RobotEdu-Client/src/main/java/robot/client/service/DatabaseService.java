package robot.client.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import robot.client.api.card.CardApi;
import robot.client.api.customer.CustomerApi;
import robot.client.api.staff.StaffApi;
import robot.client.api.swipe.SwipeCardApi;
import robot.client.db.DbHelper;
import robot.client.model.card.EduCardInfo;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.model.staff.EduStaffInfo;
import robot.client.model.swipe.EduSwipeCardRecords;
import robot.client.util.Logger;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Feng on 2017/5/18.
 */
public class DatabaseService {
    private static DatabaseService instance;

    public static DatabaseService getInstance() {
        if (instance == null) {
            synchronized (DatabaseService.class) {
                instance = new DatabaseService();
            }
        }

        return instance;
    }

    private Long timeTicks = 1000L * 30;
    private Thread threadDbDatas;

    public void init() {
        threadDbDatas = new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
                //card
                selectUploadCardInfos();
                // customer
                selectUploadCustomerInfos();
                //swipe card
                selectUploadSwipeRecords();
                // staff info
                selectUploadStaffInfos();

//                    Thread.sleep(timeTicks);
//                } catch (InterruptedException e) {
//                    Logger.error(e.getMessage(), e);
//                }
            }
        }, "threadDbDatas");
        threadDbDatas.start();
    }

    private void selectUploadCardInfos() {
        try {
            String sql = "select * from edu_card_info WHERE upload = '0';";
            ResultSetHandler<List<EduCardInfo>> handler = new BeanListHandler<>(EduCardInfo.class);
            List<EduCardInfo> list = DbHelper.query(sql, handler);
            if (list != null && list.size() > 0) {
                CardApi api = new CardApi();
                Iterator iterator = list.iterator();
                if (iterator.hasNext()) {
                    api.postJsonString((EduCardInfo) iterator.next());
                }
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    private void selectUploadCustomerInfos() {
        try {
            String sql = "select * from edu_customer_info WHERE upload = '0';";
            ResultSetHandler<List<EduCustomerInfo>> handler = new BeanListHandler<>(EduCustomerInfo.class);
            List<EduCustomerInfo> list = DbHelper.query(sql, handler);
            if (list != null && list.size() > 0) {
                CustomerApi api = new CustomerApi();
                Iterator iterator = list.iterator();
                if (iterator.hasNext()) {
                    api.postJsonString((EduCustomerInfo) iterator.next());
                }
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    private void selectUploadSwipeRecords() {
        try {
            String sql = "select * from edu_swipe_card_records WHERE upload = '0';";
            ResultSetHandler<List<EduSwipeCardRecords>> handler = new BeanListHandler<>(EduSwipeCardRecords.class);
            List<EduSwipeCardRecords> list = DbHelper.query(sql, handler);
            if (list != null && list.size() > 0) {
                SwipeCardApi api = new SwipeCardApi();
                Iterator iterator = list.iterator();
                if (iterator.hasNext()) {
                    api.postJsonString((EduSwipeCardRecords) iterator.next());
                }
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    private void selectUploadStaffInfos() {
        try {
            String sql = "select * from edu_staff_info WHERE upload = '0';";
            ResultSetHandler<List<EduStaffInfo>> handler = new BeanListHandler<>(EduStaffInfo.class);
            List<EduStaffInfo> list = DbHelper.query(sql, handler);
            if (list != null && list.size() > 0) {
                StaffApi api = new StaffApi();
                Iterator iterator = list.iterator();
                if (iterator.hasNext()) {
                    api.postJsonString((EduStaffInfo) iterator.next());
                }
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
    }

}
