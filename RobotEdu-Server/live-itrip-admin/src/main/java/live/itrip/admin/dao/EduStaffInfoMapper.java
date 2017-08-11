package live.itrip.admin.dao;

import live.itrip.admin.model.EduStaffInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EduStaffInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EduStaffInfo record);

    int insertSelective(EduStaffInfo record);

    EduStaffInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduStaffInfo record);

    int updateByPrimaryKey(EduStaffInfo record);

    Integer updateClientIdAndShopNo(EduStaffInfo info);

    // 自定义查询

    Integer countAll(@Param("shopNo") String shopNo, @Param("staffName") String staffName);

    List<EduStaffInfo> selectStaffList(@Param("shopNo") String shopNo, @Param("staffName") String staffName
            , @Param("start") Integer start, @Param("length") Integer length);


}