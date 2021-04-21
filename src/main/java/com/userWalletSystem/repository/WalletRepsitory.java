package com.userWalletSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userWalletSystem.entity.User;
import com.userWalletSystem.entity.Wallet;

@Repository
public interface WalletRepsitory extends JpaRepository<Wallet, Integer> {

	public Optional<Wallet> findWalletByWalletIdAndUser(Integer walletId, User user);
}
