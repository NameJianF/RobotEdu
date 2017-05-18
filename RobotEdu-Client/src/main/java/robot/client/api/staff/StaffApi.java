package robot.client.api.staff;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import robot.client.api.AbstractApi;
import robot.client.common.Config;
import robot.client.common.ErrorCode;
import robot.client.model.staff.EduStaffInfo;
import robot.client.model.staff.User;
import robot.client.observer.Observer;
import robot.client.service.SystemService;
import robot.client.util.Logger;
import robot.client.util.SigUtils;

/**
 * Created by Feng on 2017/5/12.
 */
public class StaffApi extends AbstractApi implements Observer {

    private static final String ACTION_URL_SSO = "sso.action";

    public static String getUrl() {
        return Config.SERVER_URL + ACTION_URL_SSO;
    }

    /**
     * post　json
     *
     * @return
     */
    public void postJsonString(EduStaffInfo info) {
        // 获取sig
        String sig = SigUtils.getSig(JSON.toJSONString(info), Config.MODULE_APP_SECRET);
        // 设置sig
        info.setSig(sig);
        String json = JSON.toJSONString(info);
        SystemService.UploadDatas datas = new SystemService.UploadDatas();
        datas.setUrl(getUrl());
        datas.setJson(json);
        SystemService.getInstance().addUploadDatas(datas);
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    public ErrorCode login(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return ErrorCode.USERNAME_PWD_INVALID;
        }

        try {
            JSONObject jsonObject = getBaseJsonObject();
            jsonObject.put("op", "Sso.login");

            JSONObject data = new JSONObject();
            data.put("email", userName);
            data.put("password", password);
            data.put("source", Config.MODULE_SOURCE);
            data.put("clientVersion", Config.CLIENT_VERSION);

            jsonObject.put("data", data);

            String res = this.postJsonString(jsonObject, ACTION_URL_SSO);

            if (StringUtils.isEmpty(res)) {
                return ErrorCode.USERNAME_PWD_INVALID;
            }
            JSONObject resOject = JSON.parseObject(res);
            if (resOject.getInteger("code") != null && resOject.get("data") != null) {
                User loginUser = JSON.parseObject(resOject.getString("data"), User.class);
                Config.LoginUser = loginUser;

                return ErrorCode.SUCCESS;
            }

        } catch (Exception ex) {
            Logger.error(ex.getMessage(), ex);
        }

        return ErrorCode.UNKNOWN;
    }

    @Override
    public void upload(Object object) {
        this.postJsonString((EduStaffInfo) object);
    }
}
