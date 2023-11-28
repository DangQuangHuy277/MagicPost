package com.magicpost.app.magicPost.transport.dto;

import com.magicpost.app.magicPost.point.dto.PointResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class P2PTransportOrderResponse extends TransportOrderResponse{
    PointResponse to;
}
