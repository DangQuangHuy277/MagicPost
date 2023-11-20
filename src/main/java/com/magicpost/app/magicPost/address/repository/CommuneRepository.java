package com.magicpost.app.magicPost.address.repository;

import com.magicpost.app.magicPost.address.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuneRepository extends JpaRepository<Commune, Long> {
}