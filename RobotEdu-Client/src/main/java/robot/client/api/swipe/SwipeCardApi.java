package robot.client.api.swipe;

import com.alibaba.fastjson.JSON;
import robot.client.api.AbstractApi;
import robot.client.common.Config;
import robot.client.model.swipe.EduSwipeCardRecords;
import robot.client.observer.Observer;
import robot.client.service.SystemService;
import robot.client.util.SigUtils;

/**
 * Created by Feng on 2017/5/18.
 */
public class SwipeCardApi extends AbstractApi implements Observer {

    /**
     * post　json
     *
     * @return
     */
    public void postJsonString(EduSwipeCardRecords info) {
        // 获取sig
        String sig = SigUtils.getSig(JSON.toJSONString(info), Config.MODULE_APP_SECRET);
        // 设置sig
        info.setSig(sig);
        String json = JSON.toJSONString(info);
        SystemService.UploadDatas datas = new SystemService.UploadDatas();
        datas.setRowId(info.getId());
        datas.setTableName(EduSwipeCardRecords.tableName);
        datas.setUrl(getUrl());
        datas.setJson(json);
        SystemService.getInstance().addUploadDatas(datas);
    }

    @Override
    public void upload(Object object) {
        EduSwipeCardRecords info = (EduSwipeCardRecords) object;
        info.setOp("swipeCardRecords.add");
        this.postJsonString(info);
    }
}
