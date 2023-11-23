package com.magicpost.app.magicPost.actor;

import com.magicpost.app.magicPost.actor.Customer;
import com.magicpost.app.magicPost.actor.CustomerRepository;
import com.magicpost.app.magicPost.actor.dto.CustomerDTO;
import com.magicpost.app.magicPost.address.AddressService;
import com.magicpost.app.magicPost.address.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    public Customer checkAndCreateCustomer(CustomerDTO customerDTO){
        Address address = addressService.checkAndCreateAddress(customerDTO.getAddress());
        Customer customer = customerRepository.findByPhone(customerDTO.getPhone())
                .orElseGet(() -> new Customer(customerDTO.getPhone()));
        //if customer have new address then set that
        if(address != null) customer.setAddress(address);
        //Assumption: the name of request will be correct according to the phone (if it different, it is same person but rename =))) )
        customer.setName(customerDTO.getName());
        return customerRepository.save(customer);
    }
}
