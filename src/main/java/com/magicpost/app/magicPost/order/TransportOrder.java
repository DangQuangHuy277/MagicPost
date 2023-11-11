package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.event.TrackingEvent;
import com.magicpost.app.magicPost.point.Point;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
public abstract class TransportOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    protected Point from;
//    private Point to;
    protected LocalDateTime createdDate;
    protected Status status;

    @ManyToMany
    @JoinTable(name = "TransportOrder_expressOrders",
            joinColumns = @JoinColumn(name = "transportOrder_id"),
            inverseJoinColumns = @JoinColumn(name = "expressOrders_id"))
    protected Set<ExpressOrder> expressOrders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "transportOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrackingEvent> trackingEvents = new LinkedHashSet<>();

    public enum Status {
        WAITING,
        SHIPPING,
        SHIPPED
    }
}