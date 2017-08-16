package live.itrip.admin.service.impls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import live.itrip.admin.bean.BootStrapDataTableList;
import live.itrip.admin.bean.PagerInfo;
import live.itrip.admin.dao.EduCardInfoMapper;
import live.itrip.admin.model.EduCardInfo;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IEduCardInfoService;
import live.itrip.common.ErrorCode;
import live.itrip.common.Logger;
import live.itrip.common.response.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Feng on 2017/5/19.
 */
@Service
public class EduCardInfoService extends BaseService implements IEduCardInfoService {
    @Autowired
    private EduCardInfoMapper eduCardInfoMapper;


    @Override
    public void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduCardInfo info = JSON.parseObject(decodeJson, EduCardInfo.class);
        info.setClientId(info.getId());
        info.setId(null);
        info.setShopNo(shopNo);
        info.setClientCreateTime(info.getCreateTime());
        info.setClientUpdateTime(info.getUpdateTime());
        info.setCreateTime(System.currentTimeMillis());
        info.setUpdateTime(info.getCreateTime());

        Integer ret = eduCardInfoMapper.insertSelective(info);

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
        EduCardInfo info = JSON.parseObject(decodeJson, EduCardInfo.class);
        info.setClientId(info.getId());
        info.setId(null);
        info.setShopNo(shopNo);
        info.setClientUpdateTime(info.getUpdateTime());
        info.setUpdateTime(info.getCreateTime());

        Integer ret = eduCardInfoMapper.updateClientIdAndShopNo(info);

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
    public void selectCardList(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BootStrapDataTableList<EduCardInfo> result = new BootStrapDataTableList<>();
        try {
            // 解析查询条件
            JSONArray jsonarray = JSONArray.parseArray(decodeJson);
            String shopNo = null;
            String cardNo = null;
            for (int i = 0; i < jsonarray.size(); i++) {
                JSONObject obj = (JSONObject) jsonarray.get(i);
                if (obj.get("name").equals("shop")) {
                    shopNo = obj.getString("value");
                } else if (obj.get("name").equals("cardNo")) {
                    cardNo = obj.getString("value");
                }
            }

            PagerInfo pagerInfo = this.getPagerInfo(jsonarray);
            Integer count = eduCardInfoMapper.countAll(shopNo, cardNo);
            List<EduCardInfo> list = eduCardInfoMapper.selectCardList(shopNo, cardNo, pagerInfo.getStart(), pagerInfo.getLength());

            if (list != null) {
                result.setsEcho(String.valueOf(pagerInfo.getDraw() + 1));
                result.setiTotalRecords(count);
                result.setiTotalDisplayRecords(count);
                result.setAaData(list);

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
    public void selectCardById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Integer cardId = (Integer) jsonObject.get("cardId");
        if (cardId != null) {
            EduCardInfo info = this.eduCardInfoMapper.selectByPrimaryKey(cardId);
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setData(info);
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }

    @Override
    public void deleteCardById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Integer cardId = (Integer) jsonObject.get("cardId");
        if (cardId != null) {
            Integer ret = this.eduCardInfoMapper.deleteByPrimaryKey(cardId);
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
    public void editCardById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduCardInfo info = JSON.parseObject(decodeJson, EduCardInfo.class);
        Integer ret;
        if (info.getId() == null) {
            // new
            info.setCreateTime(System.currentTimeMillis());
            info.setUpdateTime(info.getCreateTime());
            ret = this.eduCardInfoMapper.insertSelective(info);
        } else {
            // update
            info.setUpdateTime(System.currentTimeMillis());
            ret = this.eduCardInfoMapper.updateByPrimaryKeySelective(info);
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
