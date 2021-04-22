package com.userWalletSystem.service;

import java.util.ArrayList;

import com.userWalletSystem.entity.TransactionMessage;
import com.userWalletSystem.entity.Wallet;

public interface WalletService {

	public Wallet addMoneyToWallet(Integer userId, Integer walletId, Double amount) throws Exception;

	public Wallet transferMoney(Integer userId, Integer fromWalletId, Integer toWalletId, Double amount, String transactionFeesParty) throws Exception;

	public String computeTransactionOverhead(Integer userId, Double amount,
			String transactionFeesParty);

	public ArrayList<TransactionMessage> getTrasactionLogs(Integer userId, Integer walletId) throws Exception;

	public Wallet getWalletBalance(Integer userId, Integer walletId) throws Exception;
	
}
