package robot.client.api.swipe;

import com.alibaba.fastjson.JSON;
import robot.client.api.AbstractApi;
import robot.client.common.Config;
import robot.client.common.DataOp;
import robot.client.common.TableNames;
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
        datas.setTableName(TableNames.EDU_SWIPE_CARD_RECORDS);
        datas.setUrl(getUrl());
        datas.setJson(json);
        SystemService.getInstance().addUploadDatas(datas);
    }

    @Override
    public void upload(Object object, DataOp dataOp) {
        EduSwipeCardRecords info = (EduSwipeCardRecords) object;
        if (DataOp.INSERT.equals(dataOp)) {
            info.setOp("swipeCardRecords.add");
        } else if (DataOp.MODIFY.equals(dataOp)) {
            info.setOp("swipeCardRecords.modify");
        } else if (DataOp.DELETE.equals(dataOp)) {
            info.setOp("swipeCardRecords.delete");
        }

        this.postJsonString(info);
    }
}
