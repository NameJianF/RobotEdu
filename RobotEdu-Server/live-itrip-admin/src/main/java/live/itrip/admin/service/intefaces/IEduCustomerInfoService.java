package live.itrip.admin.service.intefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Feng on 2017/5/19.
 */
public interface IEduCustomerInfoService {

    void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void modify(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectCustomerList(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectCustomerById(String decodeJson, HttpServletResponse response, HttpServletRequest request);
}
