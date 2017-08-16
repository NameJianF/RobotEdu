package live.itrip.admin.dao;

import live.itrip.admin.model.EduCardInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EduCardInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EduCardInfo record);

    int insertSelective(EduCardInfo record);

    EduCardInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduCardInfo record);

    int updateByPrimaryKey(EduCardInfo record);

    Integer updateClientIdAndShopNo(EduCardInfo info);

    // 自定义查询

    Integer countAll(@Param("shopNo") String shopNo, @Param("cardNo") String cardNo);

    List<EduCardInfo> selectCardList(@Param("shopNo") String shopNo, @Param("cardNo") String cardNo
            , @Param("start") Integer start, @Param("length") Integer length);
}