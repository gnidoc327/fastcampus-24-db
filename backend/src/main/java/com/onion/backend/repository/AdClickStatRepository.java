package com.onion.backend.repository;

import com.onion.backend.entity.AdClickStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdClickStatRepository extends JpaRepository<AdClickStat, Long> {
}
