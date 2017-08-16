package live.itrip.admin.service.intefaces;

import live.itrip.admin.model.EduShopInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Feng on 2017/8/10.
 */
public interface IEduShopInfoService {

    void selectShopList(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void selectShopById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void deleteShopById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    void editShopById(String decodeJson, HttpServletResponse response, HttpServletRequest request);

    List<EduShopInfo> selectAll();

}
