package com.magicpost.app.magicPost.point.repository;


import com.magicpost.app.magicPost.point.entity.Point;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository<T extends Point> extends JpaRepository<T, Long> {
    List<T> findAll();
    boolean existsByName(@NotNull String name);
}