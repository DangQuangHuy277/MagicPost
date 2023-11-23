package com.magicpost.app.magicPost.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magicpost.app.magicPost.actor.Customer;
import com.magicpost.app.magicPost.actor.dto.CustomerDTO;
import com.magicpost.app.magicPost.order.ExpressOrder;
import com.magicpost.app.magicPost.order.Good;
import com.magicpost.app.magicPost.order.TrackingEvent;
import com.magicpost.app.magicPost.transport.TransportOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ExpressOrderResponse {
    private UUID id;
    private CustomerDTO sender;
    private CustomerDTO receiver;
    private ExpressOrder.Type type;
    private List<Good> goods;
    private String specialService;
    private ExpressOrder.Instructor senderInstructor;
    //    private String senderCommitment;
    private LocalDateTime sendTime;
    private ExpressOrder.Price price;
    private ExpressOrder.CashOnDelivery cashOnDelivery;
    private Double actualWeight;
    private Double volumetricWeight;
    private String businessInstructions;
    private LocalDateTime receivedTime;
    private ExpressOrder.Status status;
    private LocalDateTime createTime;
}
