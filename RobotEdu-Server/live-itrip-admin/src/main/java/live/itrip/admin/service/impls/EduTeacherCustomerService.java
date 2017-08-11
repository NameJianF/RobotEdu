package live.itrip.admin.service.impls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import live.itrip.admin.bean.BootStrapDataTableList;
import live.itrip.admin.bean.PagerInfo;
import live.itrip.admin.dao.EduTeacherCustomerMapper;
import live.itrip.admin.model.EduTeacherCustomer;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IEduTeacherCustomerService;
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
public class EduTeacherCustomerService extends BaseService implements IEduTeacherCustomerService {
    @Autowired
    private EduTeacherCustomerMapper eduTeacherCustomerMapper;

    /**
     * 添加 teacher 信息
     *
     * @param shopNo
     * @param decodeJson
     * @param response
     * @param request
     */
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

    @Override
    public void selectTeacherCustomerList(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
//        BootStrapDataTableList<EduTeacherCustomer> result = new BootStrapDataTableList<>();
//        try {
//            // 解析查询条件
//            JSONArray jsonarray = JSONArray.parseArray(decodeJson);
//            String shopNo = null;
//            String teacherName = null;
//            for (int i = 0; i < jsonarray.size(); i++) {
//                JSONObject obj = (JSONObject) jsonarray.get(i);
//                if (obj.get("name").equals("teacherName")) {
//                    teacherName = obj.getString("value");
//                } else if (obj.get("name").equals("shopNo")) {
//                    shopNo = obj.getString("value");
//                }
//            }
//            if (StringUtils.isNotEmpty(teacherName)) {
//                teacherName = "'%" + teacherName.trim() + "%'";
//            }
//
//            PagerInfo pagerInfo = this.getPagerInfo(jsonarray);
//            Integer count = eduTeacherCustomerMapper.countAll(shopNo, teacherName);
//            List<EduTeacherCustomer> teacherList = eduTeacherCustomerMapper.selectTeacherCustomerList(shopNo, teacherName, pagerInfo.getStart(), pagerInfo.getLength());
//
//            if (teacherList != null) {
//                result.setsEcho(String.valueOf(pagerInfo.getDraw() + 1));
//                result.setiTotalRecords(count);
//                result.setiTotalDisplayRecords(count);
//                result.setAaData(teacherList);
//
//                // response
//                this.writeResponse(response, result);
//                return;
//            }
//        } catch (Exception ex) {
//            Logger.error(ex.getMessage(), ex);
//        }
//
//        BaseResult error = new BaseResult();
//        error.setCode(ErrorCode.UNKNOWN.getCode());
//        this.writeResponse(response, error);
    }

    @Override
    public void selectTeacherCustomerById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Long id = (Long) jsonObject.get("id");
        if (id != null) {
            EduTeacherCustomer info = this.eduTeacherCustomerMapper.selectByPrimaryKey(id);
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setData(info);
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }

    @Override
    public void deleteTeacherCustomerById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Long id = (Long) jsonObject.get("id");
        if (id != null) {
            Integer ret = this.eduTeacherCustomerMapper.deleteByPrimaryKey(id);
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
    public void editTeacherCustomerById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduTeacherCustomer info = JSON.parseObject(decodeJson, EduTeacherCustomer.class);
        Integer ret;
        if (info.getId() == null) {
            // new
            info.setCreateTime(System.currentTimeMillis());
            info.setUpdateTime(info.getCreateTime());
            ret = this.eduTeacherCustomerMapper.insertSelective(info);
        } else {
            // update
            info.setUpdateTime(System.currentTimeMillis());
            ret = this.eduTeacherCustomerMapper.updateByPrimaryKeySelective(info);
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
