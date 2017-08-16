package live.itrip.admin.dao;

import live.itrip.admin.model.AdminProvince;

import java.util.List;

public interface AdminProvinceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminProvince record);

    int insertSelective(AdminProvince record);

    AdminProvince selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminProvince record);

    int updateByPrimaryKey(AdminProvince record);

    // 自定义查询

    List<AdminProvince> selectAll();
}