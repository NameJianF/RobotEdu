package robot.client.model.customer;

import robot.client.common.Config;
import robot.client.model.BaseModel;

import java.util.Map;

public class EduCustomerInfo extends BaseModel {
    public EduCustomerInfo() {
        this.setApikey(Config.MODULE_APP_KEY);
        this.setTimestamp(System.currentTimeMillis());
        if (Config.LoginUser != null) {
            this.setSid(Config.LoginUser.getSid());
        }
    }

    private Integer id;
    private String adviser;
    private Long cardNo;
    private String childName;
    private String childSex;
    private String birthday;
    private String childImage;
    private String momName;
    private String momMobile;
    private String momEmail;
    private String dadName;
    private String dadMobile;
    private String dadEmail;
    private String address;
    private String remarks;
    private String upload;
    private Long createTime;
    private Long updateTime;

    public static String getUrl() {
        return Config.SERVER_URL + "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdviser() {
        return adviser;
    }

    public void setAdviser(String adviser) {
        this.adviser = adviser == null ? null : adviser.trim();
    }

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    public String getDadName() {
        return dadName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName == null ? null : childName.trim();
    }

    public String getChildSex() {
        return childSex;
    }

    public void setChildSex(String childSex) {
        this.childSex = childSex == null ? null : childSex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getChildImage() {
        return childImage;
    }

    public void setChildImage(String childImage) {
        this.childImage = childImage == null ? null : childImage.trim();
    }

    public String getMomName() {
        return momName;
    }

    public void setMomName(String momName) {
        this.momName = momName == null ? null : momName.trim();
    }

    public String getMomMobile() {
        return momMobile;
    }

    public void setMomMobile(String momMobile) {
        this.momMobile = momMobile == null ? null : momMobile.trim();
    }

    public String getMomEmail() {
        return momEmail;
    }

    public void setMomEmail(String momEmail) {
        this.momEmail = momEmail == null ? null : momEmail.trim();
    }

    public void setDadName(String dadName) {
        this.dadName = dadName == null ? null : dadName.trim();
    }

    public String getDadMobile() {
        return dadMobile;
    }

    public void setDadMobile(String dadMobile) {
        this.dadMobile = dadMobile == null ? null : dadMobile.trim();
    }

    public String getDadEmail() {
        return dadEmail;
    }

    public void setDadEmail(String dadEmail) {
        this.dadEmail = dadEmail == null ? null : dadEmail.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    public static EduCustomerInfo getBean(Map<String, Object> item) {
        EduCustomerInfo bean = new EduCustomerInfo();
        bean.setId((Integer) item.get("id"));
        bean.setAdviser((String) item.get("adviser"));
        bean.setCardNo((Long) item.get("card_no"));
        bean.setChildName((String) item.get("child_name"));
        bean.setChildSex((String) item.get("child_sex"));
        bean.setBirthday((String) item.get("birthday"));
        bean.setChildName((String) item.get("child_image"));
        bean.setMomName((String) item.get("mom_name"));
        bean.setMomMobile((String) item.get("mom_mobile"));
        bean.setMomEmail((String) item.get("mom_email"));
        bean.setDadName((String) item.get("dad_name"));
        bean.setDadMobile((String) item.get("dad_mobile"));
        bean.setDadEmail((String) item.get("dad_email"));
        bean.setAddress((String) item.get("address"));
        bean.setRemarks((String) item.get("remarks"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime((Long) item.get("create_time"));
        bean.setUpdateTime((Long) item.get("update_time"));

        return bean;
    }
}