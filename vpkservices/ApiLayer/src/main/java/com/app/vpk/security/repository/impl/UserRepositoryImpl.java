package com.app.vpk.security.repository.impl;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.app.vpk.dto.CustomUserPrincipal;
import com.app.vpk.entity.User;

@Repository
public class UserRepositoryImpl {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {

		User user = findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return new CustomUserPrincipal(user);
	}

	
	Optional<User> findByUsername(String username) {
		String jpql = "SELECT u FROM User u WHERE u.username = :param";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		query.setParameter("param", username);
		return Optional.of(query.getSingleResult());
	}
}
