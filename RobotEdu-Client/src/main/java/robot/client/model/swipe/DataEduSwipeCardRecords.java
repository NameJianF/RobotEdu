package robot.client.model.swipe;

import javafx.beans.property.*;

import java.util.Map;

/**
 * Created by Feng on 2017/5/24.
 */
public class DataEduSwipeCardRecords {
    private final LongProperty id = new SimpleLongProperty();
    private final LongProperty cardNo = new SimpleLongProperty();
    private final StringProperty upload = new SimpleStringProperty();
    private final LongProperty createTime = new SimpleLongProperty();

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
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

    public static DataEduSwipeCardRecords getBean(Map<String, Object> item) {
        DataEduSwipeCardRecords bean = new DataEduSwipeCardRecords();
        bean.setId((Long) item.get("id"));
        bean.setCardNo((Integer) item.get("card_no"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime((Long) item.get("create_time"));

        return bean;
    }
}
