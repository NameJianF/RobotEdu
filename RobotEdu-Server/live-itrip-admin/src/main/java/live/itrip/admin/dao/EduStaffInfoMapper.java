package live.itrip.admin.dao;

import live.itrip.admin.model.EduStaffInfo;

public interface EduStaffInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EduStaffInfo record);

    int insertSelective(EduStaffInfo record);

    EduStaffInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduStaffInfo record);

    int updateByPrimaryKey(EduStaffInfo record);

    Integer updateClientIdAndShopNo(EduStaffInfo info);
}