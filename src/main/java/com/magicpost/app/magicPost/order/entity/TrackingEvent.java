package com.magicpost.app.magicPost.order.entity;

import com.magicpost.app.magicPost.address.entity.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TrackingEvent {
    public static final String POSTED = "Order has been posted";
    public static final String TRANSPORTING_FROM_SRC_TRANSACTION = "Transporting from source transaction point";
    public static final String TRANSPORTED_TO_SRC_GATHERING = "Transported to source gathering point";
    public static final String TRANSPORTING_FROM_SRC_GATHERING = "Transporting from source gathering point";
    public static final String TRANSPORTED_TO_DES_GATHERING = "Transported to destination gathering point";
    public static final String TRANSPORTING_FROM_DES_GATHERING = "Transporting from destination gathering point";
    public static final String TRANSPORTED_TO_DES_TRANSACTION = "Transported to destination transaction point";
    public static final String SHIPPING = "Order is currently shipping";
    public static final String DELIVERED = "Order has been delivered";
    public static final String CANCELING = "Order has been canceled and is being returned to the customer";
    public static final String CANCELED = "Order has been returned to source transaction point";
    public static final String RETURNED = "Order has been returned to the customer";

    private String message;
    private LocalDateTime timestamp;
    @Embedded
    private Address location;

//    @ManyToOne
//    private ExpressOrder expressOrder;

}
