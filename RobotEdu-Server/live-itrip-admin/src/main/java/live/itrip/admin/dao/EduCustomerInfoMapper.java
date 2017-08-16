package live.itrip.admin.dao;

import live.itrip.admin.model.EduCustomerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EduCustomerInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EduCustomerInfo record);

    int insertSelective(EduCustomerInfo record);

    EduCustomerInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduCustomerInfo record);

    int updateByPrimaryKey(EduCustomerInfo record);

    Integer updateClientIdAndShopNo(EduCustomerInfo info);

    // 自定义查询

    Integer countAll(@Param("shopNo") String shopNo, @Param("customerName") String customerName);

    List<EduCustomerInfo> selectCustomerList(@Param("shopNo") String shopNo, @Param("customerName") String customerName
            , @Param("start") Integer start, @Param("length") Integer length);
}