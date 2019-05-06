package com.jex.official.controller.admin;

import com.jex.official.common.ResponseMessage;
import com.jex.official.entity.db.Official;
import com.jex.official.service.AdminOfficialService;
import com.jex.official.service.KeywordReplyService;
import com.jex.official.service.dto.KeywordReplyCreateRequest;
import com.jex.official.service.dto.KeywordReplyEditRequest;
import com.jex.official.service.dto.KeywordReplyListRequest;
import com.jex.official.service.dto.KeywordReplyListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/admin/keywordReply")
@Controller("/admin/keywordReply")
public class KeywordReplyController {

    @Autowired
    private KeywordReplyService keywordReplyService;
    @Autowired
    private AdminOfficialService adminOfficialService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(){
        List<Official> list = this.adminOfficialService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("officials", list);
        mv.setViewName("admin/keywordReply/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseMessage list(KeywordReplyListRequest model){
        KeywordReplyListResponse data = this.keywordReplyService.getKeywordReplies(model);
        return ResponseMessage.initializeSuccessMessage(data);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/keywordReply/create");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseMessage create(KeywordReplyCreateRequest model){
        if(model.validate()){
            if(this.keywordReplyService.createKeywordReply(model)) {
                return ResponseMessage.initializeSuccessMessage(null);
            }else{
                return new ResponseMessage(ResponseMessage.PARAMETER_ERROR, "参数异常");
            }
        }else{
            return new ResponseMessage(ResponseMessage.PARAMETER_ERROR, "参数异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMessage delete(@RequestParam int id){
        if(id <= 0){
            return new ResponseMessage(ResponseMessage.PARAMETER_ERROR, "参数异常");
        }
        this.keywordReplyService.deleteKeywordReply(id);
        return ResponseMessage.initializeSuccessMessage(null);
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseMessage edit(KeywordReplyEditRequest model){
        if(this.keywordReplyService.updateKeywordReply(model)){
            return ResponseMessage.initializeSuccessMessage(null);
        }else{
            return new ResponseMessage(ResponseMessage.PARAMETER_ERROR, "参数异常");
        }
    }
}
