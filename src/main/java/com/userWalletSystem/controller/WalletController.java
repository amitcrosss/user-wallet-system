package com.userWalletSystem.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userWalletSystem.entity.TransactionMessage;
import com.userWalletSystem.entity.Wallet;
import com.userWalletSystem.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	WalletService walletService;

	@PostMapping("/wallet/{userId}/{walletId}/addMoney/amount/{amount}")
	public ResponseEntity<String> addMoneyToWallet(@PathVariable Integer userId, @PathVariable Integer walletId,
			@PathVariable Double amount) {

		try {
			Wallet wallet = walletService.addMoneyToWallet(userId, walletId, amount);
			String resp = "Current wallet balance " + wallet.getBalance();
			return new ResponseEntity<>(resp, HttpStatus.OK);

		} catch (Exception e) {
			String resp = e.getMessage();
			return new ResponseEntity<>(resp, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PostMapping("/wallet/{userId}/transferMoney/from/{fromWalletId}/to/{toWalletId}/amount/{amount}/overheadOn/{party}")
	public ResponseEntity<String> transferMoney(@PathVariable Integer userId, @PathVariable Integer fromWalletId,
			@PathVariable Integer toWalletId, @PathVariable Double amount,
			@PathVariable(name = "party") String transactionFeesParty) {

		try {
			Wallet fromWallet = walletService.transferMoney(userId, fromWalletId, toWalletId, amount,
					transactionFeesParty);
			String resp = "Transfer successfull! Current wallet balance " + fromWallet.getBalance();
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (Exception e) {
			String resp = e.getMessage();
			return new ResponseEntity<>(resp, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/wallet/{userId}/transactionOverhead/amount/{amount}/overheadOn/{party}")
	public ResponseEntity<String> computeTransactionOverhead(@PathVariable Integer userId, @PathVariable Double amount,
			@PathVariable(name = "party") String transactionFeesParty) {

		String transactionOverhead = walletService.computeTransactionOverhead(userId, amount, transactionFeesParty);
		String resp = transactionOverhead;
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@PostMapping("/wallet/{userId}/trasactionLogs/wallet/{walletId}")
	public ResponseEntity trasactionLogs(@PathVariable Integer userId, @PathVariable Integer walletId) {

		ArrayList<TransactionMessage> transactionMessageList;
		try {
			transactionMessageList = walletService.getTrasactionLogs(userId, walletId);
			return new ResponseEntity<ArrayList<TransactionMessage>>(transactionMessageList, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}
}
