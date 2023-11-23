package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.address.entity.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
public class TrackingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String message;
    private LocalDateTime timestamp;
    @Embedded
    private Address location;

    @ManyToOne
    @JoinColumn(name = "express_order_id")
    private ExpressOrder expressOrder;

}
