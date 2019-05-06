package com.jex.official.controller.admin;

import com.jex.official.common.ResponseMessage;
import com.jex.official.entity.db.Activity;
import com.jex.official.entity.db.Official;
import com.jex.official.service.ActivityService;
import com.jex.official.service.AdminOfficialService;
import com.jex.official.service.dto.ActivityRequest;
import com.jex.official.service.dto.ActivityResponse;
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

@RequestMapping("/admin/activity")
@Controller("/admin/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private AdminOfficialService adminOfficialService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView activityIndex() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/activity/activityList");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMessage activityList(ActivityRequest request){
        ActivityResponse data = this.activityService.findAllActivity(request);
        return ResponseMessage.initializeSuccessMessage(data);
    }

    @ResponseBody
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public ResponseMessage activitySelect(){
        List<Activity> activityList = this.activityService.findAll();
        List<ActivityResponse.ActivityItemResponse> result = new ArrayList<>();
        for (Activity activity : activityList) {
            ActivityResponse.ActivityItemResponse item = new ActivityResponse.ActivityItemResponse();
            item.setId(activity.getId());
            item.setName(activity.getName());
            result.add(item);
        }
        return ResponseMessage.initializeSuccessMessage(result);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView activityAdd() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/activity/activityCreate");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMessage createActivity(ActivityRequest model){
        activityService.createActivity(model);
        return ResponseMessage.initializeSuccessMessage(null);
    }
}
