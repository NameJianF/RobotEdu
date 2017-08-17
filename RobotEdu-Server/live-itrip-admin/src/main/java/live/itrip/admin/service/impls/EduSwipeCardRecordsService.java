package live.itrip.admin.service.impls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import live.itrip.admin.bean.BootStrapDataTableList;
import live.itrip.admin.bean.PagerInfo;
import live.itrip.admin.dao.EduSwipeCardRecordsMapper;
import live.itrip.admin.model.EduSwipeCardRecords;
import live.itrip.admin.service.BaseService;
import live.itrip.admin.service.intefaces.IEduSwipeCardRecordsService;
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
public class EduSwipeCardRecordsService extends BaseService implements IEduSwipeCardRecordsService {
    @Autowired
    private EduSwipeCardRecordsMapper eduSwipeCardRecordsMapper;

    @Override
    public void insert(String shopNo, String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        EduSwipeCardRecords info = JSON.parseObject(decodeJson, EduSwipeCardRecords.class);

        info.setId(null);
        info.setShopNo(shopNo);
        info.setClientCreateTime(info.getCreateTime());
        info.setCreateTime(System.currentTimeMillis());

        Integer ret = eduSwipeCardRecordsMapper.insertSelective(info);

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
    public void selectSwipeCardList(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BootStrapDataTableList<EduSwipeCardRecords> result = new BootStrapDataTableList<>();
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
            Integer count = this.eduSwipeCardRecordsMapper.countAll(shopNo, cardNo);
            List<EduSwipeCardRecords> list = this.eduSwipeCardRecordsMapper.selectSwipeCardList(shopNo, cardNo, pagerInfo.getStart(), pagerInfo.getLength());

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
    public void selectSwipeCardById(String decodeJson, HttpServletResponse response, HttpServletRequest request) {
        BaseResult result = new BaseResult();
        JSONObject jsonObject = JSON.parseObject(decodeJson);
        Long recordId = (Long) jsonObject.get("recordId");
        if (recordId != null) {
            EduSwipeCardRecords info = this.eduSwipeCardRecordsMapper.selectByPrimaryKey(recordId);
            result.setCode(ErrorCode.SUCCESS.getCode());
            result.setData(info);
            this.writeResponse(response, result);
            return;
        }

        result.setError(ErrorCode.UNKNOWN);
        this.writeResponse(response, result);
    }


}
