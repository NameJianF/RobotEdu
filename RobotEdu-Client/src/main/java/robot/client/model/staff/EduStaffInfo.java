package robot.client.model.staff;

import robot.client.common.Config;
import robot.client.model.BaseModel;

import java.util.Map;

public class EduStaffInfo extends BaseModel {

    private Integer id;
    private String staffNo;
    private String staffType;
    private String staffName;
    private String mobile;
    private String sex;
    private String birthday;
    private String address;
    private String upload;
    private Long createTime;
    private Long updateTime;

    public EduStaffInfo(){
        this.setApikey(Config.MODULE_APP_KEY);
        this.setTimestamp(System.currentTimeMillis());
        if (Config.LoginUser != null) {
            this.setSid(Config.LoginUser.getSid());
        }
    }

    public static String getUrl() {
        return Config.SERVER_URL + "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo == null ? null : staffNo.trim();
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType == null ? null : staffType.trim();
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public static EduStaffInfo getBean(Map<String, Object> item) {
        EduStaffInfo bean = new EduStaffInfo();
        bean.setId((Integer) item.get("id"));
        bean.setStaffNo((String) item.get("staff_no"));
        bean.setStaffType((String) item.get("staff_type"));
        bean.setStaffName((String) item.get("staff_name"));
        bean.setMobile((String) item.get("mobile"));
        bean.setSex((String) item.get("sex"));
        bean.setBirthday((String) item.get("birthday"));
        bean.setAddress((String) item.get("address"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime((Long) item.get("create_time"));
        bean.setUpdateTime((Long) item.get("update_time"));

        return bean;
    }
}