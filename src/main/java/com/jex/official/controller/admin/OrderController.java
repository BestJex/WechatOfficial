package com.jex.official.controller.admin;

import com.jex.official.common.ResponseMessage;
import com.jex.official.config.OfficialConfig;
import com.jex.official.entity.db.Order;
import com.jex.official.service.ActivityService;
import com.jex.official.service.OrderService;
import com.jex.official.service.dto.ActivityRequest;
import com.jex.official.service.dto.OrderRequest;
import com.jex.official.service.dto.OrderResponse;
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


@RequestMapping("/admin/order")
@Controller("/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private OfficialConfig officialConfig;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView orderIndex() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("baseUrl", officialConfig.WEB_HOST);
        mv.setViewName("admin/order/orderList");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMessage orderList(ActivityRequest model){
       OrderResponse data = this.orderService.findAllOrder(model);
        return ResponseMessage.initializeSuccessMessage(data);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView orderAdd() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/order/orderCreate");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMessage createOrder(OrderRequest orderRequest){
        int activityId = orderRequest.getActivityId();
        int price = new Double(orderRequest.getPrice()*100).intValue();
        String remark = orderRequest.getRemark();
        orderService.createOrder(activityId,price,remark);
        return new ResponseMessage(0);
    }

}
