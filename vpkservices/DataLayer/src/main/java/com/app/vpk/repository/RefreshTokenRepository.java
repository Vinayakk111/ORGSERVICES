package com.app.vpk.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.vpk.entity.RefreshToken;
import com.app.vpk.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByTokenHash(@Param("tokenHash") String tokenHash);

	

	void deleteByUser(@Param("user") User user);

//	void deleteByToken(@Param("tokenHash") String tokenHash);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select rt from RefreshToken rt join fetch rt.user where rt.tokenHash=:tokenHash")
	Optional<RefreshToken> findByTokenHashForUpdate(@Param("tokenHash") String tokenHash);

	@Modifying
	@Query("update RefreshToken rt set rt.revoked = true, rt.revokedAt = :revokedAt where rt.user.id = :userId and rt.revoked = false")
	int revokeAllByUserId(@Param("userId") Long userId, @Param("revokedAt") LocalDateTime revokedAt);

//	@Modifying
//	@Query("        delete from RefreshToken rt        where rt.expiresAt < :now    ")
//	int deleteExpiredTokens(@Param("now") LocalDateTime now);
}
