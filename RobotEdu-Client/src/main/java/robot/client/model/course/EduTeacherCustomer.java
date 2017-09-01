package robot.client.model.course;

import robot.client.common.Config;
import robot.client.model.BaseModel;

import java.util.Map;

public class EduTeacherCustomer extends BaseModel {
    private Integer id;
    private String staffNo;
    private String staffName;
    private Integer customerId;
    private String childName;
    private String upload;
    private Long createTime;
    private Long updateTime;

    public EduTeacherCustomer() {
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

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo == null ? null : staffNo.trim();
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName == null ? null : childName.trim();
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

    public static EduTeacherCustomer getBean(Map<String, Object> item) {
        EduTeacherCustomer bean = new EduTeacherCustomer();
        bean.setId((Integer) item.get("id"));
        bean.setStaffNo((String) item.get("staff_no"));
        bean.setStaffName((String) item.get("staff_name"));
        bean.setCustomerId((Integer) item.get("customer_id"));
        bean.setChildName((String) item.get("child_name"));
        bean.setUpload((String) item.get("upload"));
        bean.setCreateTime((Long) item.get("create_time"));
        bean.setUpdateTime((Long) item.get("update_time"));

        return bean;
    }
}