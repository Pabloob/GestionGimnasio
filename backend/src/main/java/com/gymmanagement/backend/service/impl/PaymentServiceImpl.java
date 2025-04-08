package com.gymmanagement.backend.service.impl;

import com.gymmanagement.backend.dto.get.PaymentGetDTO;
import com.gymmanagement.backend.dto.post.PaymentPostDTO;
import com.gymmanagement.backend.dto.put.PaymentPutDTO;
import com.gymmanagement.backend.mappers.PaymentMapper;
import com.gymmanagement.backend.model.Customer;
import com.gymmanagement.backend.model.Payment;
import com.gymmanagement.backend.repository.CustomerRepository;
import com.gymmanagement.backend.repository.PaymentRepository;
import com.gymmanagement.backend.service.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentGetDTO> findAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::mapPaymentEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentGetDTO findPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        return paymentMapper.mapPaymentEntityToGetDto(payment);
    }

    @Override
    public PaymentGetDTO savePayment(PaymentPostDTO dto) {
        // Validate customer existence
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow();

        // Validate positive amount
        if (dto.getAmount() <= 0) {
            throw new RuntimeException("The payment amount must be positive.");
        }

        Payment payment = paymentMapper.mapPostDtoToPaymentEntity(dto);
        payment.setCustomer(customer);

        Payment saved = paymentRepository.save(payment);
        return paymentMapper.mapPaymentEntityToGetDto(saved);
    }

    @Override
    public PaymentGetDTO updatePayment(Long id, PaymentPutDTO dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow();
            payment.setCustomer(customer);
        }

        if (dto.getAmount() != null && dto.getAmount() <= 0) {
            throw new RuntimeException("The payment amount must be positive.");
        }

        paymentMapper.updatePaymentEntityFromPutDto(dto, payment);
        Payment updated = paymentRepository.save(payment);
        return paymentMapper.mapPaymentEntityToGetDto(updated);
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public List<PaymentGetDTO> findByCustomer(Long clientId) {
        return paymentRepository.findByCustomerId(clientId)
                .stream()
                .map(paymentMapper::mapPaymentEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentGetDTO processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        payment.setPaymentDate(LocalDate.now());
        payment.setPaid(true);

        Payment processed = paymentRepository.save(payment);
        return paymentMapper.mapPaymentEntityToGetDto(processed);
    }

    @Override
    public PaymentGetDTO cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        payment.setPaid(false);

        Payment canceled = paymentRepository.save(payment);
        return paymentMapper.mapPaymentEntityToGetDto(canceled);
    }
}
2