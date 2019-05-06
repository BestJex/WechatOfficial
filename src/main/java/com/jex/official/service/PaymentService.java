package com.jex.official.service;

import com.jex.official.entity.db.Payment;
import com.jex.official.repository.PaymentRepository;
import com.jex.official.service.dto.PaymentReequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findOnePayment() {
        return this.paymentRepository.findOnePayment();
    }

    public void modifyPayment(PaymentReequest model) {
        Optional<Payment> optionalPayment = this.paymentRepository.findById(model.getId());
        if(!optionalPayment.isPresent()) {
            return;
        }
        Payment payment = optionalPayment.get();
        payment.setAppId(model.getAppId());
        payment.setAppSecret(model.getAppSecret());
        payment.setMchId(model.getMchId());
        payment.setPayKey(model.getPayKey());
        payment.setNotifyUrl(model.getNotifyUrl());
        this.paymentRepository.save(payment);
    }
}
