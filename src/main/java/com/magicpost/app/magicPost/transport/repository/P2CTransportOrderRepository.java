package com.magicpost.app.magicPost.transport.repository;

import com.magicpost.app.magicPost.transport.entity.P2CTransportOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface P2CTransportOrderRepository extends TransportOrderRepository<P2CTransportOrder>{
}