package com.magicpost.app.magicPost.actor.repository;

import com.magicpost.app.magicPost.actor.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShipperRepository extends JpaRepository<Shipper, UUID> {
    List<Shipper> findByTransactionPoint_Id(Long id);
}