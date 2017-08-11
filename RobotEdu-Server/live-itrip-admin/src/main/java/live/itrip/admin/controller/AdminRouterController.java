package live.itrip.admin.controller;

import live.itrip.admin.controller.base.AbstractController;
import live.itrip.admin.model.*;
import live.itrip.admin.service.intefaces.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Created by Feng on 2016/8/4.
 * <p>
 * 后台管理 页面路由
 */
@Controller
public class AdminRouterController extends AbstractController {
    @Autowired
    private IAdminDictService iAdminDictService;
    @Autowired
    private IAdminDepartService iAdminDepartService;
    @Autowired
    private IAdminDictItemService iAdminDictItemService;
    @Autowired
    private IAdminProvinceService iAdminProvinceService;
    @Autowired
    private IEduShopInfoService iEduShopInfoService;


    @RequestMapping(value = "/system/index", method = RequestMethod.GET)
    public String pagesIndex(HttpServletResponse response, HttpServletRequest request, Model model) {
        //权限校验。判断是否包含权限。
        Subject currentSubject = SecurityUtils.getSubject();

//        if (currentSubject.hasRole(Constants.RoleName.ADMINISTRATOR) || currentSubject.hasRole(Constants.RoleName.ADMIN)) {
        return "pages/index";
//        } else {
//            throw new AuthorizationException();
//        }
    }


    @RequestMapping(value = "/system/login", method = RequestMethod.GET)
    public String pagesLogin() {
        return "pages/login";
    }

    @RequestMapping(value = "/system/profile", method = RequestMethod.GET)
    public String pagesProfile(HttpServletRequest request, Model model) {
        Subject currentSubject = SecurityUtils.getSubject();
        AdminUser user = (AdminUser) currentSubject.getPrincipal();
        model.addAttribute("user", user);
        return "pages/system/profile";
    }

    @RequestMapping(value = "/system/dashboard", method = RequestMethod.GET)
    public String pagesDashboard() {
        return "pages/dashboard";
    }

    @RequestMapping(value = "/system/module", method = RequestMethod.GET)
    public String systemModule() {
        return "pages/system/module";
    }

    @RequestMapping(value = "/system/dictItem", method = RequestMethod.GET)
    public String dictItem(HttpServletRequest request, Model model) {
        List<AdminDict> dictList = iAdminDictService.selectAllDicts();
        model.addAttribute("dictList", dictList);

        return "pages/system/dictItem";
    }

    @RequestMapping(value = "/system/dict", method = RequestMethod.GET)
    public String systemDict() {
        return "pages/system/dict";
    }

    @RequestMapping(value = "/system/depart", method = RequestMethod.GET)
    public String systemDepart() {
        return "pages/system/depart";
    }

    @RequestMapping(value = "/system/operation", method = RequestMethod.GET)
    public String systemOperation() {
        return "pages/system/operation";
    }

    @RequestMapping(value = "/system/role", method = RequestMethod.GET)
    public String systemRole() {
        return "pages/system/role";
    }

    @RequestMapping(value = "/system/apikey", method = RequestMethod.GET)
    public String systemApikey() {
        return "pages/system/apikey";
    }

    @RequestMapping(value = "/system/member", method = RequestMethod.GET)
    public String systemMember(HttpServletRequest request, Model model) {
        List<AdminDepart> departList = iAdminDepartService.selectAllDeparts();
        model.addAttribute("departList", departList);
        return "pages/system/member";
    }

    @RequestMapping(value = "/system/group", method = RequestMethod.GET)
    public String systemGroup(HttpServletRequest request, Model model) {
        List<AdminDepart> departList = iAdminDepartService.selectAllDeparts();
        model.addAttribute("departList", departList);
        return "pages/system/group";
    }

    /**
     * 系统日志
     *
     * @return
     */
    @RequestMapping(value = "/system/log", method = RequestMethod.GET)
    public String systemLog() {
        return "pages/system/log";
    }


    // ================== edu pages ========

    @RequestMapping(value = "/edu/shop", method = RequestMethod.GET)
    public String systemShop(HttpServletRequest request, Model model) {
        List<AdminProvince> provinceList = iAdminProvinceService.selectAll();
        model.addAttribute("provinceList", provinceList);
        return "pages/edu/shop";
    }

    @RequestMapping(value = "/edu/staff", method = RequestMethod.GET)
    public String systemTeacher(HttpServletRequest request, Model model) {
        List<EduShopInfo> shopList = iEduShopInfoService.selectAll();
        model.addAttribute("shopList", shopList);
        return "pages/edu/staff";
    }
}
