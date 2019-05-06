package com.jex.official.controller;

import com.jex.official.common.Constant;
import com.jex.official.entity.db.Order;
import com.jex.official.service.WechatPayService;
import com.jex.official.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/order")
@Controller("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private WechatPayService wechatPayService;

    @RequestMapping(value = "/write/{orderId}",method = RequestMethod.GET)
    public ModelAndView writeOrder(HttpServletRequest request, @PathVariable("orderId") String orderId) {
        ModelAndView mv = new ModelAndView();
        Object userId = request.getSession().getAttribute(Constant.SESSION_PAY_USER_ID);
        if (userId == null) {
            //mv.setViewName("redirect:/wechat/pay/auth/" + orderId);
            //return mv;
            return this.wechatPayService.payAuth(orderId);
        }
        Order order = this.orderService.findOrderByOrderId(orderId);
        if (order != null) {
            if(order.getStatus()==1){
                mv.addObject("order",order);
                mv.setViewName("/order/paySuccess");
                return mv;
            } else {
                double price = this.orderService.getPriceByOrderId(orderId);
                mv.addObject("orderId", orderId);
                mv.addObject("price",String .format("%.2f",price /100));
                mv.setViewName("order/address");
            }
        }
        return mv;
    }
}
