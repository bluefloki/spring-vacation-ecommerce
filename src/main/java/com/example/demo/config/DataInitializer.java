package com.example.demo.config;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // division
        Division div = new Division();
        div.setId(42L);

        // create sample customers
        Customer c1 = new Customer("George", "Washington", "100 Mt Vernon", "87434", "1110001010", div);
        Customer c2 = new Customer("John", "Adams", "75 Adams Street", "56789", "2221112020", div);
        Customer c3 = new Customer("Thomas", "Jefferson", "Monticello", "12345", "3332223030", div);
        Customer c4 = new Customer("James", "Madison", "Montpelier", "67890", "4443334040", div);
        Customer c5 = new Customer("James", "Monroe", "Highland", "98765", "5554445050", div);


        // save customers
        if(customerRepository.count() <= 1) {
            customerRepository.save(c1);
            customerRepository.save(c2);
            customerRepository.save(c3);
            customerRepository.save(c4);
            customerRepository.save(c5);
        }
    }
}