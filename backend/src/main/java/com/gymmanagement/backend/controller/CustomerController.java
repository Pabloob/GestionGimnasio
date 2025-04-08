package com.gymmanagement.backend.controller;

import com.gymmanagement.backend.dto.get.CustomerGetDTO;
import com.gymmanagement.backend.dto.post.CustomerPostDTO;
import com.gymmanagement.backend.dto.put.CustomerPutDTO;
import com.gymmanagement.backend.service.interfaces.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerGetDTO>> getAllCustomers() {
        List<CustomerGetDTO> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerGetDTO> getCustomerById(@PathVariable Long id) {
        CustomerGetDTO customer = customerService.findCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerGetDTO> createCustomer(@RequestBody CustomerPostDTO customerPostDTO) {
        CustomerGetDTO newCustomer = customerService.saveCustomer(customerPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerGetDTO> updateCustomer(@PathVariable Long id,
                                                         @RequestBody CustomerPutDTO customerPutDTO) {
        CustomerGetDTO updatedCustomer = customerService.updateCustomer(id, customerPutDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
