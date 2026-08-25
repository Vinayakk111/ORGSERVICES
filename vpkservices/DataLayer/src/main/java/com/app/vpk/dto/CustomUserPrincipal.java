package com.app.vpk.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.vpk.entity.Permission;
import com.app.vpk.entity.Role;
import com.app.vpk.entity.User;

public class CustomUserPrincipal implements UserDetails {

	private final Long id;

	private final String username;

	private final String password;

	private final boolean enabled;

	private final boolean accountNonExpired;

	private final boolean accountNonLocked;

	private final boolean credentialsNonExpired;

	private final Collection<? extends GrantedAuthority> authorities;

	public CustomUserPrincipal(User user) {

		this.id = user.getId();

		this.username = user.getUsername();

		this.password = user.getPassword();

		this.enabled = user.isEnabled();

		this.accountNonExpired = user.isAccountNonExpired();

		this.accountNonLocked = user.isAccountNonLocked();

		this.credentialsNonExpired = user.isCredentialsNonExpired();

		this.authorities = buildAuthorities(user);
	}

	private Collection<GrantedAuthority> buildAuthorities(User user) {

		Set<GrantedAuthority> authorities = new HashSet<>();

		for (Role role : user.getRoles()) {

			if (!role.isEnabled()) {
				continue;
			}

			/*
			 * ROLE_ADMIN ROLE_MANAGER ROLE_FINANCE
			 */
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

			for (Permission permission : role.getPermissions()) {

				if (!permission.isEnabled()) {
					continue;
				}

				/*
				 * USER_READ USER_CREATE PAYMENT_APPROVE
				 */
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}

		return authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public boolean isAccountNonExpired() {

		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {

		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}

	public Long getId() {
		return id;
	}

}
