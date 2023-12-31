package com.magicpost.app.magicPost.transport.repository;

import com.magicpost.app.magicPost.transport.entity.P2PTransportOrder;
import com.magicpost.app.magicPost.transport.entity.TransportOrder;

import java.util.List;

public interface P2PTransportOrderRepository extends TransportOrderRepository<P2PTransportOrder> {
    List<P2PTransportOrder> findByTo_Id(Long id);

    List<P2PTransportOrder> findByTo_IdAndStatus(Long id, TransportOrder.Status status);
}