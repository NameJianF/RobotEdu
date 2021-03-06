package live.itrip.admin.service.intefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Feng on 2017/5/19.
 */
public interface IEduCardInfoService {
    
    void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void modify(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectCardList(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectCardById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void deleteCardById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void editCardById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectCardByCardNo(String decodeJson, HttpServletResponse response, HttpServletRequest request);
}
