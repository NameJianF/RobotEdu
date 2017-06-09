package robot.client.model.card;

import robot.client.common.Config;
import robot.client.model.BaseModel;

import java.util.Map;

public class EduCardInfo extends BaseModel {
    private Integer id;
    private String cardNo;
    private String cardType;
    private Integer totalTimes;
    private Integer usedTimes;
    private Integer price;
    private Integer discount;
    private String adviser;
    private String flag;
    private String upload;
    private Long createTime;
    private Long updateTime;

    public EduCardInfo() {
        this.setApikey(Config.MODULE_APP_KEY);
        this.setTimestamp(System.currentTimeMillis());
        if (Config.LoginUser != null) {
            this.setSid(Config.LoginUser.getSid());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public Integer getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Integer totalTimes) {
        this.totalTimes = totalTimes;
    }

    public Integer getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(Integer usedTimes) {
        this.usedTimes = usedTimes;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getAdviser() {
        return adviser;
    }

    public void setAdviser(String adviser) {
        this.adviser = adviser == null ? null : adviser.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public static EduCardInfo getBean(Map<String, Object> item) {
        EduCardInfo bean = new EduCardInfo();
        bean.setId((Integer) item.get("id"));
        bean.setCardNo((String) item.get("card_no"));
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