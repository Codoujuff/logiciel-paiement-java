package com.company.payment.controller;

import com.company.payment.dto.PaymentRequest;
import com.company.payment.dto.PaymentResponse;
import com.company.payment.entity.Payment;
import com.company.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    public String paymentsPage(Model model) {
        return "payments";
    }

    @GetMapping("/api")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    @ResponseBody
    public List<PaymentResponse> listAll() {
        return paymentService.findAll();
    }

    @GetMapping("/api/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    @ResponseBody
    public PaymentResponse getById(@PathVariable Long id) {
        return paymentService.findById(id);
    }

    @PostMapping("/api")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    @ResponseBody
    public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest request) {
        PaymentResponse created = paymentService.create(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/api/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYE')")
    @ResponseBody
    public PaymentResponse update(@PathVariable Long id, @RequestBody PaymentRequest request) {
        return paymentService.update(id, request);
    }

    @DeleteMapping("/api/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
