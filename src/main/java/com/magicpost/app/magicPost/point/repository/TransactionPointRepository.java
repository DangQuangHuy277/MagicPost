package com.magicpost.app.magicPost.point.repository;

import com.magicpost.app.magicPost.point.entity.TransactionPoint;

import java.util.List;

public interface TransactionPointRepository extends PointRepository<TransactionPoint> {
    List<TransactionPoint> findByGatheringPoint_Id(Long id);
}
