package com.magicpost.app.magicPost.transport;

import com.magicpost.app.magicPost.transport.dto.P2PTransportOrderRequest;
import com.magicpost.app.magicPost.transport.dto.P2PTransportOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransportController {
    private final TransportService transportService;

    @PostMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders")
    ResponseEntity<?> makeTransportOrderFromSrcTrans(@PathVariable("transaction-point-id") Long transactionPointId,
                                                     @RequestBody P2PTransportOrderRequest transportOrderRequest) {
        P2PTransportOrderResponse newTransportOrder = transportService.makeTransportOrderFromSrcTrans(transactionPointId, transportOrderRequest);
        return ResponseEntity.ok(newTransportOrder);
    }

    @GetMapping("/transaction-points/{transaction-point-id}/p2p-transport-orders")
    ResponseEntity<?> getTransportOrderToTransactionPoint(@PathVariable("transaction-point-id") Long transactionPointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getTransportOrderToTransactionPoint(transactionPointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @GetMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders")
    ResponseEntity<?> getTransportOrderToGatheringPoint(@PathVariable("gathering-point-id") Long gatheringPointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getTransportOrderToGatheringPoint(gatheringPointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @GetMapping("/points/{point-id}/p2p-transport-orders")
    ResponseEntity<?> getTransportOrderToThisPoint(@PathVariable("point-id") Long pointId) {
        List<P2PTransportOrderResponse> transportOrders = transportService.getTransportOrderToThisPoint(pointId);
        return transportOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transportOrders);
    }

    @PutMapping("/gathering-points/{gathering-point-id}/p2p-transport-orders/{transport-order-id}/confirm-arrival")
    ResponseEntity<?> confirmArrivalAtGathering(@PathVariable("gathering-point-id") Long gatheringPointId,
                                                @PathVariable("transport-order-id") UUID p2pTransportOrderId) {
        boolean isConfirmed = transportService.confirmArrivalAtGathering(gatheringPointId, p2pTransportOrderId);
        return isConfirmed ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
