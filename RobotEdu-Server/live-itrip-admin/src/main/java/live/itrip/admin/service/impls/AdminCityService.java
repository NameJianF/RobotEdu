package live.itrip.admin.service.impls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import live.itrip.admin.dao.AdminCityMapper;
import live.itrip.admin.model.AdminCity;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IAdminCityService;
import live.itrip.common.ErrorCode;
import live.itrip.common.response.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Feng on 2017/8/10.
 */
@Service
public class AdminCityService extends BaseService implements IAdminCityService {

    @Autowired
    private AdminCityMapper adminCityMapper;


    @Override
    public List<AdminCity> selectByProvinceCode(String provinceCode) {
        return adminCityMapper.selectByProvinceCode(provinceCode);
    }

    @Override
    public void selectByProvinceCode(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();

        JSONObject jsonObject = JSON.parseObject(decodeJson);
        String provinceCode = jsonObject.getString("provinceCode");
        if (StringUtils.isNotEmpty(provinceCode)) {
            List<AdminCity> list = this.selectByProvinceCode(provinceCode);
            if (list != null) {
                JSONArray array = new JSONArray();
                for (AdminCity city : list) {
                    JSONObject object = new JSONObject();
                    object.put("code", city.getCode());
                    object.put("name", city.getName());
                    array.add(object);
                }
                result.setCode(ErrorCode.SUCCESS.getCode());
                result.setData(array);
                this.writeResponse(response, result);
                return;
            }
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }
}
