package com.jex.official.controller.admin;

import com.jex.official.common.ResponseMessage;
import com.jex.official.entity.db.Official;
import com.jex.official.entity.db.SubscribeReply;
import com.jex.official.service.AdminOfficialService;
import com.jex.official.service.SubscribeReplyService;
import com.jex.official.service.dto.SubscribeReplyRequest;
import com.jex.official.service.dto.SubscribeReplyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/subscribeReply")
@Controller
public class SubscribeReplyController {

    @Autowired
    private SubscribeReplyService subscribeReplyService;
    @Autowired
    private AdminOfficialService adminOfficialService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView officialReply() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/official/subscribeReplyList");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMessage replyList(SubscribeReplyRequest model){
        SubscribeReplyResponse data = subscribeReplyService.findAllSubscribeReply(model);
        return ResponseMessage.initializeSuccessMessage(data);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView replyCreateIndex() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/official/subscribeReplyCreate");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseMessage replyCreate(SubscribeReplyRequest request) {
        int officialId = request.getOfficialId();
        String content = request.getContent();
        SubscribeReply subscribeReply = subscribeReplyService.findSubReplyByOfficialId(officialId);
        if(subscribeReply != null){
            return new ResponseMessage(ResponseMessage.PARAMETER_ERROR,"关注回复已存在");
        }
        subscribeReplyService.createSubcribeReply(officialId, content);
        return ResponseMessage.initializeSuccessMessage(null);
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseMessage replyEdit(SubscribeReplyRequest request) {
        int officialId = request.getOfficialId();
        String content = request.getContent();
        Official official = adminOfficialService.findOfficialByOfficialId(officialId);
        if(official == null){
            return new ResponseMessage(ResponseMessage.PARAMETER_ERROR,"公众号不存在");
        }
        SubscribeReply subscribeReply = subscribeReplyService.findSubReplyByOfficialId(officialId);
        if(subscribeReply == null){
            return new ResponseMessage(ResponseMessage.PARAMETER_ERROR,"关注回复不存在");
        }
        subscribeReplyService.editSubcribeReply(officialId, content);
        return ResponseMessage.initializeSuccessMessage(null);
    }
}
