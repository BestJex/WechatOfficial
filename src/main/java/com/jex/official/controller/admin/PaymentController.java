package com.jex.official.controller.admin;

import com.jex.official.common.ResponseMessage;
import com.jex.official.config.OfficialConfig;
import com.jex.official.entity.db.Payment;
import com.jex.official.service.PaymentService;
import com.jex.official.service.dto.PaymentReequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/admin/payment")
@Controller
public class PaymentController {
    private final OfficialConfig officialConfig;
    private final PaymentService paymentService;

    @Autowired
    public  PaymentController(OfficialConfig officialConfig,
                              PaymentService paymentService) {
        this.officialConfig = officialConfig;
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView paymentIndex() {
        ModelAndView mv = new ModelAndView();
        Payment payment = this.paymentService.findOnePayment();
        mv.addObject("baseUrl", officialConfig.WEB_HOST);
        mv.addObject("payment", payment);
        mv.setViewName("admin/payment/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseMessage paymentModify(PaymentReequest model) {
        this.paymentService.modifyPayment(model);
        return ResponseMessage.initializeSuccessMessage(null);
    }
}
