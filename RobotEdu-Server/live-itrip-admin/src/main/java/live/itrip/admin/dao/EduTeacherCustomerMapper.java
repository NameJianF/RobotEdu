package live.itrip.admin.dao;

import live.itrip.admin.model.EduTeacherCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EduTeacherCustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EduTeacherCustomer record);

    int insertSelective(EduTeacherCustomer record);

    EduTeacherCustomer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EduTeacherCustomer record);

    int updateByPrimaryKey(EduTeacherCustomer record);

    // 自定义查询

//    Integer countAll(@Param("shopNo") String shopNo, @Param("teacherName") String teacherName);
//
//    List<EduTeacherCustomer> selectTeacherCustomerList(@Param("shopNo") String shopNo, @Param("teacherName") String teacherName
//            , @Param("start") Integer start, @Param("length") Integer length);


}