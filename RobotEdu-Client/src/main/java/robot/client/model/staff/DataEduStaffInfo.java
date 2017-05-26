package robot.client.model.staff;

import javafx.beans.property.*;
import robot.client.util.DateTimeUtil;

import java.util.Map;

/**
 * Created by Feng on 2017/5/24.
 */
public class DataEduStaffInfo {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty staffNo = new SimpleStringProperty();
    private StringProperty staffType = new SimpleStringProperty();
    private StringProperty staffName = new SimpleStringProperty();
    private StringProperty mobile = new SimpleStringProperty();
    private StringProperty sex = new SimpleStringProperty();
    private StringProperty birthday = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty upload = new SimpleStringProperty();
    private final StringProperty createTime = new SimpleStringProperty();
    private final StringProperty updateTime = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getStaffNo() {
        return staffNo.get();
    }

    public StringProperty staffNoProperty() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo.set(staffNo);
    }

    public String getStaffType() {
        return staffType.get();
    }

    public StringProperty staffTypeProperty() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType.set(staffType);
    }

    public String getStaffName() {
        return staffName.get();
    }

    public StringProperty staffNameProperty() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName.set(staffName);
    }

    public String getMobile() {
        return mobile.get();
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
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

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
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

    public String getUpdateTime() {
        return updateTime.get();
    }

    public StringProperty updateTimeProperty() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime.set(updateTime);
    }

    public static DataEduStaffInfo getBean(Map<String, Object> item) {
        DataEduStaffInfo bean = new DataEduStaffInfo();
        bean.setId((Integer) item.get("id"));
        bean.setStaffNo((String) item.get("staff_no"));
        bean.setStaffType((String) item.get("staff_type"));
        bean.setStaffName((String) item.get("staff_name"));
        bean.setMobile((String) item.get("mobile"));
        bean.setSex((String) item.get("sex"));
        bean.setBirthday((String) item.get("birthday"));
        bean.setAddress((String) item.get("address"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime(DateTimeUtil.getStrByLong((Long) item.get("create_time")));
        bean.setUpdateTime(DateTimeUtil.getStrByLong((Long) item.get("update_time")));

        return bean;
    }
}
