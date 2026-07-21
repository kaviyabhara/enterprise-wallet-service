package com.example.walletapi.service;

import com.example.walletapi.entity.Wallet;
import com.example.walletapi.entity.TransactionLedger;
import com.example.walletapi.repository.WalletRepository;
import com.example.walletapi.repository.TransactionalLedgerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionalLedgerRepository ledgerRepository;

    @BeforeEach
    public void setup() {
        ledgerRepository.deleteAll();
        walletRepository.deleteAll();

        // Create two initial wallets for testing
        walletService.createWallet("USER_A", new BigDecimal("1000.00"));
        walletService.createWallet("USER_B", new BigDecimal("500.00"));
    }

    // TEST 1: Concurrent successful transfers (Happy Path)
    @Test
    public void testConcurrentTransfers() throws InterruptedException {
        int numberOfThreads = 10;
        BigDecimal transferAmount = new BigDecimal("10.00");

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    walletService.transferMoney("USER_A", "USER_B", transferAmount);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // Verifications
        Wallet walletA = walletRepository.findById("USER_A").orElseThrow();
        Wallet walletB = walletRepository.findById("USER_B").orElseThrow();

        // 10 threads * 10.00 = 100.00 total transferred
        assertEquals(new BigDecimal("900.00"), walletA.getBalance());
        assertEquals(new BigDecimal("600.00"), walletB.getBalance());

        List<TransactionLedger> totalLogs = ledgerRepository.findAll();
        assertEquals(10, totalLogs.size());
    }

    // TEST 2: High traffic exceeding the available balance
    @Test
    public void testConcurrentTransfersWithInsufficientBalance() throws InterruptedException {
        int numberOfThreads = 10;
        BigDecimal transferAmount = new BigDecimal("20.00"); 

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    walletService.transferMoney("USER_A", "USER_B", transferAmount);
                } catch (IllegalArgumentException e) {
                    // Expected exception caught for the 5 failing threads
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // Verifications
        Wallet walletA = walletRepository.findById("USER_A").orElseThrow();
        Wallet walletB = walletRepository.findById("USER_B").orElseThrow();

        // Only 5 transfers can succeed (5 * 20 = 100) before USER_A hit 0.00
        assertEquals(new BigDecimal("0.00"), walletA.getBalance());
        assertEquals(new BigDecimal("600.00"), walletB.getBalance());

        List<TransactionLedger> totalLogs = ledgerRepository.findAll();
        assertEquals(5, totalLogs.size());
    }

    // TEST 3: Multi-directional traffic running at the same time (Deadlock Check)
    @Test
    public void testConcurrentReverseTransfers() throws InterruptedException {
        int numberOfThreads = 10; 
        BigDecimal transferAmount = new BigDecimal("10.00");

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            final boolean isEven = (i % 2 == 0);
            executorService.submit(() -> {
                try {
                    if (isEven) {
                        walletService.transferMoney("USER_A", "USER_B", transferAmount);
                    } else {
                        walletService.transferMoney("USER_B", "USER_A", transferAmount);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // Verifications
        Wallet walletA = walletRepository.findById("USER_A").orElseThrow();
        Wallet walletB = walletRepository.findById("USER_B").orElseThrow();

        // 5 transfers out (-50) and 5 transfers in (+50) means net zero change
        assertEquals(new BigDecimal("1000.00"), walletA.getBalance());
        assertEquals(new BigDecimal("500.00"), walletB.getBalance());

        List<TransactionLedger> totalLogs = ledgerRepository.findAll();
        assertEquals(10, totalLogs.size());
    }
}