package robot.client.api.card;

import com.alibaba.fastjson.JSON;
import robot.client.api.AbstractApi;
import robot.client.common.Config;
import robot.client.common.TableNames;
import robot.client.model.card.EduCardInfo;
import robot.client.observer.Observer;
import robot.client.service.SystemService;
import robot.client.util.SigUtils;


/**
 * Created by Feng on 2017/5/17.
 */
public class CardApi extends AbstractApi implements Observer {

    /**
     * post　json
     *
     * @return
     */
    public void postJsonString(EduCardInfo cardInfo) {
        String tmp = JSON.toJSONString(cardInfo);

        // 获取sig
        String sig = SigUtils.getSig(tmp, Config.MODULE_APP_SECRET);
        // 设置sig
        cardInfo.setSig(sig);
        String json = JSON.toJSONString(cardInfo);
        SystemService.UploadDatas datas = new SystemService.UploadDatas();
        datas.setRowId(cardInfo.getId().longValue());
        datas.setTableName(TableNames.EDU_CARD_INFO);
        datas.setUrl(getUrl());
        datas.setJson(json);
        SystemService.getInstance().addUploadDatas(datas);
    }

    @Override
    public void upload(Object object) {
        EduCardInfo info = (EduCardInfo) object;
        info.setOp("cardInfo.add");
        this.postJsonString(info);
    }
}
