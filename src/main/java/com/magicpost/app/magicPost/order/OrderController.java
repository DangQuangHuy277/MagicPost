package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.order.dto.ExpressOrderRequest;
import com.magicpost.app.magicPost.order.dto.ExpressOrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/transaction-points/{transaction-point-id}/express-orders")
    ResponseEntity<?> createNewExpressOrder(@PathVariable("transaction-point-id") Long transactionPointId,
            @Valid @RequestBody ExpressOrderRequest expressOrderRequest) {
        ExpressOrderResponse returnOrder = orderService.createNewExpressOrder(transactionPointId ,expressOrderRequest);
        return ResponseEntity.created(URI.create("/api/v1/express-orders/" + returnOrder.getId()))
                .body(returnOrder);
    }
}
