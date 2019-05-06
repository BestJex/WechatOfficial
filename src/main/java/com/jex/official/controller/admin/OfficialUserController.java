package com.jex.official.controller.admin;

import com.jex.official.common.ResponseMessage;
import com.jex.official.entity.db.Official;
import com.jex.official.service.AdminOfficialService;
import com.jex.official.service.OfficialUserService;
import com.jex.official.service.dto.OfficialUserRequest;
import com.jex.official.service.dto.OfficialUserListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/admin/officialUser")
@Controller
public class OfficialUserController {

    @Autowired
    private OfficialUserService officialUserService;
    @Autowired
    private AdminOfficialService adminOfficialService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(){
        List<Official> list = this.adminOfficialService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("officials", list);
        mv.setViewName("admin/officialUser/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseMessage list(OfficialUserRequest model){
        OfficialUserListResponse data = this.officialUserService.getOfficialUserList(model);
        return ResponseMessage.initializeSuccessMessage(data);
    }
}
