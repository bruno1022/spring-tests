package com.javaee.bruno.springtests.services;

import java.util.List;

import com.javaee.bruno.springtests.api.v1.model.CustomerDTO;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(String id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerByDTO(String id, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(String id, CustomerDTO customerDTO);

    void deleteCustomerById(String id);
}
