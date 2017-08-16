package live.itrip.admin.service.intefaces;

import live.itrip.admin.model.AdminCity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Feng on 2017/8/10.
 */
public interface IAdminCityService {

    List<AdminCity> selectByProvinceCode(String provinceCode);

    void selectByProvinceCode(String decodeJson, HttpServletResponse response, HttpServletRequest request);
}
