package com.magicpost.app.magicPost.transport;

import com.magicpost.app.magicPost.transport.dto.*;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransportController {
    private final TransportService transportService;

    @GetMapping("/p2p-transport-orders/{p2p-transport-order-id}/express-orders")
    ResponseEntity<?> getExpressFromP2PTransportOrder(@PathVariable("p2p-transport-order-id") UUID p2pTransportOrderId) {
        Set<UUID> expressOrderIds = transportService.getExpressFromP2PTransportOrder(p2pTransportOrderId);
        return expressOrderIds.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(expressOrderIds);
    }

    @GetMapping("/p2c-transport-orders/{p2c-transport-order-id}/express-orders")
    ResponseEntity<?> getExpressFromP2CTransportOrder(@PathVariable("p2c-transport-order-id") UUID p2cTransportOrderId) {
        Set<UUID> expressOrderIds = transportService.getExpressFromP2CTransportOrder(p2cTransportOrderId);
        return expressOrderIds.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(expressOrderIds);
    }

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @GetMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders")
    ResponseEntity<?> getP2PTransportOrderToTransPoint(@PathVariable("transaction-point-id") Long transactionPointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getP2PTransportOrderToTransPoint(transactionPointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @PreAuthorize("hasRole('GATHERINGSTAFF') and @customAuthorization.belongsPoint(authentication,#gatheringPointId)")
    @GetMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders")
    ResponseEntity<?> getP2PTransportOrderToGatherPoint(@PathVariable("gathering-point-id") Long gatheringPointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getP2PTransportOrderToGatherPoint(gatheringPointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @PreAuthorize("hasAnyRole('TRANSACTIONSTAFF', 'GATHERINGSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @GetMapping("/points/{point-id}/p2p-transport-orders")
    ResponseEntity<?> getTransportOrderToThisPoint(@PathVariable("point-id") Long pointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getTransportOrderToThisPoint(pointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @GetMapping("/transaction-points/{transaction-point-id}/p2c-transport-orders")
    ResponseEntity<?> getP2CTransportOrderOfTransactionPoint(@PathVariable("transaction-point-id") Long transactionPointId) {
        List<P2CTransportOrderResponse> transportOrders = transportService.getP2CTransportOrderOfTransactionPoint(transactionPointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PostMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders")
    ResponseEntity<?> makeP2PTransportOrderFromSrcTrans(@PathVariable("transaction-point-id") Long transactionPointId,
                                                        @RequestBody P2PTransportOrderRequest transportOrderRequest) {
        P2PTransportOrderResponse newP2PTransportOrder = transportService.makeP2PTransportOrderFromSrcTrans(transactionPointId, transportOrderRequest);
        return ResponseEntity.ok(newP2PTransportOrder);
    }

    @PreAuthorize("hasRole('GATHERINGSTAFF') and @customAuthorization.belongsPoint(authentication,#gatheringPointId)")
    @PostMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders")
    ResponseEntity<?> makeTransportOrderFromGather(@PathVariable("gathering-point-id") Long gatheringPointId,
                                                   @RequestBody P2PTransportOrderRequest transportOrderRequest) {
        P2PTransportOrderResponse newP2PTransportOrder = transportService.makeTransportOrderFromGather(gatheringPointId, transportOrderRequest);
        return ResponseEntity.ok(newP2PTransportOrder);
    }

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PostMapping("/transaction-points/{transaction-point-id}/p2c-transport-orders")
    ResponseEntity<?> makeTransportOrderToCustomer(@PathVariable("transaction-point-id") Long transactionPointId,
                                                   @RequestBody P2CTransportOrderRequest p2CTransportOrderRequest) {
        TransportOrderResponse newP2CTransportOrder = transportService.makeTransportOrderToCustomer(transactionPointId, p2CTransportOrderRequest);
        return ResponseEntity.ok(newP2CTransportOrder);
    }

    @PreAuthorize("hasRole('GATHERINGSTAFF') and @customAuthorization.belongsPoint(authentication,#gatheringPointId)")
    @PutMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders/{p2p-transport-order-id}/confirm-arrival")
    ResponseEntity<?> confirmArrivalAtGathering(@PathVariable("gathering-point-id") Long gatheringPointId,
                                                @PathVariable("p2p-transport-order-id") UUID p2PTransportOrderId) {
        boolean isConfirmed = transportService.confirmP2PArrivalAtGathering(gatheringPointId, p2PTransportOrderId);
        String message = "Successful transported to Gathering Point";
        return isConfirmed ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }


    @PreAuthorize("hasRole('GATHERINGSTAFF') and @customAuthorization.belongsPoint(authentication,#gatheringPointId)")
    @PutMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders/confirm-arrival")
    ResponseEntity<?> confirmMultipleArrivalAtGathering(@PathVariable("gathering-point-id") Long gatheringPointId,
                                                        @RequestBody List<UUID> p2pTransportOrderIdList) {
        boolean isConfirmed = transportService.confirmMultipleArrivalAtGathering(gatheringPointId, p2pTransportOrderIdList);
        String message = "Successful transported to Gathering Point";
        return isConfirmed ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PutMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders/{p2p-transport-order-id}/confirm-arrival")
    ResponseEntity<?> confirmP2pArrivalAtTrans(@PathVariable("transaction-point-id") Long transactionPointId,
                                               @PathVariable("p2p-transport-order-id") UUID p2PTransportOrderId) {
        boolean isConfirmed = transportService.confirmP2PArrivalAtTrans(transactionPointId, p2PTransportOrderId);
        String message = "Successful transported to destination Transaction Point";
        return isConfirmed ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }
    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PutMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders/confirm-arrival")
    ResponseEntity<?> confirmMultipleArrivalAtTrans(@PathVariable("transaction-point-id") Long transactionPointId,
                                                    @RequestBody List<UUID> p2pTransportOrderIdList){
        boolean isConfirmed = transportService.confirmMultipleArrivalAtTrans(transactionPointId, p2pTransportOrderIdList);
        String message = "Successful transported to Transaction Point";
        return isConfirmed ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }


    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PutMapping("transaction-points/{transaction-point-id}/p2c-transport-orders/{p2c-transport-order-id}/express-orders/{express-order-id}/confirm-delivery")
    ResponseEntity<?> confirmDeliveryAExpressOrder(@PathVariable("transaction-point-id") Long transactionPointId,
                                                   @PathVariable("p2c-transport-order-id") UUID p2CTransportOrderId,
                                                   @PathVariable("express-order-id") UUID expressOrderId) {
        boolean isConfirmed = transportService.confirmDeliveryAExpressOrder(transactionPointId, p2CTransportOrderId, expressOrderId);
        String message = "Delivery successful";
        return isConfirmed ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PutMapping("transaction-points/{transaction-point-id}/p2c-transport-orders/{p2c-transport-order-id}/express-orders/{express-order-id}/cancel")
    ResponseEntity<?> cancelExpressOrderAtReceiver(@PathVariable("transaction-point-id") Long transactionPointId,
                                                   @PathVariable("p2c-transport-order-id") UUID p2CTransportOrderId,
                                                   @PathVariable("express-order-id") UUID expressOrderId) {
        boolean isCanceled = transportService.cancelExpressOrderAtReceiver(transactionPointId, p2CTransportOrderId, expressOrderId);
        String message = "Cancel successful";
        return isCanceled ? ResponseEntity.ok(Map.of("message", message)) : ResponseEntity.badRequest().build();
    }
}
