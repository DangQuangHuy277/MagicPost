package com.magicpost.app.magicPost.transport.repository;

import com.magicpost.app.magicPost.transport.entity.P2PTransportOrder;

import java.util.List;

public interface P2PTransportOrderRepository extends TransportOrderRepository<P2PTransportOrder> {
    List<P2PTransportOrder> findByTo_Id(Long id);

}