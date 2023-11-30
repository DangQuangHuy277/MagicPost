package com.magicpost.app.magicPost.transport.entity;

import com.magicpost.app.magicPost.order.entity.ExpressOrder;
import com.magicpost.app.magicPost.point.entity.Point;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
public abstract class TransportOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @ManyToOne
    @JoinColumn(name = "from_point_id")
    private Point from;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @ManyToMany
//    @JoinTable(name = "express_orders_transport_order",
//            joinColumns = @JoinColumn(name = "transport_order_id"),
//            inverseJoinColumns = @JoinColumn(name = "express_orders_id"))
//    private List<ExpressOrder> expressOrders = new ArrayList<>();

    @OneToMany(mappedBy = "transportOrder")
    @MapKey(name = "id")
    private Map<UUID, ExpressOrder> expressOrders = new HashMap<>();

    public enum Status {
//        WAITING,
        SHIPPING,
        SHIPPED,
        CANCELED
    }
}