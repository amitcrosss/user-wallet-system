package com.userWalletSystem.entity;

import java.util.Date;

public class TransactionMessage {

	private Integer transactionId;
	private Integer walletId;
	private Double transactionAmount;
	private String transactionType;
	private Date timestamp;
	private Integer transactionStatus;
	private String transactionDetail;

	
	public TransactionMessage(Integer transactionId, Integer walletId, Double transactionAmount, String transactionType,
			Date timestamp, Integer transactionStatus, String transactionDetail) {
		super();
		this.transactionId = transactionId;
		this.walletId = walletId;
		this.transactionAmount = transactionAmount;
		this.transactionType = transactionType;
		this.timestamp = timestamp;
		this.transactionStatus = transactionStatus;
		this.transactionDetail = transactionDetail;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
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

	@Override
	public String toString() {
		return "TransactionMessage [transactionId=" + transactionId + ", walletId=" + walletId + ", transactionAmount="
				+ transactionAmount + ", transactionType=" + transactionType + ", timestamp=" + timestamp
				+ ", transactionStatus=" + transactionStatus + ", transactionDetail=" + transactionDetail + "]";
	}

}
