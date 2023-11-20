package com.magicpost.app.magicPost.point;

import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PointRepository<T extends Point> extends JpaRepository<T, Long> {
    @Query("SELECT g FROM GatheringPoint g")
    List<GatheringPoint> findAllGatheringPoint();

    @Query("SELECT g FROM GatheringPoint g WHERE g.id = :id")
    Optional<GatheringPoint> findGatheringPointById(@Param("id") Long id);

    @Query("SELECT t FROM TransactionPoint t")
    List<TransactionPoint> findAllTransactionPoint();

    @Query("SELECT t FROM TransactionPoint t WHERE t.id = :id")
    Optional<TransactionPoint> findTransactionPointById(@Param("id") Long id);

    boolean existsByName(@NotNull String name);
}