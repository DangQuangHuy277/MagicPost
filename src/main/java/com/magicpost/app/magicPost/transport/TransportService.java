package com.magicpost.app.magicPost.transport;

import com.magicpost.app.magicPost.actor.entity.Shipper;
import com.magicpost.app.magicPost.actor.repository.ShipperRepository;
import com.magicpost.app.magicPost.exception.InvalidBusinessConditionException;
import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.order.entity.ExpressOrder;
import com.magicpost.app.magicPost.order.entity.TrackingEvent;
import com.magicpost.app.magicPost.order.repository.ExpressOrderRepository;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import com.magicpost.app.magicPost.point.repository.GatheringPointRepository;
import com.magicpost.app.magicPost.point.repository.PointRepository;
import com.magicpost.app.magicPost.point.repository.TransactionPointRepository;
import com.magicpost.app.magicPost.transport.dto.*;
import com.magicpost.app.magicPost.transport.entity.P2CTransportOrder;
import com.magicpost.app.magicPost.transport.entity.P2PTransportOrder;
import com.magicpost.app.magicPost.transport.entity.TransportOrder;
import com.magicpost.app.magicPost.transport.repository.P2CTransportOrderRepository;
import com.magicpost.app.magicPost.transport.repository.P2PTransportOrderRepository;
import com.magicpost.app.magicPost.transport.repository.TransportOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final ExpressOrderRepository expressOrderRepository;
    private final ShipperRepository shipperRepository;
    private final P2CTransportOrderRepository p2CTransportOrderRepository;

    public List<P2PTransportOrderResponse> getTransportOrderToThisPoint(Long pointId) {
        if (!pointRepository.existsById(pointId))
            throw new ResourceNotFoundException("Point");
        return p2PTransportOrderRepository.findByTo_Id(pointId).stream()
                .map((element) -> modelMapper.map(element, P2PTransportOrderResponse.class))
                .toList();
    }

    public List<P2PTransportOrderResponse> getP2PTransportOrderToTransPoint(Long transactionPointId) {
        if (!transactionPointRepository.existsById(transactionPointId))
            throw new ResourceNotFoundException("Transaction Point");
        return p2PTransportOrderRepository.findByTo_Id(transactionPointId).stream()
                .map((element) -> modelMapper.map(element, P2PTransportOrderResponse.class))
                .toList();
    }

    public List<P2PTransportOrderResponse> getP2PTransportOrderToGatherPoint(Long gatheringPointId) {
        if (!gatheringPointRepository.existsById(gatheringPointId))
            throw new ResourceNotFoundException("Gathering Point");
        return p2PTransportOrderRepository.findByTo_Id(gatheringPointId).stream()
                .map((element) -> modelMapper.map(element, P2PTransportOrderResponse.class))
                .toList();
    }

    @Transactional
    public P2PTransportOrderResponse makeP2PTransportOrderFromSrcTrans(Long transactionPointId,
                                                                       P2PTransportOrderRequest transportOrderRequest) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction point"));
        GatheringPoint gatheringPoint = gatheringPointRepository.findById(transportOrderRequest.getDestinationPointId())
                .orElseThrow(() -> new ResourceNotFoundException("Gathering Point"));
        if (!transactionPoint.getGatheringPoint().equals(gatheringPoint))
            throw new InvalidBusinessConditionException("The destination Point is not the Gathering Point that manage this Transaction Point");
        P2PTransportOrder newP2PTransportOrder = makeP2pTransport(
                transactionPoint,
                gatheringPoint,
                transportOrderRequest);
        return modelMapper.map(transportOrderRepository.save(newP2PTransportOrder), P2PTransportOrderResponse.class);
    }

    @Transactional
    public P2PTransportOrderResponse makeTransportOrderFromGather(Long gatheringPointId,
                                                                  P2PTransportOrderRequest transportOrderRequest) {
        if (gatheringPointId.equals(transportOrderRequest.getDestinationPointId()))
            throw new InvalidBusinessConditionException("The Point can't not send Transport Order to itself");
        GatheringPoint gatheringPoint = gatheringPointRepository.findById(gatheringPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Gathering Point"));
        Point point = pointRepository.findById(transportOrderRequest.getDestinationPointId())
                .orElseThrow(() -> new ResourceNotFoundException("Point"));
        P2PTransportOrder p2PTransportOrder = switch (point) {
            case GatheringPoint gp -> makeP2pTransport(gatheringPoint, gp, transportOrderRequest);
            case TransactionPoint tp -> {
                if (!tp.getGatheringPoint().equals(gatheringPoint))
                    throw new InvalidBusinessConditionException("Can't send Transport Order that doesn't manage by this Gathering Point");
                yield makeP2pTransport(gatheringPoint, tp, transportOrderRequest);
            }
            default -> throw new IllegalStateException("Unexpected value: " + point);
        };
        return modelMapper.map(p2PTransportOrder, P2PTransportOrderResponse.class);
    }

    @Transactional
    private P2PTransportOrder makeP2pTransport(Point srcPoint,
                                               Point desPoint,
                                               P2PTransportOrderRequest transportOrderRequest) {
        // TODO: handle srcPoint same as desPoint

        // * update statistic of srcPoint
        srcPoint.incrementTotalSendOrders();

        // * create new transactionOrder
        P2PTransportOrder newP2PTransportOrder = new P2PTransportOrder();
        newP2PTransportOrder.setFrom(srcPoint);
        newP2PTransportOrder.setTo(desPoint);
        newP2PTransportOrder.setDepartureTime(LocalDateTime.now());
        newP2PTransportOrder.setStatus(TransportOrder.Status.SHIPPING);

        // * Update status and tracking event and move expressOrder from transactionPoint to P2PTransportOrder
        for (var expressId : transportOrderRequest.getExpressOrderIdList()) {
            //update status and add tracking event for expressOrder
            ExpressOrder expressOrder = srcPoint.getInventory().get(expressId);
            if (expressOrder == null)
                throw new ResourceNotFoundException("Express Order");
            TrackingEvent trackingEvent = TrackingEvent.builder()
                    .timestamp(LocalDateTime.now())
                    .location(srcPoint.getAddress())
                    .build();
            switch (expressOrder.getStatus()) {
                case POSTED -> {
                    trackingEvent.setMessage(TrackingEvent.TRANSPORTING_FROM_SRC_TRANSACTION);
                    expressOrder.setStatus(ExpressOrder.Status.TRANSPORTING_FROM_SRC_TRANSACTION);
                    expressOrder.getTrackingEvents().add(trackingEvent);
                }
                case TRANSPORTED_TO_SRC_GATHERING -> {
                    trackingEvent.setMessage(TrackingEvent.TRANSPORTING_FROM_SRC_GATHERING);
                    expressOrder.setStatus(ExpressOrder.Status.TRANSPORTING_FROM_SRC_GATHERING);
                    expressOrder.getTrackingEvents().add(trackingEvent);
                }
                case TRANSPORTED_TO_DES_GATHERING -> {
                    trackingEvent.setMessage(TrackingEvent.TRANSPORTING_FROM_DES_GATHERING);
                    expressOrder.setStatus(ExpressOrder.Status.TRANSPORTING_FROM_DES_GATHERING);
                    expressOrder.getTrackingEvents().add(trackingEvent);
                }
                case CANCELING -> {
                    //TODO: maybe handle another business logic of canceling
                }
            }
            expressOrder.setTransportOrder(newP2PTransportOrder);
//            trackingEvent = trackingEventRepository.save(trackingEvent);
            expressOrder = expressOrderRepository.save(expressOrder);

            // * Take expressOrder from transaction point inventory to transportOrder
            srcPoint.getInventory().remove(expressId);
            newP2PTransportOrder.getExpressOrders().put(expressId, expressOrder);
        }
        // * Commit save at transactionPoint and transportOrder
        pointRepository.save(srcPoint);
        return transportOrderRepository.save(newP2PTransportOrder);
    }

    @Transactional
    public TransportOrderResponse makeTransportOrderToCustomer(Long transactionPointId,
                                                               P2CTransportOrderRequest p2cTransportOrderRequest) {
        // ? Do we need to check transportOrder is to customer or to another Point
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));
        Shipper shipper = shipperRepository.findById(p2cTransportOrderRequest.getShipperId())
                .orElseThrow(() -> new ResourceNotFoundException("Shipper"));
        if (!shipper.getTransactionPoint().equals(transactionPoint))
            throw new InvalidBusinessConditionException("The shipper is not associated with this transaction point.");


        // * Create new P2CTransportOrder
        P2CTransportOrder newP2CTransportOrder = new P2CTransportOrder();
        newP2CTransportOrder.setFrom(transactionPoint);
        newP2CTransportOrder.setShipper(shipper);
        newP2CTransportOrder.setDepartureTime(LocalDateTime.now());
        newP2CTransportOrder.setStatus(TransportOrder.Status.SHIPPING);

        // * Update status and tracking event and move expressOrder from transactionPoint to P2CTransportOrder
        for (UUID expressOrderId : p2cTransportOrderRequest.getExpressOrderIdList()) {
            ExpressOrder expressOrder = transactionPoint.getInventory().get(expressOrderId);
            if (expressOrder == null)
                throw new ResourceNotFoundException("Express Order");
            expressOrder.setStatus(ExpressOrder.Status.SHIPPING);
            TrackingEvent trackingEvent = new TrackingEvent(TrackingEvent.SHIPPING, LocalDateTime.now(), transactionPoint.getAddress());
            expressOrder.getTrackingEvents().add(trackingEvent);
            expressOrder.setTransportOrder(newP2CTransportOrder);

            // ? Does ManyToMany relationship need to call save from two side
            expressOrderRepository.save(expressOrder);
            // * Take expressOrder from transaction point inventory to transportOrder
            transactionPoint.getInventory().remove(expressOrderId);
            newP2CTransportOrder.getExpressOrders().put(expressOrderId, expressOrder);
        }
        // * Commit save at transactionPoint and transportOrder
        pointRepository.save(transactionPoint);
        transportOrderRepository.save(newP2CTransportOrder);
        return modelMapper.map(newP2CTransportOrder, P2CTransportOrderResponse.class);
    }

    @Transactional
    public boolean confirmP2PArrivalAtGathering(Long gatheringPointId, UUID p2PTransportOrderId) {
        if (!gatheringPointRepository.existsById(gatheringPointId))
            throw new ResourceNotFoundException("Gathering Point");
        P2PTransportOrder p2pTransportOrder = p2PTransportOrderRepository.findById(p2PTransportOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("P2P Transport Order"));
        if (!p2pTransportOrder.getTo().getId().equals(gatheringPointId))
            throw new InvalidBusinessConditionException("The TransportOrder is not match with destination Gathering Point");

        return confirmP2PArrivalAtPoint(p2pTransportOrder);
    }

    @Transactional
    public boolean confirmP2PArrivalAtTrans(Long transactionPointId, UUID p2PTransportOrderId) {
        if (!transactionPointRepository.existsById(transactionPointId))
            throw new ResourceNotFoundException("Gathering Point");
        P2PTransportOrder p2pTransportOrder = p2PTransportOrderRepository.findById(p2PTransportOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("P2P Transport Order"));
        if (!p2pTransportOrder.getTo().getId().equals(transactionPointId))
            throw new InvalidBusinessConditionException("The TransportOrder is not match with destination Gathering Point");

        return confirmP2PArrivalAtPoint(p2pTransportOrder);
    }

    @Transactional
    private boolean confirmP2PArrivalAtPoint(P2PTransportOrder p2pTransportOrder) {
        // * Update statistic for destination ponint
        p2pTransportOrder.getTo().incrementTotalReceiveOrders();

        for (ExpressOrder expressOrder : p2pTransportOrder.getExpressOrders().values()) {
            // * Update status and add tracking event
            TrackingEvent trackingEvent = TrackingEvent.builder()
                    .timestamp(LocalDateTime.now())
                    .location(p2pTransportOrder.getTo().getAddress())
                    .build();
            switch (expressOrder.getStatus()) {
                case ExpressOrder.Status.TRANSPORTING_FROM_SRC_TRANSACTION -> {
                    expressOrder.setStatus(ExpressOrder.Status.TRANSPORTED_TO_SRC_GATHERING);
                    trackingEvent.setMessage(TrackingEvent.TRANSPORTED_TO_SRC_GATHERING);
                }
                case ExpressOrder.Status.TRANSPORTING_FROM_DES_GATHERING -> {
                    expressOrder.setStatus(ExpressOrder.Status.TRANSPORTED_TO_DES_TRANSACTION);
                    trackingEvent.setMessage(TrackingEvent.TRANSPORTED_TO_DES_TRANSACTION);
                }
                case ExpressOrder.Status.TRANSPORTING_FROM_SRC_GATHERING -> {
                    switch (p2pTransportOrder.getTo()) {
                        case GatheringPoint gp -> {
                            expressOrder.setStatus(ExpressOrder.Status.TRANSPORTED_TO_DES_GATHERING);
                            trackingEvent.setMessage(TrackingEvent.TRANSPORTED_TO_DES_GATHERING);
                        }
                        case TransactionPoint tp -> {
                            expressOrder.setStatus(ExpressOrder.Status.TRANSPORTED_TO_DES_TRANSACTION);
                            trackingEvent.setMessage(TrackingEvent.TRANSPORTED_TO_DES_TRANSACTION);
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + p2pTransportOrder.getTo());
                    }
                }
                case ExpressOrder.Status.CANCELING -> {
                    //TODO: Need a way to check source transactionPoint of this return
                    if (!p2pTransportOrder.getTo().equals(expressOrder.getSourcePoint())) break;
                    expressOrder.setStatus(ExpressOrder.Status.CANCELED);
                    trackingEvent.setMessage(TrackingEvent.CANCELED);
                }
            }

            expressOrder.getTrackingEvents().add(trackingEvent);
//            trackingEvent = trackingEventRepository.save(trackingEvent);
            if (!expressOrder.getStatus().equals(ExpressOrder.Status.CANCELED))
                expressOrder = expressOrderRepository.save(expressOrder);

            // * Add to Inventory of destination
            p2pTransportOrder.getTo().getInventory().putIfAbsent(expressOrder.getId(), expressOrder);
        }
        // * Commit save inventory
        pointRepository.save(p2pTransportOrder.getTo());

        // * Update transportOrder information
        p2pTransportOrder.setStatus(TransportOrder.Status.SHIPPED);
        p2pTransportOrder.setArrivalTime(LocalDateTime.now());
        p2PTransportOrderRepository.save(p2pTransportOrder);

        return true;
    }

    @Transactional
    public boolean confirmDeliveryAExpressOrder(Long transactionPointId, UUID p2CTransportOrderId, UUID expressOrderId) {
        return handleReceiveExpressOrder(transactionPointId, p2CTransportOrderId, expressOrderId, ExpressOrder.Status.DELIVERED);
    }

    @Transactional
    public boolean cancelExpressOrderAtReceiver(Long transactionPointId, UUID p2CTransportOrderId, UUID expressOrderId) {
        return handleReceiveExpressOrder(transactionPointId, p2CTransportOrderId, expressOrderId, ExpressOrder.Status.CANCELING);
    }

    @Transactional
    private boolean handleReceiveExpressOrder(Long transactionPointId,
                                              UUID p2CTransportOrderId,
                                              UUID expressOrderId,
                                              ExpressOrder.Status newStatus) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));
        P2CTransportOrder p2CTransportOrder = p2CTransportOrderRepository.findById(p2CTransportOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Transport Order to Customer"));
        if (!p2CTransportOrder.getFrom().equals(transactionPoint))
            throw new InvalidBusinessConditionException("The Transport Order is not from this Transaction Point");
        ExpressOrder expressOrder = p2CTransportOrder.getExpressOrders().get(expressOrderId);
        if (expressOrder == null)
            throw new InvalidBusinessConditionException("The Express Order is not exist in this Transport Order");

        // * Update statistic of transactionPoint
        switch (newStatus) {
            case CANCELING -> transactionPoint.incrementCancelOrders();
            case DELIVERED -> transactionPoint.incrementSuccessOrders();
        }


        // * Update info and add tracking event for the Express Order
        expressOrder.setReceivedTime(LocalDateTime.now());
        TrackingEvent trackingEvent = TrackingEvent.builder()
                .location(expressOrder.getReceiver().getAddress())
                .timestamp(LocalDateTime.now())
                .build();
        expressOrder.setStatus(newStatus);
        trackingEvent.setMessage(switch (newStatus) {
            case DELIVERED -> TrackingEvent.DELIVERED;
            case CANCELING -> TrackingEvent.CANCELING;
            default -> throw new IllegalStateException("Unexpected value: " + newStatus);
        });
        expressOrder.getTrackingEvents().add(trackingEvent);
        expressOrderRepository.save(expressOrder);

        // * Remove this ExpressOrder from Transport Order
        p2CTransportOrder.getExpressOrders().remove(expressOrderId);
        // * Update status of Transport Order if needed
        if (p2CTransportOrder.getExpressOrders().isEmpty()) {
            p2CTransportOrder.setStatus(TransportOrder.Status.SHIPPED);
            p2CTransportOrder.setArrivalTime(LocalDateTime.now());
        }
        return true;
    }
}
