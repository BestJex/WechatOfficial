package com.jex.official.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.jex.official.common.ResponseMessage;
import com.jex.official.entity.db.Official;
import com.jex.official.sdk.wechat.WechatSDK;
import com.jex.official.service.AdminOfficialService;
import com.jex.official.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RequestMapping("/admin/official")
@Controller("/admin/official")
public class OfficialController {


    @Autowired
    private AdminOfficialService adminOfficialService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView officialIndex() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/official/officialList");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMessage officialList(OfficialRequest model){
        OfficialResponse data = this.adminOfficialService.findAllOfficial(model);
        return ResponseMessage.initializeSuccessMessage(data);
    }

    @ResponseBody
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public ResponseMessage officialSelect(){
        List<Official> officialList = this.adminOfficialService.findAll();
        List<OfficialResponse.OfficialItemResponse> result = new ArrayList<>();
        for (Official official : officialList) {
            OfficialResponse.OfficialItemResponse item = new OfficialResponse.OfficialItemResponse();
            item.setId(official.getId());
            item.setName(official.getName());
            result.add(item);
        }
        return ResponseMessage.initializeSuccessMessage(result);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView officialAdd() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/official/officialCreate");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMessage createOfficial(OfficialRequest model){
        adminOfficialService.createOfficial(model);
        return ResponseMessage.initializeSuccessMessage(null);
    }

    @RequestMapping(value = "/menu/{appId}", method = RequestMethod.GET)
    public ModelAndView officialMenu( @PathVariable("appId") String appId) {
        String menu = "{menu:{button:[]}}";
        ModelAndView mv = new ModelAndView();
        Official official = this.adminOfficialService.findOfficialByAppId(appId);
        WechatSDK sdk = new WechatSDK(official.getAppId(), official.getAppSecret());
        String response = sdk.getOfficialMenu();
        JSONObject jsonObj = JSONObject.parseObject(response);
        if(jsonObj.containsKey("menu")){
            menu = response;
        }
        mv.addObject("menu", menu);
        mv.addObject("official", official);
        mv.setViewName("admin/official/menuCreate");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/menu/save/{appId}", method = RequestMethod.POST)
    public ResponseMessage saveOfficialMenu(@PathVariable("appId") String appId, @RequestBody String menu) {
        Official official = this.adminOfficialService.findOfficialByAppId(appId);
        WechatSDK sdk = new WechatSDK(official.getAppId(), official.getAppSecret());
        String response;
        JSONObject jsonMenu = JSONObject.parseObject(menu);
        if(jsonMenu.getJSONArray("button").size() == 0){
             response = sdk.delOfficialMenu();
        } else {
             response = sdk.saveOfficialMenu(menu);
        }
        System.out.println(response);
        JSONObject jsonObj = JSONObject.parseObject(response);
        if (jsonObj.getInteger("errcode" )!= 0){
            return new ResponseMessage(ResponseMessage.SYSTEM_ERROR,"保存菜单失败");
        }
        return ResponseMessage.initializeSuccessMessage(null);
    }
}
