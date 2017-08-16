package live.itrip.admin.dao;

import live.itrip.admin.model.EduShopInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EduShopInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EduShopInfo record);

    int insertSelective(EduShopInfo record);

    EduShopInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduShopInfo record);

    int updateByPrimaryKey(EduShopInfo record);

    // ================= add ===============

    Integer countAll(@Param("province") String province, @Param("city") String city
            , @Param("name") String name);

    List<EduShopInfo> selectShopList(@Param("province") String province, @Param("city") String city
            , @Param("name") String name, @Param("start") Integer start, @Param("length") Integer length);

    List<EduShopInfo> selectAll();
}