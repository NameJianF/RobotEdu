package live.itrip.admin.service.intefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Feng on 2017/5/19.
 */
public interface IEduSwipeCardRecordsService {

    void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectSwipeCardList(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectSwipeCardById(String decodeJson, HttpServletResponse response, HttpServletRequest request);
}
