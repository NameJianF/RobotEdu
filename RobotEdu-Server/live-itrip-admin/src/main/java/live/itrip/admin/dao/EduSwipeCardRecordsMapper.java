package live.itrip.admin.dao;

import live.itrip.admin.model.EduSwipeCardRecords;

public interface EduSwipeCardRecordsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EduSwipeCardRecords record);

    int insertSelective(EduSwipeCardRecords record);

    EduSwipeCardRecords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EduSwipeCardRecords record);

    int updateByPrimaryKey(EduSwipeCardRecords record);
}