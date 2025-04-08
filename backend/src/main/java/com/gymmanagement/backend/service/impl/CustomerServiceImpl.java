package com.gymmanagement.backend.service.impl;

import com.gymmanagement.backend.dto.get.CustomerGetDTO;
import com.gymmanagement.backend.dto.post.CustomerPostDTO;
import com.gymmanagement.backend.dto.put.CustomerPutDTO;
import com.gymmanagement.backend.mappers.CustomerMapper;
import com.gymmanagement.backend.model.Customer;
import com.gymmanagement.backend.model.User;
import com.gymmanagement.backend.repository.CustomerRepository;
import com.gymmanagement.backend.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerGetDTO> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapCustomerEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerGetDTO findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        return customerMapper.mapCustomerEntityToGetDto(customer);
    }

    @Override
    public CustomerGetDTO saveCustomer(CustomerPostDTO customerPostDTO) {
        if (customerRepository.existsByEmail(customerPostDTO.getUser().getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        customerPostDTO.getUser().setPassword(
                passwordEncoder.encode(customerPostDTO.getUser().getPassword())
        );

        System.out.println("Before mapping: " + customerPostDTO.getUser());
        Customer customer = customerMapper.mapPostDtoToCustomerEntity(customerPostDTO);
        System.out.println("After mapping: " + customer);

        customer.setUserType(User.UserType.CUSTOMER);

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.mapCustomerEntityToGetDto(savedCustomer);
    }

    @Override
    public CustomerGetDTO updateCustomer(Long id, CustomerPutDTO customerPutDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        if (customerPutDTO.getUserPutDTO().getEmail() != null &&
                !customerPutDTO.getUserPutDTO().getEmail().equals(customer.getEmail()) &&
                customerRepository.existsByEmail(customerPutDTO.getUserPutDTO().getEmail())) {
            throw new RuntimeException("The new email is already registered");
        }

        if (customerPutDTO.getUserPutDTO().getPassword() != null &&
                !customerPutDTO.getUserPutDTO().getPassword().isEmpty()) {
            customerPutDTO.getUserPutDTO().setPassword(
                    passwordEncoder.encode(customerPutDTO.getUserPutDTO().getPassword())
            );
        }

        customerMapper.updateCustomerEntityFromPutDto(customerPutDTO, customer);

        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.mapCustomerEntityToGetDto(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        customerRepository.delete(customer);
    }
}
