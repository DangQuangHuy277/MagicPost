package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.actor.ActorService;
import com.magicpost.app.magicPost.actor.Customer;
import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.order.dto.ExpressOrderRequest;
import com.magicpost.app.magicPost.order.dto.ExpressOrderResponse;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import com.magicpost.app.magicPost.point.repository.TransactionPointRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ActorService actorService;
    private final ModelMapper modelMapper;
    private final TransactionPointRepository transactionPointRepository;
    private final ExpressOrderRepository expressOrderRepository;
    public ExpressOrderResponse createNewExpressOrder(Long transactionPointId, @Valid ExpressOrderRequest expressOrderRequest) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));

        ExpressOrder newExpressOrder = modelMapper.map(expressOrderRequest, ExpressOrder.class);

        Customer sender = actorService.checkAndCreateCustomer(expressOrderRequest.getSender());
        newExpressOrder.setSender(sender);
        Customer receiver = actorService.checkAndCreateCustomer(expressOrderRequest.getReceiver());
        newExpressOrder.setReceiver(receiver);

        newExpressOrder = expressOrderRepository.save(newExpressOrder);
        transactionPoint.getInventory().add(newExpressOrder);
        transactionPointRepository.save(transactionPoint);

        return modelMapper.map(newExpressOrder, ExpressOrderResponse.class);
    }
}
