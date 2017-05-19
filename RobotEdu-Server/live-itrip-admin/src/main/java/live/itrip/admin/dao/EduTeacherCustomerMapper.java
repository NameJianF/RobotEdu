package live.itrip.admin.dao;

import live.itrip.admin.model.EduTeacherCustomer;

public interface EduTeacherCustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EduTeacherCustomer record);

    int insertSelective(EduTeacherCustomer record);

    EduTeacherCustomer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EduTeacherCustomer record);

    int updateByPrimaryKey(EduTeacherCustomer record);
}