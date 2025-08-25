package com.NovaMind.Project.NovaMind.controller;

import com.NovaMind.Project.NovaMind.Documents.Payment;
import com.NovaMind.Project.NovaMind.Services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/{courseId}")
    private Payment addPayment(@RequestBody Payment payment,@PathVariable Long courseId) {
        return paymentService.addPayment(payment, courseId);
    }
    @PostMapping("/addpaymentformodule/{ModuleId}")
    private Payment addPaymentModule(@RequestBody Payment payment,@PathVariable Long ModuleId) {
        return paymentService.addPaymentModule(payment, ModuleId);
    }
}
