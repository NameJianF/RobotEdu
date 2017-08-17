package live.itrip.admin.dao;

import live.itrip.admin.model.EduSwipeCardRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EduSwipeCardRecordsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EduSwipeCardRecords record);

    int insertSelective(EduSwipeCardRecords record);

    EduSwipeCardRecords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EduSwipeCardRecords record);

    int updateByPrimaryKey(EduSwipeCardRecords record);

    // 自定义查询

    Integer countAll(@Param("shopNo") String shopNo, @Param("cardNo") String cardNo);

    List<EduSwipeCardRecords> selectSwipeCardList(@Param("shopNo") String shopNo, @Param("cardNo") String cardNo
            , @Param("start") Integer start, @Param("length") Integer length);
}