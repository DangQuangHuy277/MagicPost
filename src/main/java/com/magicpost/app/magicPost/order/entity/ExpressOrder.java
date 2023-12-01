package com.magicpost.app.magicPost.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magicpost.app.magicPost.actor.entity.Customer;
import com.magicpost.app.magicPost.order.Good;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import com.magicpost.app.magicPost.transport.entity.TransportOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExpressOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Customer receiver;

    @Enumerated(EnumType.STRING)
    private Type type;


    @ElementCollection
    @CollectionTable(name = "Good", joinColumns = @JoinColumn(name = "express_order_id"))
    private List<Good> goods = new ArrayList<>();

    private String specialService;

    @Enumerated(EnumType.STRING)
    private Instructor senderInstructor;

    //    private String senderCommitment;
    private LocalDateTime sendTime;

    private Price price;

    private CashOnDelivery cashOnDelivery;

    private Double actualWeight;

    private Double volumetricWeight;

    private String businessInstructions;

    private LocalDateTime receivedTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.POSTED;

    @ManyToOne
    @JoinTable(
            name = "express_orders_transport_order",
            joinColumns = @JoinColumn(name = "express_orders_id"),
            inverseJoinColumns = @JoinColumn(name = "transport_order_id")
    )
    @JsonIgnore
    private TransportOrder transportOrder;

    @ElementCollection
    @CollectionTable(name = "tracking_event", joinColumns = @JoinColumn(name = "express_order_id"))
    private List<TrackingEvent> trackingEvents = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "source_point_id")
    @JsonIgnore
    private TransactionPoint sourcePoint;

    public enum Type {
        DOCUMENT,
        GOOD
    }

    public enum Status {
        POSTED,
        TRANSPORTING_FROM_SRC_TRANSACTION,
        TRANSPORTED_TO_SRC_GATHERING,
        TRANSPORTING_FROM_SRC_GATHERING,
        TRANSPORTED_TO_DES_GATHERING,
        TRANSPORTING_FROM_DES_GATHERING,
        TRANSPORTED_TO_DES_TRANSACTION,
        SHIPPING,
        DELIVERED,
        CANCELING,
        CANCELED,
        RETURNED
    }

    public enum Instructor {
        IMMEDIATE_RETURN,
        CALL_SENDER,
        CANCEL,
        RETURN_BEFORE_DATE,
        RETURN_WHEN_STORAGE_EXPIRES
    }

    @Embeddable
    @Getter
    @Setter
    public static class Price {
        private Double mainTax;
        private Double subTax;
        private Double transportTax;
        private Double totalTax;
        private Double otherTax;
        private Double totalPrice;
    }

    @Embeddable
    @Getter
    @Setter
    public static class CashOnDelivery {
        private Double cod;
        private Double other;
        private Double totalCOD;
    }
}


