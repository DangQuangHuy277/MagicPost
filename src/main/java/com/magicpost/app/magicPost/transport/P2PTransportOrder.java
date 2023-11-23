package com.magicpost.app.magicPost.transport;

import com.magicpost.app.magicPost.point.entity.Point;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class P2PTransportOrder extends TransportOrder{
    @ManyToOne
    @JoinColumn(name = "to_point_id")
    private Point toPoint;
}
