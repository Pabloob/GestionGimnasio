package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.get.PaymentGetDTO;
import com.gymmanagement.backend.dto.post.PaymentPostDTO;
import com.gymmanagement.backend.dto.put.PaymentPutDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentGetDTO> findAllPayments();
    PaymentGetDTO findPaymentById(Long id);
    PaymentGetDTO savePayment(PaymentPostDTO paymentPostDTO);
    PaymentGetDTO updatePayment(Long id, PaymentPutDTO paymentPutDTO);
    void deletePayment(Long id);
    List<PaymentGetDTO> findByCustomer(Long customerId);
    PaymentGetDTO processPayment(Long paymentId);
    PaymentGetDTO cancelPayment(Long paymentId);
}
