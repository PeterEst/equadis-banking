package tech.peterestephan.equadisbackend.transaction.domain.services;

import org.junit.jupiter.api.Test;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.transaction.domain.values.TransactionResult;

import static org.junit.jupiter.api.Assertions.*;

public class DepositTransactionStrategyTest {

    private final DepositTransactionStrategy depositStrategy = new DepositTransactionStrategy();

    private Account generateAccount(double balance) {
        Account account = new Account();
        account.setBalance(balance);
        return account;
    }

    @Test
    void execute_ValidDeposit_ShouldReturnSuccessfulTransaction() {
        // Arrange
        Account account = this.generateAccount(0.0);
        double initialBalance = account.getBalance();
        double depositAmount = 100.0;

        // Act
        TransactionResult result = depositStrategy.execute(account, depositAmount);

        // Assert
        assertTrue(result.success());
        assertEquals(initialBalance + depositAmount, account.getBalance());
        assertEquals("Deposit transaction executed", result.message());
    }

    @Test
    void execute_InvalidDeposit_ShouldReturnFailedTransaction() {
        // Arrange
        Account account = this.generateAccount(0.0);
        double initialBalance = account.getBalance();
        double depositAmount = -100.0;

        // Act
        TransactionResult result = depositStrategy.execute(account, depositAmount);

        // Assert
        assertFalse(result.success());
        assertEquals(initialBalance, account.getBalance());
        assertEquals("Deposit transaction failed, amount must be greater than 0", result.message());
    }
}
