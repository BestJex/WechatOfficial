package com.jex.official.controller.admin;

import com.jex.official.entity.db.AdminUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/admin")
@Controller("/admin/site")
public class SiteController {
    @RequestMapping(value = "/")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        AdminUser adminUser = (AdminUser) request.getSession().getAttribute("adminUser");
        if(adminUser == null){
            mv.setViewName("redirect:/admin/user/login");
        }else {
            mv.setViewName("redirect:/admin/index");
        }
        return mv;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/index");
        return mv;
    }

    @RequestMapping(value = "/noSession", method = RequestMethod.GET)
    public ModelAndView noSession(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/noSession");
        return mv;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/welcome");
        return mv;
    }




}
