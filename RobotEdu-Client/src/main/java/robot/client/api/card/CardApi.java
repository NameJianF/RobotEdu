package robot.client.api.card;

import com.alibaba.fastjson.JSON;
import robot.client.api.AbstractApi;
import robot.client.common.Config;
import robot.client.model.card.EduCardInfo;
import robot.client.observer.Observer;
import robot.client.service.SystemService;
import robot.client.util.SigUtils;


/**
 * Created by Feng on 2017/5/17.
 */
public class CardApi extends AbstractApi implements Observer {

    public static String getUrl() {
        return Config.SERVER_URL + "";
    }

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
        datas.setUrl(getUrl());
        datas.setJson(json);
        SystemService.getInstance().addUploadDatas(datas);
    }

    @Override
    public void upload(Object object) {
        this.postJsonString((EduCardInfo) object);
    }
}
