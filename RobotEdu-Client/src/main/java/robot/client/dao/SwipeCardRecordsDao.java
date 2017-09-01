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


    public static EduSwipeCardRecords selectLastSwipe(String cardNo) {

        String sql = "select * from " + TableNames.EDU_SWIPE_CARD_RECORDS + " WHERE card_no = '" + cardNo + "' order by id desc limit 1; ";
        try {
            List list = DbHelper.query(sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                Map<String, Object> item = (Map<String, Object>) list.get(0);

                EduSwipeCardRecords info = EduSwipeCardRecords.getBean(item);
                return info;
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static LinkedList<DataEduSwipeCardRecords> selectDatas(String cardNo) {
        LinkedList<DataEduSwipeCardRecords> infos = new LinkedList<>();
        try {
            String sql = null;
            if (StringUtils.isEmpty(cardNo)) {
                sql = "select * from " + TableNames.EDU_SWIPE_CARD_RECORDS + " order by id desc; ";
            } else {
                sql = "select * from " + TableNames.EDU_SWIPE_CARD_RECORDS + " WHERE card_no = '" + cardNo + "' order by id desc; ";
            }

            List list = DbHelper.query(sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> item = (Map<String, Object>) iterator.next();
                    DataEduSwipeCardRecords info = DataEduSwipeCardRecords.getBean(item);
                    infos.add(info);
                }
            }

        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return infos;
    }

    /**
     * 需要事务处理
     *
     * @param swipeCardRecords
     * @return
     */
    public static Integer insert(EduSwipeCardRecords swipeCardRecords) {

        List<Object> params = new ArrayList<Object>();
        StringBuffer sqlBuffer = new StringBuffer("insert into " + TableNames.EDU_SWIPE_CARD_RECORDS);
        sqlBuffer.append(" (card_no,child_name,create_time)");
        sqlBuffer.append(" values(?,?,?)");

        params.add(swipeCardRecords.getCardNo());
        params.add(swipeCardRecords.getChildName());
        params.add(swipeCardRecords.getCreateTime());

        try {
            return DbHelper.insert(sqlBuffer.toString(), params.toArray());
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return -1;
    }
}
