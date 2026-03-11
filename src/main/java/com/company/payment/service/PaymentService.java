package com.company.payment.service;

import com.company.payment.dto.PaymentRequest;
import com.company.payment.dto.PaymentResponse;
import com.company.payment.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> findAll();
    List<Payment> findAllPayments();
    PaymentResponse findById(Long id);
    PaymentResponse create(PaymentRequest request);
    PaymentResponse update(Long id, PaymentRequest request);
    void delete(Long id);
}
