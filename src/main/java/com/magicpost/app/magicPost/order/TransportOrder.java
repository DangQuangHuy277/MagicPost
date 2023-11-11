package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.point.Point;

import java.util.Date;
import java.util.Set;

public abstract class TransportOrder {
    private Long id;
    private Set<ExpressOrder> expressOrders;
    private Point from;
//    private Point to;
    private Date createdDate;
    private Status status;

    enum Status {
        WAITING,
        SHIPPING,
        SHIPPED
    }
}