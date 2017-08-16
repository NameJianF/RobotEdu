package live.itrip.admin.dao;

import live.itrip.admin.model.AdminArea;

import java.util.List;

public interface AdminAreaMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(AdminArea record);

    int insertSelective(AdminArea record);

    AdminArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminArea record);

    int updateByPrimaryKey(AdminArea record);

    // 自定义查询

    List<AdminArea> selectByCityCode(String cityCode);
}