package com.userWalletSystem.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer transactionId;

	@OneToOne
	private User user;

	@OneToOne
	private Wallet primaryWallet;

	@OneToOne
	private Wallet secondaryWallet;

	private Double transactionAmount;

	private Double transactionOverheadAmount;
	private String transactionOverheadOn;

	private String transactionType;
	private Date timestamp;
	private Integer transactionStatus;

	private String transactionDetail;

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Wallet getPrimaryWallet() {
		return primaryWallet;
	}

	public void setPrimaryWallet(Wallet primaryWallet) {
		this.primaryWallet = primaryWallet;
	}

	public Wallet getSecondaryWallet() {
		return secondaryWallet;
	}

	public void setSecondaryWallet(Wallet secondaryWallet) {
		this.secondaryWallet = secondaryWallet;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Integer getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(String transactionDetail) {
		this.transactionDetail = transactionDetail;
	}


	public Double getTransactionOverheadAmount() {
		return transactionOverheadAmount;
	}

	public void setTransactionOverheadAmount(Double transactionOverheadAmount) {
		this.transactionOverheadAmount = transactionOverheadAmount;
	}

	public String getTransactionOverheadOn() {
		return transactionOverheadOn;
	}

	public void setTransactionOverheadOn(String transactionOverheadOn) {
		this.transactionOverheadOn = transactionOverheadOn;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", user=" + user + ", primaryWallet=" + primaryWallet
				+ ", secondaryWallet=" + secondaryWallet + ", transactionAmount=" + transactionAmount
				+ ", transactionOverheadAmount=" + transactionOverheadAmount + ", transactionOverheadOn="
				+ transactionOverheadOn + ", transactionType=" + transactionType + ", timestamp=" + timestamp
				+ ", transactionStatus=" + transactionStatus + ", transactionDetail=" + transactionDetail + "]";
	}
}
