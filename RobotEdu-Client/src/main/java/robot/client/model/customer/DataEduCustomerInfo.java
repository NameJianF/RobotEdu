package robot.client.model.customer;

import javafx.beans.property.*;

import java.util.Map;

/**
 * Created by Feng on 2017/5/24.
 */
public class DataEduCustomerInfo {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty adviser = new SimpleStringProperty();
    private LongProperty cardNo = new SimpleLongProperty();
    private StringProperty childName = new SimpleStringProperty();
    private StringProperty childSex = new SimpleStringProperty();
    private StringProperty birthday = new SimpleStringProperty();
    private StringProperty childImage = new SimpleStringProperty();
    private StringProperty momName = new SimpleStringProperty();
    private StringProperty momMobile = new SimpleStringProperty();
    private StringProperty momEmail = new SimpleStringProperty();
    private StringProperty dadName = new SimpleStringProperty();
    private StringProperty dadMobile = new SimpleStringProperty();
    private StringProperty dadEmail = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty remarks = new SimpleStringProperty();
    private StringProperty upload = new SimpleStringProperty();
    private LongProperty createTime = new SimpleLongProperty();
    private LongProperty updateTime = new SimpleLongProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public long getCardNo() {
        return cardNo.get();
    }

    public LongProperty cardNoProperty() {
        return cardNo;
    }

    public void setCardNo(long cardNo) {
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

    public String getChildSex() {
        return childSex.get();
    }

    public StringProperty childSexProperty() {
        return childSex;
    }

    public void setChildSex(String childSex) {
        this.childSex.set(childSex);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public StringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getChildImage() {
        return childImage.get();
    }

    public StringProperty childImageProperty() {
        return childImage;
    }

    public void setChildImage(String childImage) {
        this.childImage.set(childImage);
    }

    public String getMomName() {
        return momName.get();
    }

    public StringProperty momNameProperty() {
        return momName;
    }

    public void setMomName(String momName) {
        this.momName.set(momName);
    }

    public String getMomMobile() {
        return momMobile.get();
    }

    public StringProperty momMobileProperty() {
        return momMobile;
    }

    public void setMomMobile(String momMobile) {
        this.momMobile.set(momMobile);
    }

    public String getMomEmail() {
        return momEmail.get();
    }

    public StringProperty momEmailProperty() {
        return momEmail;
    }

    public void setMomEmail(String momEmail) {
        this.momEmail.set(momEmail);
    }

    public String getDadName() {
        return dadName.get();
    }

    public StringProperty dadNameProperty() {
        return dadName;
    }

    public void setDadName(String dadName) {
        this.dadName.set(dadName);
    }

    public String getDadMobile() {
        return dadMobile.get();
    }

    public StringProperty dadMobileProperty() {
        return dadMobile;
    }

    public void setDadMobile(String dadMobile) {
        this.dadMobile.set(dadMobile);
    }

    public String getDadEmail() {
        return dadEmail.get();
    }

    public StringProperty dadEmailProperty() {
        return dadEmail;
    }

    public void setDadEmail(String dadEmail) {
        this.dadEmail.set(dadEmail);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
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

    public static DataEduCustomerInfo getBean(Map<String, Object> item) {
        DataEduCustomerInfo bean = new DataEduCustomerInfo();
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
