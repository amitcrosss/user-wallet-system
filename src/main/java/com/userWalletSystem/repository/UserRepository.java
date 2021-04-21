package com.userWalletSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userWalletSystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findUserByEmail(String email);

	public Optional<User> findUserByEmailAndPassword(String email, String password);

}
