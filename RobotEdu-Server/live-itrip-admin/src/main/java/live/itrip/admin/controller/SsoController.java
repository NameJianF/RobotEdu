package live.itrip.admin.controller;

import com.alibaba.fastjson.JSON;
import live.itrip.admin.common.Config;
import live.itrip.admin.common.SsoOprations;
import live.itrip.admin.controller.base.AbstractController;
import live.itrip.admin.model.ClientApiKey;
import live.itrip.admin.service.intefaces.ISsoService;
import live.itrip.common.ErrorCode;
import live.itrip.common.Logger;
import live.itrip.common.request.RequestHeader;
import live.itrip.common.response.BaseResult;
import live.itrip.common.util.JsonStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Feng on 2017/5/12.
 */

@Controller
public class SsoController extends AbstractController {

    @Autowired
    private ISsoService iSsoService;

    /**
     * sso 用户相关操作
     *
     * @param json
     * @param response
     * @param request
     */
    @RequestMapping("/sso")
    public
    @ResponseBody
    void sso(@RequestBody String json,
             HttpServletResponse response, HttpServletRequest request) {
        String decodeJson = JsonStringUtils.decoderForJsonString(json);
        Logger.debug(
                String.format("timestamp:%s action:%s json:%s",
                        System.currentTimeMillis(), "user", decodeJson));

        if (StringUtils.isEmpty(decodeJson)) {
            this.jsonIsEmpty(response);
            return;
        }

        try {
            RequestHeader header = JSON
                    .parseObject(decodeJson, RequestHeader.class);
            String op = header.getOp();

            // validate Api Params
            BaseResult error = this.validateParams(header, decodeJson);
            if (error.getCode() == ErrorCode.SUCCESS.getCode()) {
                if (SsoOprations.OP_SSO_LOGIN.equalsIgnoreCase(op)) {
                    // login
                    iSsoService.login(decodeJson, response, request);
                } else if (SsoOprations.OP_SSO_LOGOUT.equalsIgnoreCase(op)) {
                    // logout
                } else if (SsoOprations.OP_SSO_AUTH.equalsIgnoreCase(op)) {
                    // auth
                    iSsoService.authUser(decodeJson, response, request);
                }
            } else {
                this.writeResponseErrorApp(response, error);
            }

        } catch (Exception ex) {
            Logger.error(ex.getMessage(), ex);
        }
    }

    public BaseResult validateParams(RequestHeader header, String jsonString) {
        BaseResult error = new BaseResult();

        if (header == null) {
            error.setError(ErrorCode.UNKNOWN);
            return error;
        }

//        ClientApiKey clientApiKey = validateApiKey(header.getApikey());
//
//        if (clientApiKey == null) {
//            error.setCode(ErrorCode.PARAM_INVALID.getCode());
//            error.setMsg(String.format("%s(apikey)", ErrorCode.PARAM_INVALID.getMessage()));
//            return error;
//        }
//
//        if (!validateSig(clientApiKey, header, jsonString)) {
//            error.setCode(ErrorCode.PARAM_INVALID.getCode());
//            error.setMsg(String.format("%s(sig)", ErrorCode.PARAM_INVALID.getMessage()));
//            return error;
//        }

        if (!validateTimestamp(header.getTimestamp())) {
            error.setCode(ErrorCode.PARAM_INVALID.getCode());
            error.setMsg(String.format("%s(timestamp)", ErrorCode.PARAM_INVALID.getMessage()));
            return error;
        }

        if (!SsoOprations.OP_SSO_LOGIN.equalsIgnoreCase(header.getOp())) {
            if (!validateToken(header.getSid())) {
                error.setCode(ErrorCode.PARAM_INVALID.getCode());
                error.setMsg(String.format("%s(sid)", ErrorCode.PARAM_INVALID.getMessage()));
                return error;
            }
        }

        error.setError(ErrorCode.SUCCESS);
        return error;
    }

    private ClientApiKey validateApiKey(String apiKey) {
        if (StringUtils.isEmpty(apiKey)) {
            return null;
        }
        for (ClientApiKey client : Config.LIST_APIKEY) {
            if (client.getApiKey().equals(apiKey)) {
                return client;
            }
        }

        return null;
    }

    /**
     * error params
     *
     * @param response
     * @param error
     */
    protected void writeResponseErrorApp(HttpServletResponse response, BaseResult error) {
        this.writeResponse(response, error);
    }
}
