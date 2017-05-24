package robot.client.api.customer;

import com.alibaba.fastjson.JSON;
import robot.client.api.AbstractApi;
import robot.client.common.Config;
import robot.client.common.TableNames;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.observer.Observer;
import robot.client.service.SystemService;
import robot.client.util.SigUtils;

/**
 * Created by Feng on 2017/5/18.
 */
public class CustomerApi extends AbstractApi implements Observer {

    /**
     * post　json
     *
     * @return
     */
    public void postJsonString(EduCustomerInfo customerInfo) {
        // 获取sig
        String sig = SigUtils.getSig(JSON.toJSONString(customerInfo), Config.MODULE_APP_SECRET);
        // 设置sig
        customerInfo.setSig(sig);
        String json = JSON.toJSONString(customerInfo);
        SystemService.UploadDatas datas = new SystemService.UploadDatas();
        datas.setRowId(customerInfo.getId().longValue());
        datas.setTableName(TableNames.EDU_CUSTOMER_INFO);
        datas.setUrl(getUrl());
        datas.setJson(json);
        SystemService.getInstance().addUploadDatas(datas);
    }

    @Override
    public void upload(Object object) {
        EduCustomerInfo info = (EduCustomerInfo) object;
        info.setOp("customerInfo.add");
        this.postJsonString(info);
    }
}