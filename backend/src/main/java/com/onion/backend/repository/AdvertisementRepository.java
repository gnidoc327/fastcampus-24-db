package com.onion.backend.repository;

import com.onion.backend.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository  extends JpaRepository<Advertisement, Long> {
}
