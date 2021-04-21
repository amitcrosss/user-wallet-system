package com.userWalletSystem.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "wallet_id_seq", initialValue = 10000)
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_id_seq")
	private Integer walletId;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
	private double balance;

	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Wallet [walletId=" + walletId + ", user=" + user + ", balance=" + balance + "]";
	}



}
