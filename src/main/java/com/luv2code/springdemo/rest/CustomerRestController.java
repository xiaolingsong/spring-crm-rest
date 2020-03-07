package com.luv2code.springdemo.rest;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    // autowire the CustomerService
    @Autowired
    private CustomerService customerService;

    // add mapping for get /customers
    @GetMapping("/customers")
    public List<Customer> getCustomers(){

        return customerService.getCustomers();
    }

    //
    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable int customerId){

        Customer customer = customerService.getCustomer(customerId);
        if(customer == null)
            throw new CustomerNotFoundException("Customer Id not found - "+ customerId);
        return customer;
    }

    // add mapping for POST /customers -- add new customer
    @PostMapping("/customers")
    public Customer addCustomers(@RequestBody Customer customer){
        // just in case the passed id is json ... set the Id to 0
        // this is forced a save of new item ... instead of update
        customer.setId(0);
        customerService.saveCustomer(customer);
        return customer;
    }

    // add mapping for PUT /customers - update existing customer
    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer){

        customerService.saveCustomer(customer);

        return customer;
    }

    // add mapping for DELETE /customers/{customerId} -delete customer
    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId){
        Customer tempCustomer = customerService.getCustomer(customerId);
        if(tempCustomer == null)
            throw new CustomerNotFoundException("Customer Id not found - "+ customerId);

        customerService.deleteCustomer(customerId);

        return "the deleted customer Id is :" + customerId;
    }
}
