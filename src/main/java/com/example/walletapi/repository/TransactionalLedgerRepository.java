package com.example.walletapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.walletapi.entity.TransactionLedger;
import java.util.List;

public interface TransactionalLedgerRepository extends JpaRepository<TransactionLedger,String>{
	
List<TransactionLedger> findByFromWalletId(String fromWalletId);
List<TransactionLedger> findByToWalletId(String toWalletId);


}
