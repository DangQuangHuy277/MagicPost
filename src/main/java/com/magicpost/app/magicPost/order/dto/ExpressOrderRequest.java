package com.magicpost.app.magicPost.order.dto;

import com.magicpost.app.magicPost.actor.Customer;
import com.magicpost.app.magicPost.actor.dto.CustomerDTO;
import com.magicpost.app.magicPost.order.ExpressOrder;
import com.magicpost.app.magicPost.order.Good;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

import java.util.List;


@Getter
public class ExpressOrderRequest {
    @NotNull
    private CustomerDTO sender;
    @NotNull

    private CustomerDTO receiver;

    @NotNull
    private ExpressOrder.Type type;

    private List<Good> goods;

    private String specialService;

    private ExpressOrder.Instructor senderInstructor;

    @NotNull
    private LocalDateTime sendTime;

    @NotNull
    private ExpressOrder.Price price;

    private ExpressOrder.CashOnDelivery cashOnDelivery;

    private Double actualWeight;

    private Double volumetricWeight;

    private String businessInstructions;
}
