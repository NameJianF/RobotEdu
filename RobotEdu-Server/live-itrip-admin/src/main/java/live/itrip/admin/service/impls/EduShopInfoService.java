package live.itrip.admin.service.impls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import live.itrip.admin.bean.BootStrapDataTableList;
import live.itrip.admin.bean.PagerInfo;
import live.itrip.admin.dao.EduShopInfoMapper;
import live.itrip.admin.model.EduShopInfo;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IEduShopInfoService;
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
 * Created by Feng on 2017/8/10.
 */
@Service
public class EduShopInfoService extends BaseService implements IEduShopInfoService {
    @Autowired
    private EduShopInfoMapper eduShopInfoMapper;

    @Override
    public void selectShopList(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BootStrapDataTableList<EduShopInfo> result = new BootStrapDataTableList<>();
        try {
            // 解析查询条件
            JSONArray jsonarray = JSONArray.parseArray(decodeJson);
            String province = null;
            String city = null;
            String name = null;
            for (int i = 0; i < jsonarray.size(); i++) {
                JSONObject obj = (JSONObject) jsonarray.get(i);
                if (obj.get("name").equals("name")) {
                    name = obj.getString("value");
                } else if (obj.get("name").equals("province")) {
                    province = obj.getString("value");
                } else if (obj.get("name").equals("city")) {
                    city = obj.getString("value");
                }
            }
            if (StringUtils.isNotEmpty(name)) {
                name = "'%" + name.trim() + "%'";
            }

            PagerInfo pagerInfo = this.getPagerInfo(jsonarray);
            Integer count = eduShopInfoMapper.countAll(province, city, name);
            List<EduShopInfo> shopList = eduShopInfoMapper.selectShopList(province, city, name, pagerInfo.getStart(), pagerInfo.getLength());

            if (shopList != null) {
                result.setsEcho(String.valueOf(pagerInfo.getDraw() + 1));
                result.setiTotalRecords(count);
                result.setiTotalDisplayRecords(count);
                result.setAaData(shopList);

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
    public void selectShopById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Integer shopId = (Integer) jsonObject.get("shopId");
        if (shopId != null) {
            EduShopInfo info = this.eduShopInfoMapper.selectByPrimaryKey(shopId);
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setData(info);
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }

    @Override
    public void deleteShopById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Integer shopId = (Integer) jsonObject.get("shopId");
        if (shopId != null) {
            EduShopInfo info = new EduShopInfo();
            info.setId(shopId);
            info.setStatus(EduShopInfo.STATUS_INVALID); // 不可用
            Integer ret = this.eduShopInfoMapper.updateByPrimaryKeySelective(info);
            if (ret > 0) {
                result.setCode(ErrorCode.SUCCESS.getCode());
//                result.setData(info);
                this.writeResponse(response, result);
                return;
            }
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }

    @Override
    public void editShopById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduShopInfo shopInfo = JSON.parseObject(decodeJson, EduShopInfo.class);
        Integer ret;
        if (shopInfo.getId() == null) {
            // new
            shopInfo.setCreateTime(System.currentTimeMillis());
            shopInfo.setUpdateTime(shopInfo.getCreateTime());
            ret = this.eduShopInfoMapper.insertSelective(shopInfo);
        } else {
            // update
            shopInfo.setUpdateTime(System.currentTimeMillis());
            ret = this.eduShopInfoMapper.updateByPrimaryKeySelective(shopInfo);
        }
        if (ret > 0) {
            result.setCode(ErrorCode.SUCCESS.getCode());
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }

    @Override
    public List<EduShopInfo> selectAll() {
        return eduShopInfoMapper.selectAll();
    }
}
