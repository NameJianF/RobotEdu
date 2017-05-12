package robot.client.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import robot.client.api.http.HttpUtils;
import robot.client.common.Config;
import robot.client.util.Logger;
import robot.client.util.SigUtils;

/**
 * Created by Feng on 2017/5/12.
 */
public abstract class AbstractApi {

    protected JSONObject getBaseJsonObject() {
        JSONObject object = new JSONObject();
        object.put("apikey", Config.MODULE_APP_KEY);
        if (Config.LoginUser != null) {
            object.put("sid", Config.LoginUser.getToken());
        }
        object.put("timestamp", System.currentTimeMillis());
//        object.put("", );

        return object;
    }


    /**
     * post　json
     *
     * @param reqObj
     * @param action
     * @return
     */
    public String postJsonString(JSONObject reqObj, String action) {
        String strResponse = "";
        String url = "";
        try {
            // 获取sig
            String sig = SigUtils.getSig(JSON.toJSONString(reqObj), Config.MODULE_APP_SECRET);
            // 设置sig
            reqObj.put("sig", sig);
            String json = JSON.toJSONString(reqObj);
            url = Config.SERVER_URL + action;
            Logger.info(String.format("URL:%s,Request JSON:%s", url, json));
            strResponse = HttpUtils.httpRequest(url, HttpUtils.REQUEST_METHOD_POST, json);
            Logger.info(String.format("URL:%s,Response JSON:%s", url, strResponse));
        } catch (Exception e) {
            Logger.error(url + " 发送json data 请求出错", e);
        }

        return strResponse;
    }
}
