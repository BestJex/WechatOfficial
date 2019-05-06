package com.jex.official.controller;

import com.jex.official.config.OfficialConfig;
import com.jex.official.service.WechatOfficialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OfficialCallBackController {
    private final static Logger logger = LoggerFactory.getLogger(OfficialCallBackController.class);
    @Autowired
    private OfficialConfig officialConfig;
    @Autowired
    private WechatOfficialService officialService;

    @RequestMapping(value = "/official/callback/{appId}", method = RequestMethod.GET)
    public String getIndex(@PathVariable String appId, String signature, String timestamp, String nonce, String echostr) throws  Exception{
        if(StringUtils.hasText(appId) && StringUtils.hasText(signature) && StringUtils.hasText(timestamp)
                && StringUtils.hasText(nonce) && StringUtils.hasText(echostr)){
            if(officialService.checkSignature(appId, signature, timestamp, nonce)){
                return echostr;
            }
        }
        return "FAIL";
    }

    @RequestMapping(value = "/official/callback/{appId}", method = RequestMethod.POST)
    public String postIndex(@PathVariable String appId, @RequestBody String xml){
        try {
            if (!StringUtils.hasText(xml)) {
                return "FAIL";
            }
            String result = this.officialService.acceptMessage(xml);
            return result;
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            return "ERROR";
        }
    }

    @RequestMapping(value = "/official/auth", method = RequestMethod.GET)
    public ModelAndView auth(HttpServletRequest request, @RequestParam("code") String code, @RequestParam(name = "state") String state){
        try{
            ModelAndView mv = officialService.oauthCallback(request, code, state);
            return mv;
        }catch(Exception ex){
            return new ModelAndView("error/500");
        }
    }

    @RequestMapping(value = "/official/baseAuth", method = RequestMethod.GET)
    public ModelAndView baseAuth(HttpServletRequest request, @RequestParam("code") String code, @RequestParam(name = "state") String state){
        try{
            ModelAndView mv = officialService.baseAuthCallback(request, code, state);
            return mv;
        }catch(Exception ex){
            return new ModelAndView("error/500");
        }
    }
}
