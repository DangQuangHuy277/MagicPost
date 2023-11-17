package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.actor.Customer;
import com.magicpost.app.magicPost.transport.TransportOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
public class ExpressOrder {
    @Id
    @GeneratedValue
    private Long id;
    //    @OneToOne
//    private Good good;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Customer receiver;

    private String specialService;

    @Enumerated(EnumType.STRING)
    private Instructor senderInstructor;

    private String senderCommitment;
    private LocalDateTime sendTime;
    private LocalDateTime receivedTime;
    private Price price;
    private CashOnDelivery cashOnDelivery;
    private String businessInstructions;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createTime;

    @ManyToMany(mappedBy = "expressOrders")
    private Set<TransportOrder> transportOrders = new LinkedHashSet<>();

    @OneToMany(/*mappedBy = "expressOrder",*/ orphanRemoval = true)
    @JoinColumn(name = "expressOrder", nullable = false)
//    @OrderBy("trackingEvent.timestamp")
    private List<TrackingEvent> trackingEvents = new ArrayList<>();

    enum Status {
        POSTED,
        PRINTED_DELIVERY_RECEIPT,
        TRANSPORTING_FROM_SRC_TRANSACTION,
        TRANSPORTED_TO_SRC_GATHERING,
        TRANSPORTING_FROM_SRC_GATHERING,
        TRANSPORTED_TO_DES_GATHERING,
        TRANSPORTING_FROM_DES_GATHERING,
        TRANSPORTED_TO_DES_TRANSACTION,
        SHIPPING,
        DELIVERED,
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
    public static class Price {
        private Double mainTax;
        private Double subTax;
        private Double transportTax;
        private Double totalTax;
        private Double otherTax;
        private Double totalPrice;
    }

    @Embeddable
    public static class CashOnDelivery {
        private Double cod;
        private Double other;
        private Double totalCOD;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ExpressOrder that = (ExpressOrder) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}


