package robot.client.dao;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import robot.client.common.TableNames;
import robot.client.db.DbHelper;
import robot.client.model.customer.DataEduCustomerInfo;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.util.Logger;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by Feng on 2017/5/24.
 */
public class CustomerDao {

    /**
     * @param childName
     * @return
     */
    public static LinkedList<DataEduCustomerInfo> selectDatas(String childName) {
        LinkedList<DataEduCustomerInfo> customerInfos = new LinkedList<>();
        try {
            String sql = null;
            if (StringUtils.isEmpty(childName)) {
                sql = "select * from " + TableNames.EDU_CUSTOMER_INFO + " order by id desc; ";
            } else {
                sql = "select * from " + TableNames.EDU_CUSTOMER_INFO + " WHERE child_name = " + childName + " order by id desc; ";
            }

            List list = DbHelper.query(sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> item = (Map<String, Object>) iterator.next();
                    DataEduCustomerInfo info = DataEduCustomerInfo.getBean(item);
                    customerInfos.add(info);
                }
            }

        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return customerInfos;
    }

    public static Integer insert(EduCustomerInfo customerInfo) {
        if (StringUtils.isEmpty(customerInfo.getAdviser())) {
            Logger.error("adviser is null");
            return 0;
        }
        if (StringUtils.isEmpty(customerInfo.getChildName())) {
            Logger.error("child name is null");
            return 0;
        }

        if (StringUtils.isEmpty(customerInfo.getChildSex())) {
            Logger.error("child sex is null");
            return 0;
        }


        List<Object> params = new ArrayList<Object>();
        StringBuffer sqlBuffer = new StringBuffer("insert into " + TableNames.EDU_CUSTOMER_INFO);
        sqlBuffer.append(" (adviser,card_no,child_name,child_sex,birthday,");
        sqlBuffer.append(" child_image,mom_name,mom_mobile,mom_email,dad_name,");
        sqlBuffer.append(" dad_mobile,dad_email,address,remarks,create_time,");
        sqlBuffer.append(" update_time)");
        sqlBuffer.append(" values(?,?,?,?,?,");
        sqlBuffer.append(" ?,?,?,?,?,");
        sqlBuffer.append(" ?,?,?,?,?,");
        sqlBuffer.append(" ?)");

        params.add(customerInfo.getAdviser());
        params.add(customerInfo.getCardNo());
        params.add(customerInfo.getChildName());
        params.add(customerInfo.getChildSex());
        params.add(customerInfo.getBirthday());

        params.add(customerInfo.getChildImage());
        params.add(customerInfo.getMomName());
        params.add(customerInfo.getMomMobile());
        params.add(customerInfo.getMomEmail());
        params.add(customerInfo.getDadName());

        params.add(customerInfo.getDadMobile());
        params.add(customerInfo.getDadEmail());
        params.add(customerInfo.getAddress());
        params.add(customerInfo.getRemarks());
        params.add(customerInfo.getCreateTime());

        params.add(customerInfo.getUpdateTime());

        try {
            Long ret = DbHelper.insert(sqlBuffer.toString(), params.toArray());
            return ret.intValue();
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return -1;
    }

    public static EduCustomerInfo select(Long cardNo) {
        try {
            String sql = " select * from " + TableNames.EDU_CUSTOMER_INFO + " where card_no = " + cardNo;

            List list = DbHelper.query(sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                Map<String, Object> item = (Map<String, Object>) list.get(0);
                return EduCustomerInfo.getBean(item);
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return null;
    }
}
