package live.itrip.admin.service.intefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Feng on 2017/5/19.
 */
public interface IEduTeacherCustomerService {

    void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectTeacherCustomerList(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectTeacherCustomerById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void deleteTeacherCustomerById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void editTeacherCustomerById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

}
