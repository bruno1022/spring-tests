package com.javaee.bruno.springtests.services;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaee.bruno.springtests.api.v1.mapper.CustomerMapper;
import com.javaee.bruno.springtests.api.v1.model.CustomerDTO;
import com.javaee.bruno.springtests.domain.Customer;
import com.javaee.bruno.springtests.repositories.CustomerRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CustomerServiceImplIT {

	@Autowired
    CustomerRepository customerRepository;
	
	CustomerMapper customerMapper;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());
        
        customerMapper = new CustomerMapper();

        //setup data for testing
        Customer joe = new Customer();
        joe.setFirstName("Joe");
        joe.setLastName("Newman");

        Customer michael = new Customer();
        michael.setFirstName("Michael");
        michael.setLastName("Lachappele");

        customerRepository.save(joe);
        customerRepository.save(michael);

        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        String updatedName = "UpdatedName";
        String id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(id).get();
        assertNotNull(originalCustomer);
        //save original first name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        String updatedName = "UpdatedName";
        String id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(id).get();
        assertNotNull(originalCustomer);

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
    }

    private String getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }
}
