package robot.client.dao;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import robot.client.common.TableNames;
import robot.client.db.DbHelper;
import robot.client.model.swipe.DataEduSwipeCardRecords;
import robot.client.model.swipe.EduSwipeCardRecords;
import robot.client.util.Logger;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by 建锋 on 2017/5/24.
 */
public class SwipeCardRecordsDao {


    public static LinkedList<DataEduSwipeCardRecords> selectDatas(String cardNo) {
        LinkedList<DataEduSwipeCardRecords> customerInfos = new LinkedList<>();
        try {
            String sql = null;
            if (StringUtils.isEmpty(cardNo)) {
                sql = "select * from " + TableNames.EDU_SWIPE_CARD_RECORDS + " order by id desc; ";
            } else {
                sql = "select * from " + TableNames.EDU_SWIPE_CARD_RECORDS + " WHERE card_no = " + cardNo + " order by id desc; ";
            }

            List list = DbHelper.query(sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> item = (Map<String, Object>) iterator.next();
                    DataEduSwipeCardRecords info = DataEduSwipeCardRecords.getBean(item);
                    customerInfos.add(info);
                }
            }

        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return customerInfos;
    }

    public static Integer insert(EduSwipeCardRecords swipeCardRecords) {

        List<Object> params = new ArrayList<Object>();
        StringBuffer sqlBuffer = new StringBuffer("insert into " + TableNames.EDU_SWIPE_CARD_RECORDS);
        sqlBuffer.append(" (card_no,create_time)");
        sqlBuffer.append(" values(?,?)");

        params.add(swipeCardRecords.getCardNo());
        params.add(swipeCardRecords.getCreateTime());

        try {
            Long ret = DbHelper.insert(sqlBuffer.toString(), params.toArray());
            return ret.intValue();
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return -1;
    }
}
