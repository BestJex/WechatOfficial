package com.jex.official.controller.pay;

import com.jex.official.common.DateTimeUtils;
import com.jex.official.config.OfficialConfig;
import com.jex.official.entity.db.Order;
import com.jex.official.sdk.wechat.XMLParse;
import com.jex.official.service.WechatPayService;
import com.jex.official.service.OrderService;
import com.jex.official.service.dto.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/pay/wechat")
@Controller
public class WechatPayController {
    private static Logger logger = LoggerFactory.getLogger(WechatPayController.class);

    @Autowired
    OrderService orderService;
    @Autowired
    WechatPayService wechatPayService;
    @Autowired
    OfficialConfig officialConfig;

    @RequestMapping(value = "/{orderId}",method = RequestMethod.POST)
    public ModelAndView pay(HttpServletRequest request, OrderRequest orderModel, @PathVariable("orderId") String orderId) {
        ModelAndView mv = new ModelAndView();
        Order order = orderService.findOrderByOrderId(orderId);

        try {
            Map<String, String> payMap  = this.wechatPayService.pay(request, orderModel, order);
            mv.addObject("price",order.getPrice()/100);
            mv.addObject("payMap",payMap);
            mv.setViewName("order/address");
        }catch (Exception ex){
            mv.setViewName("redirect:error/500");
        }
        return mv;
    }

    @RequestMapping(value = "/callback")
    public String payCallBack(@RequestBody String resxml) {
        try {
            Map<String, String> restmap = XMLParse.xmlParse(resxml);
            logger.info("支付结果：" + restmap.get("return_code") + "  订单号：" + restmap.get("out_trade_no"));
            if ("SUCCESS".equals(restmap.get("return_code"))) {
                // 订单支付成功 业务处理
                String orderId = restmap.get("out_trade_no"); // 商户订单号
                Order order = orderService.findOrderByOrderId(orderId);
                if (order != null && order.getStatus() == Order.STATUS_NOT_PAY && Integer.valueOf(restmap.get("total_fee")) == order.getPrice()) {
                    order.setStatus(Order.STATUS_PAY_COMPLETE);
                    order.setPaymentTime(DateTimeUtils.dateToLocalTime(DateTimeUtils.parseDate(restmap.get("time_end"))));
                    order.setTradeNo(restmap.get("transaction_id"));
                    order.setPayType(Order.WECHAT_TYPE_PAY);
                    orderService.updateOrder(order);
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
    }

}
