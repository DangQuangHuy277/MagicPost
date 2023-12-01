package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.actor.ActorService;
import com.magicpost.app.magicPost.actor.entity.Customer;
import com.magicpost.app.magicPost.exception.InvalidBusinessConditionException;
import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.order.dto.ExpressOrderRequest;
import com.magicpost.app.magicPost.order.dto.ExpressOrderResponse;
import com.magicpost.app.magicPost.order.dto.TrackingEventResponse;
import com.magicpost.app.magicPost.order.entity.ExpressOrder;
import com.magicpost.app.magicPost.order.entity.TrackingEvent;
import com.magicpost.app.magicPost.order.repository.ExpressOrderRepository;
//import com.magicpost.app.magicPost.order.repository.TrackingEventRepository;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import com.magicpost.app.magicPost.point.repository.TransactionPointRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ActorService actorService;
    private final ModelMapper modelMapper;
    private final TransactionPointRepository transactionPointRepository;
    private final ExpressOrderRepository expressOrderRepository;
//    private final TrackingEventRepository trackingEventRepository;

    @Transactional
    public ExpressOrderResponse createNewExpressOrder(Long transactionPointId, @Valid ExpressOrderRequest expressOrderRequest) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));

        // * map field in request
        ExpressOrder newExpressOrder = modelMapper.map(expressOrderRequest, ExpressOrder.class);


        // * set customer
        if (expressOrderRequest.getReceiver().getPhone().equals(expressOrderRequest.getSender().getPhone()))
            throw new InvalidBusinessConditionException("Each customer must have unique phone");
        Customer sender = actorService.checkAndCreateCustomer(expressOrderRequest.getSender());
        newExpressOrder.setSender(sender);
        Customer receiver = actorService.checkAndCreateCustomer(expressOrderRequest.getReceiver());
        newExpressOrder.setReceiver(receiver);

        // * set tracking event
        TrackingEvent postedEvent = new TrackingEvent(TrackingEvent.POSTED, LocalDateTime.now(), transactionPoint.getAddress());
//        postedEvent = trackingEventRepository.save(postedEvent);
        newExpressOrder.getTrackingEvents().add(postedEvent);

        // * set source Transaction Point
        newExpressOrder.setSourcePoint(transactionPoint);

        newExpressOrder = expressOrderRepository.save(newExpressOrder);
        transactionPoint.getInventory().putIfAbsent(newExpressOrder.getId(), newExpressOrder);
        transactionPointRepository.save(transactionPoint);

        return modelMapper.map(newExpressOrder, ExpressOrderResponse.class);
    }

    public List<TrackingEventResponse> getTrackingEventOfOrder(UUID expressOrderId) {
        ExpressOrder expressOrder = expressOrderRepository.findById(expressOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Express Order with id " + expressOrderId));
        return expressOrder.getTrackingEvents().stream()
                .map((element) -> modelMapper.map(element, TrackingEventResponse.class))
                .toList();
    }
}
