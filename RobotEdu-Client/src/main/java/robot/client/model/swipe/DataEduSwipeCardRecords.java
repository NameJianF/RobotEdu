package robot.client.model.swipe;

import javafx.beans.property.*;
import robot.client.util.DateTimeUtil;

import java.util.Map;

/**
 * Created by Feng on 2017/5/24.
 */
public class DataEduSwipeCardRecords {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty cardNo = new SimpleStringProperty();
    private final StringProperty childName = new SimpleStringProperty();
    private final StringProperty upload = new SimpleStringProperty();
    private final StringProperty createTime = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCardNo() {
        return cardNo.get();
    }

    public StringProperty cardNoProperty() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo.set(cardNo);
    }

    public String getChildName() {
        return childName.get();
    }

    public StringProperty childNameProperty() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName.set(childName);
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

    public String getCreateTime() {
        return createTime.get();
    }

    public StringProperty createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime.set(createTime);
    }

    public static DataEduSwipeCardRecords getBean(Map<String, Object> item) {
        DataEduSwipeCardRecords bean = new DataEduSwipeCardRecords();
        bean.setId((Integer) item.get("id"));
        bean.setCardNo((String) item.get("card_no"));
        bean.setChildName((String) item.get("child_name"));
        bean.setUpload((String) item.get("upload"));
        Long createtime = (Long) item.get("create_time");
        bean.setCreateTime(DateTimeUtil.getStrByLong(createtime));

        return bean;
    }
}
