package com.example.walletapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Transaction_Ledger")
public class TransactionLedger {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	
	@Column(name="from_wallet_id",nullable=false)
	private String fromWalletId;
	@Column(name="to_wallet_id",nullable=false)
	private String toWalletId;
	
	@Column(nullable=false)
	private BigDecimal amount;
	
	@Column(nullable=false)
	private LocalDateTime timestamp;

	public TransactionLedger() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionLedger(String fromWalletId, String toWalletId, BigDecimal amount, LocalDateTime timestamp) {
		super();
		this.fromWalletId = fromWalletId;
		this.toWalletId = toWalletId;
		this.amount = amount;
		this.timestamp = java.time.LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromWalletId() {
		return fromWalletId;
	}

	public void setFromWalletId(String fromWalletId) {
		this.fromWalletId = fromWalletId;
	}

	public String getToWalletId() {
		return toWalletId;
	}

	public void setToWalletId(String toWalletId) {
		this.toWalletId = toWalletId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
