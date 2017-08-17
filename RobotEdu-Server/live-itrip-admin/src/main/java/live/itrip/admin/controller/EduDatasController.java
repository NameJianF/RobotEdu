package live.itrip.admin.controller;

import com.alibaba.fastjson.JSON;
import live.itrip.admin.controller.base.AbstractController;
import live.itrip.admin.model.ClientApiKey;
import live.itrip.admin.service.intefaces.*;
import live.itrip.common.Logger;
import live.itrip.common.request.RequestHeader;
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
 * Created by Feng on 2017/5/19.
 */
@Controller
public class EduDatasController extends AbstractController {
    @Autowired
    private IEduCardInfoService iEduCardInfoService;
    @Autowired
    private IEduCustomerInfoService iEduCustomerInfoService;
    @Autowired
    private IEduStaffInfoService iEduStaffInfoService;
    @Autowired
    private IEduSwipeCardRecordsService iEduSwipeCardRecordsService;
    @Autowired
    private IEduTeacherCustomerService iEduTeacherCustomerService;

    /**
     * 系统配置模块
     *
     * @param json
     * @param response
     * @param request
     */
    @RequestMapping("/edu")
    public
    @ResponseBody
    void eduDatas(@RequestBody String json, HttpServletResponse response, HttpServletRequest request) {
        String decodeJson = JsonStringUtils.decoderForJsonString(json);
        Logger.debug(
                String.format("timestamp:%s action:%s json:%s",
                        System.currentTimeMillis(), "eduDatas", decodeJson));
        if (StringUtils.isEmpty(decodeJson)) {
            this.paramInvalid(response, "JSON");
            return;
        }

        try {
            RequestHeader header = JSON.parseObject(decodeJson, RequestHeader.class);
            if (header != null && StringUtils.isNotEmpty(header.getOp())) {
                String op = header.getOp();
                // cardInfo
                if ("cardInfo.add".equalsIgnoreCase(op)) {
                    iEduCardInfoService.insert(getShopNo(header.getApikey()), decodeJson, response, request);
                } else if ("cardInfo.modify".equalsIgnoreCase(op)) {
                    iEduCardInfoService.modify(getShopNo(header.getApikey()), decodeJson, response, request);
                }

                // CustomerInfo
                else if ("customerInfo.add".equalsIgnoreCase(op)) {
                    iEduCustomerInfoService.insert(getShopNo(header.getApikey()), decodeJson, response, request);
                } else if ("customerInfo.modify".equalsIgnoreCase(op)) {
                    iEduCustomerInfoService.modify(getShopNo(header.getApikey()), decodeJson, response, request);
                }

                // StaffInfo
                else if ("staffInfo.add".equalsIgnoreCase(op)) {
                    iEduStaffInfoService.insert(getShopNo(header.getApikey()), decodeJson, response, request);
                } else if ("staffInfo.modify".equalsIgnoreCase(op)) {
                    iEduStaffInfoService.modify(getShopNo(header.getApikey()), decodeJson, response, request);
                }

                // SwipeCardRecords
                else if ("swipeCardRecords.add".equalsIgnoreCase(op)) {
                    iEduSwipeCardRecordsService.insert(getShopNo(header.getApikey()), decodeJson, response, request);
                }

                // TeacherCustomer
                else if ("teacherCustomer.add".equalsIgnoreCase(op)) {
                    iEduTeacherCustomerService.insert(getShopNo(header.getApikey()), decodeJson, response, request);
                }
            }
        } catch (Exception ex) {
            Logger.error("", ex);
        }
    }

    /**
     * 获取门店编号
     *
     * @param apikey
     * @return
     */
    private String getShopNo(String apikey) {
        ClientApiKey clientApiKey = this.validateClientApiKey(apikey);
        return clientApiKey.getClientName();
    }
}
