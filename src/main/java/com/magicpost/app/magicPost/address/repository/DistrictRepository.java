package com.magicpost.app.magicPost.address.repository;

import com.magicpost.app.magicPost.address.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
}