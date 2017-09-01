package robot.client.dao;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import robot.client.api.card.CardApi;
import robot.client.common.TableNames;
import robot.client.db.DbHelper;
import robot.client.model.card.DataEduCardInfo;
import robot.client.model.card.EduCardInfo;
import robot.client.util.Logger;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by Feng on 2017/5/23.
 */
public class CardInfoDao {


    /**
     * 查询卡数据
     *
     * @param cardNo
     * @return
     */
    public static EduCardInfo selectCardData(String cardNo) {
        try {
            String sql = "select * from " + TableNames.EDU_CARD_INFO + " WHERE card_no = '" + cardNo + "'; ";

            List list = DbHelper.query(sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                Map<String, Object> item = (Map<String, Object>) list.get(0);
                return EduCardInfo.getBean(item);
            }

        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 查询数据数据
     *
     * @param cardNo
     * @return
     */
    public static LinkedList<DataEduCardInfo> selectDatas(String cardNo) {
        LinkedList<DataEduCardInfo> cardInfoList = new LinkedList<>();
        try {
            String sql = null;
            if (cardNo == null) {
                sql = "select * from " + TableNames.EDU_CARD_INFO + " order by id desc; ";
            } else {
                sql = "select * from " + TableNames.EDU_CARD_INFO + " WHERE card_no = '" + cardNo + "' order by id desc; ";
            }

            List list = DbHelper.query(sql, new MapListHandler());
            if (list != null && list.size() > 0) {
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Map<String, Object> item = (Map<String, Object>) iterator.next();
                    DataEduCardInfo info = DataEduCardInfo.getBean(item);
                    cardInfoList.add(info);
                }
            }

        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return cardInfoList;
    }

    public static Integer insert(EduCardInfo cardInfo) {
        if (StringUtils.isEmpty(cardInfo.getCardNo())) {
            Logger.error("card NO is null");
            return 0;
        }
        if (StringUtils.isEmpty(cardInfo.getCardType())) {
            Logger.error("card type is null");
            return 0;
        }
        if (StringUtils.isEmpty(cardInfo.getAdviser())) {
            Logger.error("adviser is null");
            return 0;
        }

        if (cardInfo.getPrice() == null) {
            Logger.error("card prive is null");
            return 0;
        }


        List<Object> params = new ArrayList<Object>();
        StringBuffer sqlBuffer = new StringBuffer("insert into " + TableNames.EDU_CARD_INFO);
        sqlBuffer.append(" (card_no,card_type,total_times,used_times,price,");
        sqlBuffer.append(" discount,adviser,flag,create_time,update_time)");
        sqlBuffer.append(" values(?,?,?,?,?,");
        sqlBuffer.append(" ?,?,?,?,?)");

        params.add(cardInfo.getCardNo());
        params.add(cardInfo.getCardType());
        params.add(cardInfo.getTotalTimes());
        params.add(cardInfo.getUsedTimes());
        params.add(cardInfo.getPrice());

        params.add(cardInfo.getDiscount());
        params.add(cardInfo.getAdviser());
        params.add(cardInfo.getFlag());
        params.add(cardInfo.getCreateTime());
        params.add(cardInfo.getUpdateTime());

        try {
            Integer ret = DbHelper.insert(sqlBuffer.toString(), params.toArray());
            return ret;
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return -1;
    }

    public static Integer update(EduCardInfo cardInfo) {
        if (StringUtils.isEmpty(cardInfo.getCardNo())) {
            Logger.error("card NO is null");
            return 0;
        }
        if (StringUtils.isEmpty(cardInfo.getCardType())) {
            Logger.error("card type is null");
            return 0;
        }
        if (StringUtils.isEmpty(cardInfo.getAdviser())) {
            Logger.error("adviser is null");
            return 0;
        }

        if (cardInfo.getPrice() == null) {
            Logger.error("card price is null");
            return 0;
        }
        List<Object> params = new ArrayList<Object>();
        StringBuffer sqlBuffer = new StringBuffer("update " + TableNames.EDU_CARD_INFO + " set ");
        sqlBuffer.append(" card_no = ?, ");
        params.add(cardInfo.getCardNo());
        sqlBuffer.append(" card_type = ?, ");
        params.add(cardInfo.getCardType());
        if (cardInfo.getTotalTimes() != null) {
            sqlBuffer.append(" total_times = ?, ");
            params.add(cardInfo.getTotalTimes());
        }
        if (cardInfo.getUsedTimes() != null) {
            sqlBuffer.append(" used_times = ?, ");
            params.add(cardInfo.getUsedTimes());
        }
        sqlBuffer.append(" price = ?, ");
        params.add(cardInfo.getPrice());

        if (cardInfo.getDiscount() != null) {
            sqlBuffer.append(" discount = ?, ");
            params.add(cardInfo.getDiscount());
        }
        sqlBuffer.append(" adviser = ?, ");
        params.add(cardInfo.getAdviser());
        if (cardInfo.getFlag() != null) {
            sqlBuffer.append(" flag = ?, ");
            params.add(cardInfo.getFlag());
        }
        sqlBuffer.append(" update_time = ? ");
        params.add(System.currentTimeMillis());

        sqlBuffer.append(" where id = ? ");
        params.add(cardInfo.getId());
        try {
            return DbHelper.update(sqlBuffer.toString(), params.toArray());
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return -1;
    }

    /**
     * 刷卡
     *
     * @param cardInfo
     * @return
     */
    public static Integer swipeCard(EduCardInfo cardInfo) {
        if (StringUtils.isEmpty(cardInfo.getCardNo())) {
            Logger.error("card NO is null");
            return 0;
        }

        List<Object> params = new ArrayList<Object>();
        StringBuffer sqlBuffer = new StringBuffer("update " + TableNames.EDU_CARD_INFO + " set ");
        if (cardInfo.getUsedTimes() != null) {
            sqlBuffer.append(" used_times = ? ");
            params.add(cardInfo.getUsedTimes());
        }
        sqlBuffer.append(" where card_no = ? ");
        params.add(cardInfo.getCardNo());
        try {
            return DbHelper.update(sqlBuffer.toString(), params.toArray());
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return -1;
    }

}
