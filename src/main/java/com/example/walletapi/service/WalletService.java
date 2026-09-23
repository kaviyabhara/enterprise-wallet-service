package com.example.walletapi.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.example.walletapi.entity.TransactionLedger;
import com.example.walletapi.entity.Wallet;
import com.example.walletapi.repository.TransactionalLedgerRepository;
import com.example.walletapi.repository.WalletRepository;


import jakarta.transaction.Transactional;

@Service
public class WalletService {

  @Autowired
  private WalletRepository walletRepository;
  @Autowired
  private TransactionalLedgerRepository ledgerRepository;
   
  //create walletId and Balance 
  public Wallet createWallet(String walletId,BigDecimal intialBalance) {
	  Wallet wallet=new Wallet(walletId,intialBalance);
	  return walletRepository.save(wallet);
	  
  }
  //Method to fetch wallet details
  
  public Optional<Wallet> getWallet(String walletId){
	  
	  return walletRepository.findById(walletId);
  }
  
@Transactional

public void transferMoney(String fromWalletId, String toWalletId,BigDecimal amount) {
	
	
	
	
	// Step 1: Lock and fetch the sender's wallet
	Wallet sender=walletRepository.findWalletForUpdate(fromWalletId)
	.orElseThrow(()->new RuntimeException("Sender id does not exit"));
	
	
	// Step 2: Lock and fetch the receiver's wallet
	Wallet reciever=walletRepository.findWalletForUpdate(toWalletId)
			.orElseThrow(() -> new RuntimeException("sender id does not exists"));
	
	
	// Step 3: Business Logic Validation
	if(sender.getBalance().compareTo(amount)<0) {
		throw new RuntimeException("Insufficient balance");
		
	}
	// Step 4: Perform the transaction
		sender.setBalance(sender.getBalance().subtract(amount));
		reciever.setBalance(reciever.getBalance().add(amount));
		
		
		walletRepository.save(sender);
		walletRepository.save(reciever);
	
		
		TransactionLedger ledgerEntry = new TransactionLedger(fromWalletId, toWalletId, amount,java.time.LocalDateTime.now());
	      ledgerRepository.save(ledgerEntry);
}



//Fast read from Redis cache; falls back to MySQL if missing
@Cacheable(value = "walletBalance", key = "#walletId")
public BigDecimal getBalance(String walletId) {
    Wallet wallet = walletRepository.findById(walletId)
            .orElseThrow(() -> new RuntimeException("Wallet not found"));
    return wallet.getBalance();
}

// Transaction updates balance and evicts stale cached value from Redis
@Transactional
@CacheEvict(value = "walletBalance", key = "#fromWalletId")
public void processTransaction(String fromWalletId, String toWalletId, BigDecimal amount) {
    // Your existing transfer / pessimistic locking logic here

}
}


