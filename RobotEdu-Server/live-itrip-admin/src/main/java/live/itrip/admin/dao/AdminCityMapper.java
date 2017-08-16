package live.itrip.admin.dao;

import live.itrip.admin.model.AdminCity;

import java.util.List;

public interface AdminCityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminCity record);

    int insertSelective(AdminCity record);

    AdminCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminCity record);

    int updateByPrimaryKey(AdminCity record);

    // 自定义查询
    List<AdminCity> selectByProvinceCode(String provinceCode);
}