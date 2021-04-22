package com.userWalletSystem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userWalletSystem.entity.Transaction;
import com.userWalletSystem.entity.TransactionMessage;
import com.userWalletSystem.entity.User;
import com.userWalletSystem.entity.Wallet;
import com.userWalletSystem.repository.TransactionRepository;
import com.userWalletSystem.repository.WalletRepsitory;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	UserService userService;

	@Autowired
	WalletRepsitory walletRepsitory;

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public Wallet addMoneyToWallet(Integer userId, Integer walletId, Double amount) throws Exception {

		// check if valid userId or not
		User user = userService.isValidUser(userId);
		if (user == null) {
			throw new Exception("UserId is invalid");
		}
		// find wallet for user
		Optional<Wallet> walletOpt = walletRepsitory.findWalletByWalletIdAndUser(walletId, user);

		if (!walletOpt.isPresent()) {
			throw new Exception("invalid wallet id");
		}
		Wallet wallet = walletOpt.get();
		// update and save the new wallet balance
		wallet.setBalance(wallet.getBalance() + amount);
		walletRepsitory.save(wallet);

		Transaction transaction = new Transaction();
		transaction.setUser(user);
		transaction.setPrimaryWallet(wallet);
		transaction.setTransactionAmount(amount);
		transaction.setTransactionType("self");
		transaction.setTimestamp(new Date());
		transaction.setTransactionDetail(
				"Wallet recharge amount: " + amount + "; Updated wallet balance " + wallet.getBalance());
		transaction.setTransactionStatus(2);

		transactionRepository.save(transaction);

		return wallet;
	}

	@Override
	public Wallet transferMoney(Integer userId, Integer fromWalletId, Integer toWalletId, Double amount,
			String transactionFeesParty) throws Exception {
		// check if valid userId or not
		User user = userService.isValidUser(userId);
		Wallet fromWallet = null;
		Optional<Wallet> fromWalletOpt = null;
		Wallet toWallet = null;
		Optional<Wallet> toWalletOpt = null;
		HashMap<String, Object> transactionOverhead = null;
		Double transactionOverheadAmt;
		Transaction transaction = null;

		if (user == null) {
			throw new Exception("UserId is invalid");
		}
		// find wallet for user
		fromWalletOpt = walletRepsitory.findById(fromWalletId);
		toWalletOpt = walletRepsitory.findById(toWalletId);

		if (!fromWalletOpt.isPresent()) {
			throw new Exception("invalid wallet id");
		}
		if (!toWalletOpt.isPresent()) {
			throw new Exception("User wallet you are trying to send money doesnt exist");
		}
		// check the current user wallet balance if its greater post charges and
		// commission

		if (transactionFeesParty.equals("debit")) {

			transactionOverhead = computeTrasactionOverheadDebit(amount);
			transactionOverheadAmt = (Double) transactionOverhead.get("value");

			if (fromWalletOpt.get().getBalance() >= (amount + transactionOverheadAmt)) {

				fromWallet = fromWalletOpt.get();
				fromWallet.setBalance(fromWallet.getBalance() - (amount + transactionOverheadAmt));
				fromWallet = walletRepsitory.save(fromWallet);

				toWallet = toWalletOpt.get();
				toWallet.setBalance(toWallet.getBalance() + amount);
				walletRepsitory.save(toWallet);

				transaction = new Transaction();
				transaction.setUser(user);
				transaction.setPrimaryWallet(fromWallet);
				transaction.setSecondaryWallet(toWallet);
				transaction.setTransactionAmount(amount);
				transaction.setTransactionOverheadAmount(transactionOverheadAmt);
				transaction.setTransactionOverheadOn("debit");
				transaction.setTransactionType("transfer");
				transaction.setTimestamp(new Date());
				transaction.setTransactionStatus(1);
				transaction.setTransactionDetail(
						"Transfer of amount= " + amount + " from wallet " + fromWalletId + " to wallet" + toWalletId);

				transactionRepository.save(transaction);
			} else {
				throw new Exception("insufficient wallet balance");
			}
		} else {

			transactionOverhead = computeTrasactionOverheadCredit(amount);
			transactionOverheadAmt = (Double) transactionOverhead.get("value");

			if (fromWalletOpt.get().getBalance() >= (amount)) {
				fromWallet = fromWalletOpt.get();
				fromWallet.setBalance(fromWallet.getBalance() - amount);
				fromWallet = walletRepsitory.save(fromWallet);

				toWallet = toWalletOpt.get();
				toWallet.setBalance(toWallet.getBalance() + amount - transactionOverheadAmt);
				walletRepsitory.save(toWallet);

				transaction = new Transaction();
				transaction.setUser(user);
				transaction.setPrimaryWallet(fromWallet);
				transaction.setSecondaryWallet(toWallet);
				transaction.setTransactionAmount(amount);
				transaction.setTransactionOverheadAmount(transactionOverheadAmt);
				transaction.setTransactionOverheadOn("credit");
				transaction.setTransactionType("transfer");
				transaction.setTimestamp(new Date());
				transaction.setTransactionStatus(1);
				transaction.setTransactionDetail(
						"Transfer of amount= " + amount + " from wallet " + fromWalletId + " to wallet" + toWalletId);

				transactionRepository.save(transaction);

			} else {
				throw new Exception("insufficient wallet balance");
			}
		}
		return fromWallet;

	}

	private static HashMap<String, Object> computeTrasactionOverheadDebit(Double amount) {
		HashMap<String, Object> data = new HashMap<>();
		double charges = (0.2 / 100 * amount);
		double commission = (0.05 / 100 * amount);
		String s = "";
		s = "Charge: " + charges + " & Commission: " + commission;
		data.put("message", s);
		data.put("value", (charges + commission));
		return data;
	}

	private static HashMap<String, Object> computeTrasactionOverheadCredit(Double amount) {
		HashMap<String, Object> data = new HashMap<>();

		double credit = amount / (1 + (0.2 + 0.05) / 100);
		double charges = (0.2 / 100 * credit);
		double commission = (0.05 / 100 * credit);
		String s = "";
		s = "Charge: " + charges + " & Commission: " + commission;
		data.put("message", s);
		data.put("value", (charges + commission));
		return data;
	}

	@Override
	public String computeTransactionOverhead(Integer userId, Double amount, String transactionFeesParty) {
		HashMap<String, Object> transactionOverhead = null;
		String transactionOverheadStatement;

		if (transactionFeesParty.equals("debit"))
			transactionOverhead = computeTrasactionOverheadDebit(amount);

		else
			transactionOverhead = computeTrasactionOverheadCredit(amount);

		transactionOverheadStatement = (String) transactionOverhead.get("message");

		return transactionOverheadStatement;

	}

	@Override
	public ArrayList<TransactionMessage> getTrasactionLogs(Integer userId, Integer walletId) throws Exception {

		// check if valid userId or not
		User user = userService.isValidUser(userId);
		if (user == null) {
			throw new Exception("UserId is invalid");
		}
		// find wallet for user
		Optional<Wallet> walletOpt = walletRepsitory.findWalletByWalletIdAndUser(walletId, user);

		if (!walletOpt.isPresent()) {
			throw new Exception("invalid wallet id");
		}
		Wallet wallet = walletOpt.get();
		ArrayList<Transaction> transactionList = transactionRepository
				.findTransactionByPrimaryWalletOrSecondaryWallet(wallet, wallet);

		Collections.sort(transactionList, new Comparator<Transaction>() {

			@Override
			public int compare(Transaction o1, Transaction o2) {
				return o2.getTimestamp().compareTo(o1.getTimestamp());

			}
		});

		ArrayList<TransactionMessage> transactionMessageList = new ArrayList<>();
		for (Transaction temp : transactionList) {
			// (Integer transactionId, Integer walletId, Double transactionAmount, String
			// transactionType, Date timestamp, Integer transactionStatus, String
			// transactionDetail)

			TransactionMessage transactionMessagetemp = null;
			if (temp.getPrimaryWallet().getWalletId().equals(walletId))
				transactionMessagetemp = new TransactionMessage(temp.getTransactionId(), walletId,
						temp.getTransactionAmount(), temp.getTransactionType().equals("transfer") ? "DEBIT" : "SELF",
						temp.getTimestamp(), temp.getTransactionStatus(), temp.getTransactionDetail());
			else
				transactionMessagetemp = new TransactionMessage(temp.getTransactionId(),
						temp.getSecondaryWallet().getWalletId(), temp.getTransactionAmount(),
						temp.getTransactionType().equals("transfer") ? "CREDIT" : "SELF", temp.getTimestamp(),
						temp.getTransactionStatus(), temp.getTransactionDetail());
			transactionMessageList.add(transactionMessagetemp);
		}
		return transactionMessageList;

	}

	@Override
	public Wallet getWalletBalance(Integer userId, Integer walletId) throws Exception {

		// check if valid userId or not
		User user = userService.isValidUser(userId);
		if (user == null) {
			throw new Exception("UserId is invalid");
		}
		// find wallet for user
		Optional<Wallet> walletOpt = walletRepsitory.findWalletByWalletIdAndUser(walletId, user);

		if (!walletOpt.isPresent()) {
			throw new Exception("invalid wallet id");
		}
		Wallet wallet = walletOpt.get();
		
		return wallet;
	}
}
