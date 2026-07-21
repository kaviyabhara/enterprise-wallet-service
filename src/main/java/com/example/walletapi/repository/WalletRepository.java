package com.example.walletapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.walletapi.entity.Wallet;

import jakarta.persistence.LockModeType;

@Repository
public interface WalletRepository  extends JpaRepository<Wallet,String>{

	
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT w FROM Wallet w WHERE w.walletId=:walletId")
	Optional<Wallet> findWalletForUpdate(@Param ("walletId") String walletId);

     


}
