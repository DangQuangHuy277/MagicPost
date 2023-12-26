package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.order.dto.ExpressOrderRequest;
import com.magicpost.app.magicPost.order.dto.ExpressOrderResponse;
import com.magicpost.app.magicPost.order.dto.ExpressOrderStatisticalResponse;
import com.magicpost.app.magicPost.order.dto.TrackingEventResponse;
import com.magicpost.app.magicPost.order.entity.TrackingEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/express-orders/{express-order-id}")
    ResponseEntity<?> getExpressOrderDetail(@PathVariable("express-order-id") UUID expressOrderId){
        ExpressOrderResponse expressOrderResponse = orderService.getExpressOrderDetail(expressOrderId);
        return ResponseEntity.ok(expressOrderResponse);
    }

    @GetMapping("/express-orders/{express-order-id}/tracking-events")
    ResponseEntity<?> getTrackingEventOfOrder(@PathVariable("express-order-id")UUID expressOrderId){
        List<TrackingEventResponse> trackingEvents = orderService.getTrackingEventOfOrder(expressOrderId);
        return trackingEvents.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(trackingEvents);
    }

    @PostMapping("/transaction-points/{transaction-point-id}/express-orders")
    ResponseEntity<?> createNewExpressOrder(@PathVariable("transaction-point-id") Long transactionPointId,
            @Valid @RequestBody ExpressOrderRequest expressOrderRequest) {
        ExpressOrderResponse returnOrder = orderService.createNewExpressOrder(transactionPointId ,expressOrderRequest);
        return ResponseEntity.created(URI.create("/api/v1/express-orders/" + returnOrder.getId()))
                .body(returnOrder);
    }

    @GetMapping("/express-orders/statistic")
    ResponseEntity<?> getStatisticOfAllExpressOrder(){
        ExpressOrderStatisticalResponse statisticalResponse = orderService.getStatisticOfAllExpressOrder();
        return ResponseEntity.ok(statisticalResponse);
    }
}
