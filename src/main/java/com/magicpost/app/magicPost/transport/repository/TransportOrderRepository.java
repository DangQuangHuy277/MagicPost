package com.magicpost.app.magicPost.transport.repository;

import com.magicpost.app.magicPost.transport.entity.TransportOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportOrderRepository<T extends TransportOrder> extends JpaRepository<T, UUID> {

}