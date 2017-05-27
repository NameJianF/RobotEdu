package live.itrip.admin.dao;

import live.itrip.admin.model.EduCustomerInfo;

public interface EduCustomerInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EduCustomerInfo record);

    int insertSelective(EduCustomerInfo record);

    EduCustomerInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduCustomerInfo record);

    int updateByPrimaryKey(EduCustomerInfo record);

    Integer updateClientIdAndShopNo(EduCustomerInfo info);

}