package com.magicpost.app.magicPost.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpressOrderRepository extends JpaRepository<ExpressOrder, UUID> {
}