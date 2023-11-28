package com.magicpost.app.magicPost.order.repository;

import com.magicpost.app.magicPost.order.entity.ExpressOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpressOrderRepository extends JpaRepository<ExpressOrder, UUID> {
}