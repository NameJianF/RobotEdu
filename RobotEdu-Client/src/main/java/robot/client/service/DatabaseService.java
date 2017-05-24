package robot.client.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import robot.client.api.card.CardApi;
import robot.client.api.customer.CustomerApi;
import robot.client.api.staff.StaffApi;
import robot.client.api.swipe.SwipeCardApi;
import robot.client.common.TableNames;
import robot.client.db.DbHelper;
import robot.client.model.card.EduCardInfo;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.model.staff.EduStaffInfo;
import robot.client.model.swipe.EduSwipeCardRecords;
import robot.client.util.Logger;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            String sql = "select * from " + TableNames.EDU_CARD_INFO + " WHERE upload = '0'; ";
//            ResultSetHandler<List<EduCardInfo>> handler = new BeanListHandler<>(EduCardInfo.class);
            List list = DbHelper.query(sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                CardApi api = new CardApi();
                Iterator iterator = list.iterator();
                if (iterator.hasNext()) {
                    Map<String, Object> item = (Map<String, Object>) iterator.next();
                    EduCardInfo info = EduCardInfo.getBean(item);
                    info.setOp("cardInfo.add");
                    api.postJsonString(info);
                }
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    private void selectUploadCustomerInfos() {
        try {
            String sql = "select * from " + TableNames.EDU_CUSTOMER_INFO + " WHERE upload = '0';";
            ResultSetHandler<List<EduCustomerInfo>> handler = new BeanListHandler<>(EduCustomerInfo.class);
            List<EduCustomerInfo> list = DbHelper.query(sql, handler);
            if (list != null && list.size() > 0) {
                CustomerApi api = new CustomerApi();
                Iterator iterator = list.iterator();
                if (iterator.hasNext()) {
                    Map<String, Object> item = (Map<String, Object>) iterator.next();
                    EduCustomerInfo info = EduCustomerInfo.getBean(item);
                    info.setOp("customerInfo.add");
                    api.postJsonString(info);
                }
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    private void selectUploadSwipeRecords() {
        try {
            String sql = "select * from " + EduSwipeCardRecords.tableName + " WHERE upload = '0';";
            ResultSetHandler<List<EduSwipeCardRecords>> handler = new BeanListHandler<>(EduSwipeCardRecords.class);
            List<EduSwipeCardRecords> list = DbHelper.query(sql, handler);
            if (list != null && list.size() > 0) {
                SwipeCardApi api = new SwipeCardApi();
                Iterator iterator = list.iterator();
                if (iterator.hasNext()) {
                    Map<String, Object> item = (Map<String, Object>) iterator.next();
                    EduSwipeCardRecords info = EduSwipeCardRecords.getBean(item);
                    info.setOp("swipeCardRecords.add");
                    api.postJsonString(info);
                }
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    private void selectUploadStaffInfos() {
        try {
            String sql = "select * from " + EduStaffInfo.tableName + " WHERE upload = '0';";
            ResultSetHandler<List<EduStaffInfo>> handler = new BeanListHandler<>(EduStaffInfo.class);
            List<EduStaffInfo> list = DbHelper.query(sql, handler);
            if (list != null && list.size() > 0) {
                StaffApi api = new StaffApi();
                Iterator iterator = list.iterator();
                if (iterator.hasNext()) {
                    Map<String, Object> item = (Map<String, Object>) iterator.next();
                    EduStaffInfo info = EduStaffInfo.getBean(item);
                    info.setOp("staffInfo.add");
                    api.postJsonString(info);
                }
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
    }

}
