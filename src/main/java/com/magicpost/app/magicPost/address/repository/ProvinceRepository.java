package com.magicpost.app.magicPost.address.repository;

import com.magicpost.app.magicPost.address.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByName(String name);
}