package robot.client.api.card;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import robot.client.api.AbstractApi;
import robot.client.api.http.HttpUtils;
import robot.client.common.Config;
import robot.client.model.card.EduCardInfo;
import robot.client.service.SystemService;
import robot.client.util.Logger;
import robot.client.util.SigUtils;

/**
 * Created by Feng on 2017/5/17.
 */
public class CardApi extends AbstractApi {
    private String action = "";

    /**
     * post　json
     *
     * @return
     */
    public void postJsonString(EduCardInfo cardInfo) {
        // 获取sig
        String sig = SigUtils.getSig(JSON.toJSONString(cardInfo), Config.MODULE_APP_SECRET);
        // 设置sig
        cardInfo.setSig(sig);
        String json = JSON.toJSONString(cardInfo);
        SystemService.UploadDatas datas = new SystemService.UploadDatas();
        datas.setUrl(Config.SERVER_URL + action);
        datas.setJson(json);
        SystemService.getInstance().addUploadDatas(datas);
    }
}
