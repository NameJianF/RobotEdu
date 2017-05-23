package robot.client.model.card;

import javafx.beans.property.*;

import java.util.Map;

/**
 * Created by Feng on 2017/5/23.
 */
public class DataEduCardInfo {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final LongProperty cardNo = new SimpleLongProperty();
    private final StringProperty cardType = new SimpleStringProperty();
    private final IntegerProperty totalTimes = new SimpleIntegerProperty();
    private final IntegerProperty usedTimes = new SimpleIntegerProperty();
    private final IntegerProperty price = new SimpleIntegerProperty();
    private final IntegerProperty discount = new SimpleIntegerProperty();
    private final StringProperty adviser = new SimpleStringProperty();
    private final StringProperty flag = new SimpleStringProperty();
    private final StringProperty upload = new SimpleStringProperty();
    private final LongProperty createTime = new SimpleLongProperty();
    private final LongProperty updateTime = new SimpleLongProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public long getCardNo() {
        return cardNo.get();
    }

    public LongProperty cardNoProperty() {
        return cardNo;
    }

    public void setCardNo(long cardNo) {
        this.cardNo.set(cardNo);
    }

    public String getCardType() {
        return cardType.get();
    }

    public StringProperty cardTypeProperty() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType.set(cardType);
    }

    public int getTotalTimes() {
        return totalTimes.get();
    }

    public IntegerProperty totalTimesProperty() {
        return totalTimes;
    }

    public void setTotalTimes(int totalTimes) {
        this.totalTimes.set(totalTimes);
    }

    public int getUsedTimes() {
        return usedTimes.get();
    }

    public IntegerProperty usedTimesProperty() {
        return usedTimes;
    }

    public void setUsedTimes(int usedTimes) {
        this.usedTimes.set(usedTimes);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getDiscount() {
        return discount.get();
    }

    public IntegerProperty discountProperty() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount.set(discount);
    }

    public String getAdviser() {
        return adviser.get();
    }

    public StringProperty adviserProperty() {
        return adviser;
    }

    public void setAdviser(String adviser) {
        this.adviser.set(adviser);
    }

    public String getFlag() {
        return flag.get();
    }

    public StringProperty flagProperty() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag.set(flag);
    }

    public String getUpload() {
        return upload.get();
    }

    public StringProperty uploadProperty() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload.set(upload);
    }

    public long getCreateTime() {
        return createTime.get();
    }

    public LongProperty createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime.set(createTime);
    }

    public long getUpdateTime() {
        return updateTime.get();
    }

    public LongProperty updateTimeProperty() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime.set(updateTime);
    }

    public static DataEduCardInfo getBean(Map<String, Object> item) {
        DataEduCardInfo bean = new DataEduCardInfo();
        bean.setId((Integer) item.get("id"));
        bean.setCardNo((Long) item.get("card_no"));
        bean.setCardType((String) item.get("card_type"));
        bean.setTotalTimes((Integer) item.get("total_times"));
        bean.setUsedTimes((Integer) item.get("used_times"));
        bean.setPrice((Integer) item.get("price"));
        bean.setDiscount((Integer) item.get("discount"));
        bean.setAdviser((String) item.get("adviser"));
        bean.setFlag((String) item.get("flag"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime((Long) item.get("create_time"));
        bean.setUpdateTime((Long) item.get("update_time"));
        return bean;
    }
}
