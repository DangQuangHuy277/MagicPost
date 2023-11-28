package com.magicpost.app.magicPost.transport;

import com.magicpost.app.magicPost.exception.InvalidRequestDataException;
import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.order.entity.ExpressOrder;
import com.magicpost.app.magicPost.order.entity.TrackingEvent;
import com.magicpost.app.magicPost.order.repository.ExpressOrderRepository;
//import com.magicpost.app.magicPost.order.repository.TrackingEventRepository;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import com.magicpost.app.magicPost.point.repository.GatheringPointRepository;
import com.magicpost.app.magicPost.point.repository.PointRepository;
import com.magicpost.app.magicPost.point.repository.TransactionPointRepository;
import com.magicpost.app.magicPost.transport.dto.P2PTransportOrderRequest;
import com.magicpost.app.magicPost.transport.dto.P2PTransportOrderResponse;
import com.magicpost.app.magicPost.transport.entity.P2PTransportOrder;
import com.magicpost.app.magicPost.transport.entity.TransportOrder;
import com.magicpost.app.magicPost.transport.repository.P2PTransportOrderRepository;
import com.magicpost.app.magicPost.transport.repository.TransportOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportOrderRepository<TransportOrder> transportOrderRepository;
    private final TransactionPointRepository transactionPointRepository;
    private final PointRepository<Point> pointRepository;
    private final P2PTransportOrderRepository p2PTransportOrderRepository;
    private final ModelMapper modelMapper;
    private final GatheringPointRepository gatheringPointRepository;
//    private final TrackingEventRepository trackingEventRepository;
    private final ExpressOrderRepository expressOrderRepository;

    @Transactional
    public P2PTransportOrderResponse makeTransportOrderFromSrcTrans(Long transactionPointId, P2PTransportOrderRequest transportOrderRequest) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction point"));


        //create new transport order
        P2PTransportOrder newP2PTransportOrder = new P2PTransportOrder();
        newP2PTransportOrder.setFrom(transactionPoint);
        newP2PTransportOrder.setTo(transactionPoint.getGatheringPoint());
        newP2PTransportOrder.setDepartureTime(LocalDateTime.now());
        newP2PTransportOrder.setStatus(TransportOrder.Status.SHIPPING);


        //move expressOrder from transactionPoint to transportOrder and update status and tracking event
        for (var expressId : transportOrderRequest.getExpressOrderIdList()) {
            //update status and add tracking event for expressOrder
            ExpressOrder expressOrder = transactionPoint.getInventory().get(expressId);
            if (expressOrder == null)
                throw new ResourceNotFoundException("Express Order");
            expressOrder.setStatus(ExpressOrder.Status.TRANSPORTING_FROM_SRC_TRANSACTION);
            TrackingEvent trackingEvent = TrackingEvent.builder()
                    .message(TrackingEvent.TRANSPORTING_FROM_SRC_TRANSACTION)
                    .timestamp(LocalDateTime.now())
                    .location(transactionPoint.getAddress())
                    .build();
            expressOrder.getTrackingEvents().add(trackingEvent);
            expressOrder.getTransportOrders().add(newP2PTransportOrder);
//            trackingEvent = trackingEventRepository.save(trackingEvent);
            expressOrderRepository.save(expressOrder);

            //take expressOrder from transaction point inventory to transportOrder
            transactionPoint.getInventory().remove(expressId);
            newP2PTransportOrder.getExpressOrders().add(expressOrder);
        }
        //commit save at transactionPoint and transportOrder
        transactionPointRepository.save(transactionPoint);
        return modelMapper.map(transportOrderRepository.save(newP2PTransportOrder), P2PTransportOrderResponse.class);
    }

    public List<P2PTransportOrderResponse> getTransportOrderToThisPoint(Long pointId) {
        if (!pointRepository.existsById(pointId))
            throw new ResourceNotFoundException("Point");
        return p2PTransportOrderRepository.findByTo_Id(pointId).stream()
                .map((element) -> modelMapper.map(element, P2PTransportOrderResponse.class))
                .toList();
    }

    public List<P2PTransportOrderResponse> getTransportOrderToTransactionPoint(Long transactionPointId) {
        if (!transactionPointRepository.existsById(transactionPointId))
            throw new ResourceNotFoundException("Transaction Point");
        return p2PTransportOrderRepository.findByTo_Id(transactionPointId).stream()
                .map((element) -> modelMapper.map(element, P2PTransportOrderResponse.class))
                .toList();
    }

    public List<P2PTransportOrderResponse> getTransportOrderToGatheringPoint(Long gatheringPointId) {
        if (!gatheringPointRepository.existsById(gatheringPointId))
            throw new ResourceNotFoundException("Gathering Point");
        return p2PTransportOrderRepository.findByTo_Id(gatheringPointId).stream()
                .map((element) -> modelMapper.map(element, P2PTransportOrderResponse.class))
                .toList();
    }

    @Transactional
    public boolean confirmArrivalAtGathering(Long gatheringPointId, UUID p2pTransportOrderId) {
        if (!gatheringPointRepository.existsById(gatheringPointId))
            throw new ResourceNotFoundException("Gathering Point");
        P2PTransportOrder p2pTransportOrder = p2PTransportOrderRepository.findById(p2pTransportOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("P2P Transport Order"));

        for (ExpressOrder expressOrder : p2pTransportOrder.getExpressOrders()) {
            //update status and add tracking event
            TrackingEvent trackingEvent = TrackingEvent.builder()
                    .timestamp(LocalDateTime.now())
                    .location(p2pTransportOrder.getTo().getAddress())
                    .build();
            if (expressOrder.getStatus().equals(ExpressOrder.Status.TRANSPORTING_FROM_SRC_TRANSACTION)) {
                expressOrder.setStatus(ExpressOrder.Status.TRANSPORTED_TO_SRC_GATHERING);
                trackingEvent.setMessage(TrackingEvent.TRANSPORTED_TO_SRC_GATHERING);
            } else if (expressOrder.getStatus().equals(ExpressOrder.Status.TRANSPORTING_FROM_SRC_GATHERING)) {
                expressOrder.setStatus(ExpressOrder.Status.TRANSPORTED_TO_DES_GATHERING);
                trackingEvent.setMessage(TrackingEvent.TRANSPORTED_TO_DES_GATHERING);
            }
            expressOrder.getTrackingEvents().add(trackingEvent);
//            trackingEvent = trackingEventRepository.save(trackingEvent);
            expressOrder = expressOrderRepository.save(expressOrder);

            //add to inventory of destination
            p2pTransportOrder.getTo().getInventory().putIfAbsent(expressOrder.getId(), expressOrder);
        }
        //commit save inventory
        gatheringPointRepository.save((GatheringPoint) p2pTransportOrder.getTo());

        //update status or transport order to shipper
        p2pTransportOrder.setStatus(TransportOrder.Status.SHIPPED);
        p2PTransportOrderRepository.save(p2pTransportOrder);

        return true;
    }
}
