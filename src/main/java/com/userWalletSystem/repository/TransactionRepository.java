package com.userWalletSystem.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userWalletSystem.entity.Transaction;
import com.userWalletSystem.entity.Wallet;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	public ArrayList<Transaction> findTransactionByPrimaryWalletOrSecondaryWallet(Wallet fromWallet, Wallet toWallet);
}
