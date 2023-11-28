package com.magicpost.app.magicPost.order.entity;

import com.magicpost.app.magicPost.address.entity.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TrackingEvent {
    public static final String POSTED = "Order has been posted";
    public static final String TRANSPORTING_FROM_SRC_TRANSACTION = "Transporting from source transaction";
    public static final String TRANSPORTED_TO_SRC_GATHERING = "Transported to source gathering";
    public static final String TRANSPORTING_FROM_SRC_GATHERING = "Transporting from source gathering";
    public static final String TRANSPORTED_TO_DES_GATHERING = "Transported to destination gathering";
    public static final String TRANSPORTING_FROM_DES_GATHERING = "Transporting from destination gathering";
    public static final String TRANSPORTED_TO_DES_TRANSACTION = "Transported to destination transaction";
    public static final String SHIPPING = "Order is currently shipping";
    public static final String DELIVERED = "Order has been delivered";
    public static final String CANCELED = "Order has been canceled";
    public static final String RETURNED = "Order has been returned";

    private String message;
    private LocalDateTime timestamp;
    @Embedded
    private Address location;

//    @ManyToOne
//    private ExpressOrder expressOrder;

}
