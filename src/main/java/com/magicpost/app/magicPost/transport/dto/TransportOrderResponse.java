package com.magicpost.app.magicPost.transport.dto;

import com.magicpost.app.magicPost.order.dto.ExpressOrderResponse;
import com.magicpost.app.magicPost.order.entity.ExpressOrder;
import com.magicpost.app.magicPost.point.dto.PointResponse;
import com.magicpost.app.magicPost.point.entity.Point;
import com.magicpost.app.magicPost.transport.entity.TransportOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TransportOrderResponse {
    private UUID id;
    private PointResponse from;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private TransportOrder.Status status;
    private List<ExpressOrderResponse> expressOrders;
}
