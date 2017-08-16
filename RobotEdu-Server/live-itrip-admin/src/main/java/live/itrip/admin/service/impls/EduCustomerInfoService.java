package live.itrip.admin.service.impls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import live.itrip.admin.bean.BootStrapDataTableList;
import live.itrip.admin.bean.PagerInfo;
import live.itrip.admin.dao.EduCustomerInfoMapper;
import live.itrip.admin.model.EduCustomerInfo;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IEduCustomerInfoService;
import live.itrip.common.ErrorCode;
import live.itrip.common.Logger;
import live.itrip.common.response.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Feng on 2017/5/19.
 */
@Service
public class EduCustomerInfoService extends BaseService implements IEduCustomerInfoService {
    @Autowired
    private EduCustomerInfoMapper eduCustomerInfoMapper;


    @Override
    public void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduCustomerInfo info = JSON.parseObject(decodeJson, EduCustomerInfo.class);
        info.setClientId(info.getId());
        info.setId(null);
        info.setShopNo(shopNo);
        info.setClientCreateTime(info.getCreateTime());
        info.setClientUpdateTime(info.getUpdateTime());
        info.setCreateTime(System.currentTimeMillis());
        info.setUpdateTime(info.getCreateTime());

        Integer ret = eduCustomerInfoMapper.insertSelective(info);

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

    @Override
    public void modify(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduCustomerInfo info = JSON.parseObject(decodeJson, EduCustomerInfo.class);

        info.setClientId(info.getId());
        info.setId(null);
        info.setShopNo(shopNo);
        info.setClientUpdateTime(info.getUpdateTime());
        info.setUpdateTime(System.currentTimeMillis());

        Integer ret = eduCustomerInfoMapper.updateClientIdAndShopNo(info);

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

    @Override
    public void selectCustomerList(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BootStrapDataTableList<EduCustomerInfo> result = new BootStrapDataTableList<>();
        try {
            // 解析查询条件
            JSONArray jsonarray = JSONArray.parseArray(decodeJson);
            String shopNo = null;
            String customerName = null;
            for (int i = 0; i < jsonarray.size(); i++) {
                JSONObject obj = (JSONObject) jsonarray.get(i);
                if (obj.get("name").equals("customerName")) {
                    customerName = obj.getString("value");
                } else if (obj.get("name").equals("shop")) {
                    shopNo = obj.getString("value");
                }
            }
            if (StringUtils.isNotEmpty(customerName)) {
                customerName = "'%" + customerName.trim() + "%'";
            }

            PagerInfo pagerInfo = this.getPagerInfo(jsonarray);
            Integer count = eduCustomerInfoMapper.countAll(shopNo, customerName);
            List<EduCustomerInfo> customerList = eduCustomerInfoMapper.selectCustomerList(shopNo, customerName, pagerInfo.getStart(), pagerInfo.getLength());

            if (customerList != null) {
                result.setsEcho(String.valueOf(pagerInfo.getDraw() + 1));
                result.setiTotalRecords(count);
                result.setiTotalDisplayRecords(count);
                result.setAaData(customerList);

                // response
                this.writeResponse(response, result);
                return;
            }
        } catch (Exception ex) {
            Logger.error(ex.getMessage(), ex);
        }

        BaseResult error = new BaseResult();
        error.setCode(ErrorCode.UNKNOWN.getCode());
        this.writeResponse(response, error);
    }

    @Override
    public void selectCustomerById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Integer customerId = (Integer) jsonObject.get("customerId");
        if (customerId != null) {
            EduCustomerInfo info = this.eduCustomerInfoMapper.selectByPrimaryKey(customerId);
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setData(info);
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }


}
