package com.magicpost.app.magicPost.actor;

import com.magicpost.app.magicPost.actor.dto.CustomerDTO;
import com.magicpost.app.magicPost.actor.entity.Customer;
import com.magicpost.app.magicPost.actor.entity.Shipper;
import com.magicpost.app.magicPost.actor.repository.CustomerRepository;
import com.magicpost.app.magicPost.actor.repository.ShipperRepository;
import com.magicpost.app.magicPost.address.AddressService;
import com.magicpost.app.magicPost.address.entity.Address;
import com.magicpost.app.magicPost.exception.InvalidBusinessConditionException;
import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import com.magicpost.app.magicPost.point.repository.TransactionPointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final ShipperRepository shipperRepository;
    private final TransactionPointRepository transactionPointRepository;

    @Transactional
    public Customer checkAndCreateCustomer(CustomerDTO customerDTO) {
        Address address = addressService.checkAndCreateAddress(customerDTO.getAddress());
        Customer customer = customerRepository.findByPhone(customerDTO.getPhone())
                .orElseGet(() -> new Customer(customerDTO.getPhone()));
        // * If customer have new address then set that
        if (address != null) customer.setAddress(address);
        // ! Assumption: the name of request will be correct according to the phone (if it different, it is same person but rename =))) )
        customer.setName(customerDTO.getName());
        return customerRepository.save(customer);
    }

    public Set<Shipper> getAllShipperInTransPoint(Long transactionPointId) {
        return transactionPointRepository.findById(transactionPointId).orElseThrow(
                        () -> new ResourceNotFoundException("Transaction Point"))
                .getShippers();
    }

    @Transactional
    public Shipper addNewShipperToTransPoint(Long transactionPointId, Shipper shipper) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));

        shipper.setTransactionPoint(transactionPoint);
        transactionPoint.getShippers().add(shipper);
        // ? Does save transactionPoint (The One of OneToMany) before the shipper (Many) is right
//        transactionPointRepository.save(transactionPoint);
        return shipperRepository.save(shipper);
    }


    @Transactional
    public void removeShipperAtTransPoint(Long transactionPointId, UUID shipperId) {
        if (!transactionPointRepository.existsById(transactionPointId))
            throw new ResourceNotFoundException("Transaction Point");
        Shipper shipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipper"));
        if (!shipper.getTransactionPoint().getId().equals(transactionPointId))
            throw new InvalidBusinessConditionException("The Shipper is not at this Transaction Point");
        shipperRepository.delete(shipper);
    }
}
