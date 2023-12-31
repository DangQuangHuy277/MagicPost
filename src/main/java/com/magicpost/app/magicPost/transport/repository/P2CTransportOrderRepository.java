package com.magicpost.app.magicPost.transport.repository;

import com.magicpost.app.magicPost.transport.entity.P2CTransportOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface P2CTransportOrderRepository extends TransportOrderRepository<P2CTransportOrder>{
    List<P2CTransportOrder> findByFrom_Id(Long id);
}