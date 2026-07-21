package com.example.walletapi.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferRequest {
	  @NotBlank(message="sender wallet id is required")	
     private String fromWalletId;
 
	  @NotBlank(message="reciever wallet id is required")
	 private String toWalletId;
   
     @NotNull(message="amount cannot be null")
     @Positive(message="amount must be positive")
	 private BigDecimal amount;
	 public TransferRequest() {
		super();
		// TODO Auto-generated constructor stub
	 }
	 public TransferRequest(String fromWalletId, String toWalletId, BigDecimal amount) {
		
		this.fromWalletId = fromWalletId;
		this.toWalletId = toWalletId;
		this.amount = amount;
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
	 
	 
	

}
