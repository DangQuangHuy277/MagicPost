package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.order.entity.ExpressOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.UUID;

public interface ExpressOrderRepository extends JpaRepository<ExpressOrder, UUID> {
    @Query("select count(e) from ExpressOrder e where e.status in ?1")
    long countByStatusIn(Collection<ExpressOrder.Status> statuses);

    @Query("select count(e) from ExpressOrder e where e.status = ?1")
    long countByStatus(ExpressOrder.Status status);

}