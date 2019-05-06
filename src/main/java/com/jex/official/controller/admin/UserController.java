package com.jex.official.controller.admin;

import com.jex.official.common.SecurityUtils;
import com.jex.official.entity.db.AdminUser;
import com.jex.official.service.UserService;
import com.jex.official.service.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/admin/user")
@Controller("admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(LoginRequest loginModel) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("loginModel",loginModel);
        mv.setViewName("admin/user/login");
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, LoginRequest loginModel) throws Exception {
        ModelAndView mv = new ModelAndView();
        String username = loginModel.getUsername();
        String password = SecurityUtils.SHA256WithKey(loginModel.getPassword());
        AdminUser adminUser = userService.findByUsernameAndPassword(username,password);
        if(adminUser != null){
            request.getSession().setAttribute("adminUser", adminUser);
            mv.setViewName("redirect:/admin/index");
            return mv;
        }
       else {
            loginModel.setError("账号或密码错误");
            mv.addObject("loginModel",loginModel);
            mv.setViewName("/admin/user/login");
            return mv;
        }
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public ModelAndView loginOut(HttpSession session) {
        ModelAndView mv = new ModelAndView();
            session.invalidate();
            mv.setViewName("redirect:/admin/user/login");
            return mv;
        }
}


