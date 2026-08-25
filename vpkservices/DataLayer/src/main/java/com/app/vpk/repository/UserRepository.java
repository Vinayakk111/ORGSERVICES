package com.app.vpk.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.vpk.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph(attributePaths = { "roles", "roles.permissions" })
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	@Modifying
	@Query("update User u set u.lastLoginAt = :lastLoginAt where u.id = :userId")
	int updateLastLogin(@Param("userId") Long userId, @Param("lastLoginAt") LocalDateTime lastLoginAt);
	
	@Modifying
	@Query("update User u set u.failedLoginAttempts = :failedLoginAttempts where u.username = :username")
	int updateFailedLoginAttempt(@Param("username") String username,@Param("failedLoginAttempts") int failedLoginAttempts);
	
	@Modifying
	@Query("update User u set u.accountNonLocked = :accountNonLocked,u.lockedAt = CURRENT_TIMESTAMP where u.username = :username")
	int updateUserLockdStatus(@Param("accountNonLocked") Boolean accountNonLocked,@Param("username") String username);
}