package com.cc.yhzt.controller;

import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.constant.RestBean;
import com.cc.yhzt.service.IAccountDataService;
import com.cc.yhzt.service.IPlayersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :cc
 * @date : 2019/6/8
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private IPlayersService playersService;
    @Resource
    private IAccountDataService accountDataService;

    @RequestMapping("/")
    public String login(Model model, HttpServletResponse response) {
        return "index";
    }

    @RequestMapping("/index")
    public String index(Model model, HttpServletResponse response) {
        return "index";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String defaultLogin() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RestBean login(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(name = "username") String username,
                          @RequestParam(name = "gamename") String gamename) {
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username.toLowerCase(), gamename.toLowerCase());
        // 执行认证登陆
        try {
            token.setRememberMe(true);//记住我
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return new RestBean(-1, "用户名或游戏名不正确~");
        } catch (IncorrectCredentialsException ice) {
            return new RestBean(-1, "角色名不正确~");
        } catch (LockedAccountException lae) {
            return new RestBean(-1, "账户已锁定~");
        } catch (ExcessiveAttemptsException eae) {
            return new RestBean(-1, "输入错误次数过多~");
        } catch (AuthenticationException ae) {
            return new RestBean(-1, "用户名或游戏名不正确~");
        }
        if (subject.isAuthenticated()) {
            return new RestBean(1, "登录成功~");
        } else {
            token.clear();
            return new RestBean(-1, "账户登录名或游戏名错误~");
        }
    }

    @RequiresRoles(Constant.ALL_ACCOUNT)
    @RequestMapping("/welcome")
    public String welcome(HttpServletRequest request,
                                HttpServletResponse response) {
        return "page/console/welcome";
    }

    @RequiresRoles(Constant.ADMIN_ACCOUNT)
    @RequestMapping("/getUserType")
    @ResponseBody
    public RestBean getUserType(HttpServletRequest request,
                                HttpServletResponse response) {
        return new RestBean(1, "gm");
    }

    @RequestMapping("/unauthcAdmin")
    public String unauthcAdmin() {
        return "page/error/error-403-amin";
    }

    @RequestMapping("/unauthc")
    public String unauthc() {
        return "page/error/error-403";
    }
}
