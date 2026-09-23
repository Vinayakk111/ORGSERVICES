package com.app.vpk.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens", indexes = {
		@Index(name = "idx_refresh_token_hash", columnList = "token_hash", unique = true),
		@Index(name = "idx_refresh_token_user", columnList = "user_id") })
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * SHA-256 hash of actual refresh token.
	 *
	 * Never store the raw token.
	 */
	@Column(name = "token_hash", nullable = false, unique = true, length = 64)
	private String tokenHash;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;

	@Column(nullable = false)
	private boolean revoked;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "revoked_at")
	private LocalDateTime revokedAt;

	/**
	 * Optional but highly recommended.
	 *
	 * Allows refresh-token reuse detection.
	 */
	@Column(name = "replaced_by_hash", length = 64)
	private String replacedByHash;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTokenHash() {
		return tokenHash;
	}

	public void setTokenHash(String tokenHash) {
		this.tokenHash = tokenHash;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getRevokedAt() {
		return revokedAt;
	}

	public void setRevokedAt(LocalDateTime revokedAt) {
		this.revokedAt = revokedAt;
	}

	public String getReplacedByHash() {
		return replacedByHash;
	}

	public void setReplacedByHash(String replacedByHash) {
		this.replacedByHash = replacedByHash;
	}

	public RefreshToken(String tokenHash, User user, LocalDateTime expiresAt, boolean revoked,
			LocalDateTime createdAt, LocalDateTime revokedAt, String replacedByHash) {
		super();
		this.tokenHash = tokenHash;
		this.user = user;
		this.expiresAt = expiresAt;
		this.revoked = revoked;
		this.createdAt = createdAt;
		this.revokedAt = revokedAt;
		this.replacedByHash = replacedByHash;
	}
	
	

	public RefreshToken(String tokenHash, User user, LocalDateTime createdAt,LocalDateTime expiresAt) {
		super();
		this.tokenHash = tokenHash;
		this.user = user;
		this.expiresAt = expiresAt;
		this.createdAt = createdAt;
	}
	
	

	public RefreshToken(String tokenHash, User user, LocalDateTime createdAt, LocalDateTime expiresAt,
			boolean revoked) {
		super();
		this.tokenHash = tokenHash;
		this.user = user;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.revoked = revoked;
	}

	@Override
	public String toString() {
		return "RefreshToken [id=" + id + ", tokenHash=" + tokenHash + ", user=" + user + ", expiresAt=" + expiresAt
				+ ", revoked=" + revoked + ", createdAt=" + createdAt + ", revokedAt=" + revokedAt + ", replacedByHash="
				+ replacedByHash + "]";
	}

	public RefreshToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}