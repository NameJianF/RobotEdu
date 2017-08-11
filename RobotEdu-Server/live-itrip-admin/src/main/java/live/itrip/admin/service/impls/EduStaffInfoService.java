package live.itrip.admin.service.impls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import live.itrip.admin.bean.BootStrapDataTableList;
import live.itrip.admin.bean.PagerInfo;
import live.itrip.admin.dao.EduStaffInfoMapper;
import live.itrip.admin.model.EduStaffInfo;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IEduStaffInfoService;
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
public class EduStaffInfoService extends BaseService implements IEduStaffInfoService {

    @Autowired
    private EduStaffInfoMapper eduStaffInfoMapper;

    @Override
    public void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduStaffInfo info = JSON.parseObject(decodeJson, EduStaffInfo.class);
        info.setClientId(info.getId());
        info.setId(null);
        info.setShopNo(shopNo);
        info.setClientCreateTime(info.getCreateTime());
        info.setClientUpdateTime(info.getUpdateTime());
        info.setCreateTime(System.currentTimeMillis());
        info.setUpdateTime(info.getCreateTime());

        Integer ret = eduStaffInfoMapper.insertSelective(info);

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
        EduStaffInfo info = JSON.parseObject(decodeJson, EduStaffInfo.class);
        info.setClientId(info.getId());
        info.setId(null);
        info.setShopNo(shopNo);
        info.setClientUpdateTime(info.getUpdateTime());
        info.setUpdateTime(info.getCreateTime());

        Integer ret = eduStaffInfoMapper.updateClientIdAndShopNo(info);

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
    public void selectStaffList(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BootStrapDataTableList<EduStaffInfo> result = new BootStrapDataTableList<>();
        try {
            // 解析查询条件
            JSONArray jsonarray = JSONArray.parseArray(decodeJson);
            String shopNo = null;
            String staffName = null;
            for (int i = 0; i < jsonarray.size(); i++) {
                JSONObject obj = (JSONObject) jsonarray.get(i);
                if (obj.get("name").equals("staffName")) {
                    staffName = obj.getString("value");
                } else if (obj.get("name").equals("shop")) {
                    shopNo = obj.getString("value");
                }
            }
            if (StringUtils.isNotEmpty(staffName)) {
                staffName = "'%" + staffName.trim() + "%'";
            }

            PagerInfo pagerInfo = this.getPagerInfo(jsonarray);
            Integer count = eduStaffInfoMapper.countAll(shopNo, staffName);
            List<EduStaffInfo> teacherList = eduStaffInfoMapper.selectStaffList(shopNo, staffName, pagerInfo.getStart(), pagerInfo.getLength());

            if (teacherList != null) {
                result.setsEcho(String.valueOf(pagerInfo.getDraw() + 1));
                result.setiTotalRecords(count);
                result.setiTotalDisplayRecords(count);
                result.setAaData(teacherList);

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
    public void selectStaffById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Integer staffId = (Integer) jsonObject.get("staffId");
        if (staffId != null) {
            EduStaffInfo info = this.eduStaffInfoMapper.selectByPrimaryKey(staffId);
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setData(info);
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }

    @Override
    public void deleteStaffById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Integer staffId = (Integer) jsonObject.get("staffId");
        if (staffId != null) {
            EduStaffInfo info = new EduStaffInfo();
            info.setId(staffId);
            info.setStatus(EduStaffInfo.STATUS_LEAVE); // 离职
            Integer ret = this.eduStaffInfoMapper.updateByPrimaryKey(info);
            if (ret > 0) {
                result.setCode(ErrorCode.SUCCESS.getCode());
                this.writeResponse(response, result);
                return;
            }
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }

    @Override
    public void editStaffById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduStaffInfo info = JSON.parseObject(decodeJson, EduStaffInfo.class);
        Integer ret;
        if (info.getId() == null) {
            // new
            info.setCreateTime(System.currentTimeMillis());
            info.setUpdateTime(info.getCreateTime());
            ret = this.eduStaffInfoMapper.insertSelective(info);
        } else {
            // update
            info.setUpdateTime(System.currentTimeMillis());
            ret = this.eduStaffInfoMapper.updateByPrimaryKeySelective(info);
        }
        if (ret > 0) {
            result.setCode(ErrorCode.SUCCESS.getCode());
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }
}
