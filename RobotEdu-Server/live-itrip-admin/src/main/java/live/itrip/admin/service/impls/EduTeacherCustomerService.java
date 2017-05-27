package live.itrip.admin.service.impls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import live.itrip.admin.dao.EduTeacherCustomerMapper;
import live.itrip.admin.model.EduTeacherCustomer;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IEduTeacherCustomerService;
import live.itrip.common.ErrorCode;
import live.itrip.common.response.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Feng on 2017/5/19.
 */
@Service
public class EduTeacherCustomerService extends BaseService implements IEduTeacherCustomerService {
    @Autowired
    private EduTeacherCustomerMapper eduTeacherCustomerMapper;

    @Override
    public void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduTeacherCustomer info = JSON.parseObject(decodeJson, EduTeacherCustomer.class);
        info.setClientId(info.getId());
        info.setId(null);
        info.setShopNo(shopNo);
        info.setClientCreateTime(info.getCreateTime());
        info.setClientUpdateTime(info.getUpdateTime());
        info.setCreateTime(System.currentTimeMillis());
        info.setUpdateTime(info.getCreateTime());

        Integer ret = eduTeacherCustomerMapper.insertSelective(info);

        if (ret > 0) {
            result.setCode(ErrorCode.SUCCESS.getCode());
            JSONObject data = new JSONObject();
            data.put("id", ret);
            result.setData(data);
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }
}
