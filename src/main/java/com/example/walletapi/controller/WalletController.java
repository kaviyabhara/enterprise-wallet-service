package com.example.walletapi.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletapi.dto.TransferRequest;
import com.example.walletapi.entity.Wallet;
import com.example.walletapi.service.WalletService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/wallet")
@Tag(name = "Wallet Management", description = "Endpoints for managing balances and processing concurrent transfers")

public class WalletController {
	
	@Autowired
	private WalletService  walletService;
	
	// 1. Endpoint to create a wallet
	@PostMapping("/create")
	public Wallet createWallet(@RequestParam String walletId,@RequestParam BigDecimal intialBalance) {
		return walletService.createWallet(walletId,intialBalance);
		
	}
	// Endpoint to fetch cached wallet balance
		@GetMapping("/{walletId}/balance")
		@Operation(summary = "Get wallet balance", description = "Fetches the current balance for a wallet using Redis caching.")
		public ResponseEntity<BigDecimal> getBalance(@PathVariable String walletId) {
			BigDecimal balance = walletService.getBalance(walletId);
			return ResponseEntity.ok(balance);
		}
	
	
	@PostMapping("/transfer")
	@Operation(summary = "Transfer money concurrently", description = "Executes a secure, audited money transfer between two wallets using pessimistic locking.")
	public ResponseEntity<String> transferMoney(
	        @RequestParam(name = "fromWalletId") String fromWalletId,
	        @RequestParam(name = "toWalletId") String toWalletId,
	        @RequestParam(name = "amount") BigDecimal amount) {
	    
	    walletService.transferMoney(fromWalletId, toWalletId, amount);
	    return ResponseEntity.ok("Transfer successful");
	}
	
}	


	
	
	
