package com.example.walletapi.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="wallets")
public class Wallet {
	
	@Id
	private String walletId;
	private BigDecimal balance;
	public Wallet() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Wallet(String walletId, BigDecimal balance) {
		super();
		this.walletId = walletId;
		this.balance = balance;
	}
	public String getWalletId() {
		return walletId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	
	

}
