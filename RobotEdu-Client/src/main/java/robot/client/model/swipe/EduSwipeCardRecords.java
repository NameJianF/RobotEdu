package robot.client.model.swipe;

import robot.client.common.Config;
import robot.client.model.BaseModel;

import java.util.Map;

public class EduSwipeCardRecords extends BaseModel {

    public static final String tableName = "edu_swipe_card_records";


    private Long id;

    private Integer cardNo;

    private String upload;

    private Long createTime;

    public static String getUrl() {
        return Config.SERVER_URL + "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload == null ? null : upload.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    public static EduSwipeCardRecords getBean(Map<String, Object> item) {
        EduSwipeCardRecords bean = new EduSwipeCardRecords();
        bean.setId((Long) item.get("id"));
        bean.setCardNo((Integer) item.get("card_no"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime((Long) item.get("create_time"));

        return bean;
    }
}