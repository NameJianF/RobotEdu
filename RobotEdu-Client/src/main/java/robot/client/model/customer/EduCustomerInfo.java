package robot.client.model.customer;

import robot.client.model.BaseModel;

public class EduCustomerInfo extends BaseModel {
    private Integer id;

    private String adviser;

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

    public String getDadName() {
        return dadName;
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
}