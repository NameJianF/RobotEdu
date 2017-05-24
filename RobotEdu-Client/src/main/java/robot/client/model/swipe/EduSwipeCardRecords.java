package robot.client.model.swipe;

import robot.client.common.Config;
import robot.client.model.BaseModel;

import java.util.Map;

public class EduSwipeCardRecords extends BaseModel {

    private Long id;
    private Long cardNo;
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

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
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

    public EduSwipeCardRecords(){
        this.setApikey(Config.MODULE_APP_KEY);
        this.setTimestamp(System.currentTimeMillis());
        if (Config.LoginUser != null) {
            this.setSid(Config.LoginUser.getSid());
        }
    }


    public static EduSwipeCardRecords getBean(Map<String, Object> item) {
        EduSwipeCardRecords bean = new EduSwipeCardRecords();
        bean.setId((Long) item.get("id"));
        bean.setCardNo((Long) item.get("card_no"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime((Long) item.get("create_time"));

        return bean;
    }
}