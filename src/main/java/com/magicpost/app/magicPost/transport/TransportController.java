package com.magicpost.app.magicPost.transport;

import com.magicpost.app.magicPost.transport.dto.P2CTransportOrderRequest;
import com.magicpost.app.magicPost.transport.dto.P2PTransportOrderRequest;
import com.magicpost.app.magicPost.transport.dto.P2PTransportOrderResponse;
import com.magicpost.app.magicPost.transport.dto.TransportOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransportController {
    private final TransportService transportService;

    @GetMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders")
    ResponseEntity<?> getP2PTransportOrderToTransPoint(@PathVariable("transaction-point-id") Long transactionPointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getP2PTransportOrderToTransPoint(transactionPointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @GetMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders")
    ResponseEntity<?> getP2PTransportOrderToGatherPoint(@PathVariable("gathering-point-id") Long gatheringPointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getP2PTransportOrderToGatherPoint(gatheringPointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @GetMapping("/points/{point-id}/p2p-transport-orders")
    ResponseEntity<?> getTransportOrderToThisPoint(@PathVariable("point-id") Long pointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getTransportOrderToThisPoint(pointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @GetMapping("/transaction-points/{transaction-point-id}/p2c-transport-orders")
    ResponseEntity<?> getP2CTransportOrderToTransactionPoint(@PathVariable("transaction-point-id") Long transactionPointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getP2PTransportOrderToTransPoint(transactionPointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @PostMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders")
    ResponseEntity<?> makeP2PTransportOrderFromSrcTrans(@PathVariable("transaction-point-id") Long transactionPointId,
                                                        @RequestBody P2PTransportOrderRequest transportOrderRequest) {
        P2PTransportOrderResponse newP2PTransportOrder = transportService.makeP2PTransportOrderFromSrcTrans(transactionPointId, transportOrderRequest);
        return ResponseEntity.ok(newP2PTransportOrder);
    }

    @PostMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders")
    ResponseEntity<?> makeTransportOrderFromGather(@PathVariable("gathering-point-id") Long gatheringPointId,
                                                   @RequestBody P2PTransportOrderRequest transportOrderRequest) {
        P2PTransportOrderResponse newP2PTransportOrder = transportService.makeTransportOrderFromGather(gatheringPointId, transportOrderRequest);
        return ResponseEntity.ok(newP2PTransportOrder);
    }

    @PostMapping("/transaction-points/{transaction-point-id}/p2c-transport-orders")
    ResponseEntity<?> makeTransportOrderToCustomer(@PathVariable("transaction-point-id") Long transactionPointId,
                                                   @RequestBody P2CTransportOrderRequest p2CTransportOrderRequest) {
        TransportOrderResponse newP2CTransportOrder = transportService.makeTransportOrderToCustomer(transactionPointId, p2CTransportOrderRequest);
        return ResponseEntity.ok(newP2CTransportOrder);
    }

    @PutMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders/{p2p-transport-order-id}/confirm-arrival")
    ResponseEntity<?> confirmArrivalAtGathering(@PathVariable("gathering-point-id") Long gatheringPointId,
                                                @PathVariable("p2p-transport-order-id") UUID p2PTransportOrderId) {
        boolean isConfirmed = transportService.confirmP2PArrivalAtGathering(gatheringPointId, p2PTransportOrderId);
        String message = "Successful transported to Gathering Point";
        return isConfirmed ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders/{p2p-transport-order-id}/confirm-arrival")
    ResponseEntity<?> confirmP2pArrivalAtTrans(@PathVariable("transaction-point-id") Long transactionPointId,
                                               @PathVariable("p2p-transport-order-id") UUID p2PTransportOrderId) {
        boolean isConfirmed = transportService.confirmP2PArrivalAtTrans(transactionPointId, p2PTransportOrderId);
        String message = "Successful transported to destination Transaction Point";
        return isConfirmed ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }

    @PutMapping("transaction-points/{transaction-point-id}/p2c-transport-orders/{p2c-transport-order-id}/express-orders/{express-order-id}/confirm-delivery")
    ResponseEntity<?> confirmDeliveryAExpressOrder(@PathVariable("transaction-point-id") Long transactionPointId,
                                                   @PathVariable("p2c-transport-order-id") UUID p2CTransportOrderId,
                                                   @PathVariable("express-order-id") UUID expressOrderId) {
        boolean isConfirmed = transportService.confirmDeliveryAExpressOrder(transactionPointId, p2CTransportOrderId, expressOrderId);
        String message = "Delivery successful";
        return isConfirmed ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }

    @PutMapping("transaction-points/{transaction-point-id}/p2c-transport-orders/{p2c-transport-order-id}/express-orders/{express-order-id}/cancel")
    ResponseEntity<?> cancelExpressOrderAtReceiver(@PathVariable("transaction-point-id") Long transactionPointId,
                                                   @PathVariable("p2c-transport-order-id") UUID p2CTransportOrderId,
                                                   @PathVariable("express-order-id") UUID expressOrderId){
        boolean isCanceled = transportService.cancelExpressOrderAtReceiver(transactionPointId, p2CTransportOrderId, expressOrderId);
        String message = "Cancel successful";
        return isCanceled ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }
}
