package live.itrip.admin.dao;

import live.itrip.admin.model.EduCardInfo;

public interface EduCardInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EduCardInfo record);

    int insertSelective(EduCardInfo record);

    EduCardInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduCardInfo record);

    int updateByPrimaryKey(EduCardInfo record);
}