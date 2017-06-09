package robot.client.model.swipe;

import robot.client.common.Config;
import robot.client.model.BaseModel;

import java.util.Map;

public class EduSwipeCardRecords extends BaseModel {

    private Long id;
    private String cardNo;
    private String childName;
    private String upload;
    private Long createTime;

    public EduSwipeCardRecords() {
        this.setApikey(Config.MODULE_APP_KEY);
        this.setTimestamp(System.currentTimeMillis());
        if (Config.LoginUser != null) {
            this.setSid(Config.LoginUser.getSid());
        }
    }


    public static String getUrl() {
        return Config.SERVER_URL + "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
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
        bean.setCardNo((String) item.get("card_no"));
        bean.setChildName((String) item.get("child_name"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime((Long) item.get("create_time"));

        return bean;
    }
}