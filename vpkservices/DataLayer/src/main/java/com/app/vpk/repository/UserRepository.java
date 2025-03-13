package com.app.vpk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.vpk.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
