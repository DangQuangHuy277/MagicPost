package com.magicpost.app.magicPost.transport.dto;

import com.magicpost.app.magicPost.actor.entity.Shipper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class P2CTransportOrderResponse extends TransportOrderResponse{
    Shipper shipper;
}
