package com.onion.backend.repository;

import com.onion.backend.entity.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    Optional<JwtBlacklist> findByToken(String token);

    @Query(value = "SELECT * FROM jwt_blacklist WHERE username = :username ORDER BY expiration_time LIMIT 1", nativeQuery = true)
    Optional<JwtBlacklist> findTopByUsernameOrderByExpirationTime(@Param("username") String username);
}
