package com.gymmanagement.backend.service.interfaces;

import com.gymmanagement.backend.dto.get.CustomerGetDTO;
import com.gymmanagement.backend.dto.post.CustomerPostDTO;
import com.gymmanagement.backend.dto.put.CustomerPutDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerGetDTO> findAllCustomers();
    CustomerGetDTO findCustomerById(Long id);
    CustomerGetDTO saveCustomer(CustomerPostDTO customerPostDTO);
    CustomerGetDTO updateCustomer(Long id, CustomerPutDTO customerPutDTO);
    void deleteCustomer(Long id);
}
