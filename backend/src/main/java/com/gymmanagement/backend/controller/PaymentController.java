package com.gymmanagement.backend.controller;

import com.gymmanagement.backend.dto.get.PaymentGetDTO;
import com.gymmanagement.backend.dto.post.PaymentPostDTO;
import com.gymmanagement.backend.dto.put.PaymentPutDTO;
import com.gymmanagement.backend.service.interfaces.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentGetDTO>> getAllPayments() {
        List<PaymentGetDTO> payments = paymentService.findAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentGetDTO> getPaymentById(@PathVariable Long id) {
        PaymentGetDTO payment = paymentService.findPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<PaymentGetDTO> createPayment(@RequestBody PaymentPostDTO paymentPostDTO) {
        PaymentGetDTO newPayment = paymentService.savePayment(paymentPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentGetDTO> updatePayment(@PathVariable Long id,
                                                       @RequestBody PaymentPutDTO paymentPutDTO) {
        PaymentGetDTO updatedPayment = paymentService.updatePayment(id, paymentPutDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentGetDTO>> getPaymentsByCustomer(@PathVariable Long customerId) {
        List<PaymentGetDTO> payments = paymentService.findByCustomer(customerId);
        return ResponseEntity.ok(payments);
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<Void> processPayment(@PathVariable Long id) {
        paymentService.processPayment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long id) {
        paymentService.cancelPayment(id);
        return ResponseEntity.noContent().build();
    }
}
