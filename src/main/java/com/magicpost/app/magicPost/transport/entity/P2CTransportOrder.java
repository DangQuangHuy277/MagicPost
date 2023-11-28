package com.magicpost.app.magicPost.transport.entity;

import com.magicpost.app.magicPost.actor.Shipper;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class P2CTransportOrder extends TransportOrder {
    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;
}
