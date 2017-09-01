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
            return DbHelper.insert(sqlBuffer.toString(), params.toArray());
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return -1;
    }

    public static EduCustomerInfo select(String cardNo) {
        try {
            String sql = " select * from " + TableNames.EDU_CUSTOMER_INFO + " where card_no = '" + cardNo + "' ";

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

    public static Integer update(EduCustomerInfo customerInfo) {
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
        StringBuffer sqlBuffer = new StringBuffer("update " + TableNames.EDU_CUSTOMER_INFO + " set ");

        sqlBuffer.append(" adviser = ?, ");
        params.add(customerInfo.getAdviser());
        if (StringUtils.isNotEmpty(customerInfo.getCardNo())) {
            sqlBuffer.append(" card_no = ?, ");
            params.add(customerInfo.getCardNo());
        }
        sqlBuffer.append(" child_name = ?, ");
        params.add(customerInfo.getChildName());
        sqlBuffer.append(" child_sex = ?, ");
        params.add(customerInfo.getChildSex());
        if (StringUtils.isNotEmpty(customerInfo.getBirthday())) {
            sqlBuffer.append(" birthday = ?, ");
            params.add(customerInfo.getBirthday());
        }

        if (StringUtils.isNotEmpty(customerInfo.getChildImage())) {
            sqlBuffer.append(" child_image = ?, ");
            params.add(customerInfo.getChildImage());
        }
        if (StringUtils.isNotEmpty(customerInfo.getMomName())) {
            sqlBuffer.append(" mom_name = ?, ");
            params.add(customerInfo.getMomName());
        }
        if (StringUtils.isNotEmpty(customerInfo.getMomMobile())) {
            sqlBuffer.append(" mom_mobile = ?, ");
            params.add(customerInfo.getMomMobile());
        }
        if (StringUtils.isNotEmpty(customerInfo.getMomEmail())) {
            sqlBuffer.append(" mom_email = ?, ");
            params.add(customerInfo.getMomEmail());
        }
        if (StringUtils.isNotEmpty(customerInfo.getDadName())) {
            sqlBuffer.append(" dad_name = ?, ");
            params.add(customerInfo.getDadName());
        }

        if (StringUtils.isNotEmpty(customerInfo.getDadMobile())) {
            sqlBuffer.append(" dad_mobile = ?, ");
            params.add(customerInfo.getDadMobile());
        }
        if (StringUtils.isNotEmpty(customerInfo.getDadEmail())) {
            sqlBuffer.append(" dad_email = ?, ");
            params.add(customerInfo.getDadEmail());
        }
        if (StringUtils.isNotEmpty(customerInfo.getAddress())) {
            sqlBuffer.append(" address = ?, ");
            params.add(customerInfo.getAddress());
        }
        if (StringUtils.isNotEmpty(customerInfo.getRemarks())) {
            sqlBuffer.append(" remarks = ?, ");
            params.add(customerInfo.getRemarks());
        }

        sqlBuffer.append(" upload = ?, ");
        params.add("0");

        sqlBuffer.append(" update_time = ? ");
        params.add(System.currentTimeMillis());

        sqlBuffer.append(" where id = ? ");
        params.add(customerInfo.getId());
        try {
            return DbHelper.update(sqlBuffer.toString(), params.toArray());
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return -1;
    }
}
