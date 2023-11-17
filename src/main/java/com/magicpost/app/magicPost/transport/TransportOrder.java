package com.magicpost.app.magicPost.transport;

import com.magicpost.app.magicPost.order.ExpressOrder;
import com.magicpost.app.magicPost.point.Point;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TransportOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "from_point_id")
    private Point from;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(name = "express_orders_transport_order",
            joinColumns = @JoinColumn(name = "transport_order_id"),
            inverseJoinColumns = @JoinColumn(name = "express_orders_id"))
    private Set<ExpressOrder> expressOrders = new LinkedHashSet<>();

    public enum Status {
//        WAITING,
        SHIPPING,
        SHIPPED,
        CANCELED
    }
}