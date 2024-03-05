package tech.peterestephan.equadisbackend.transaction.domain.services;

import org.junit.jupiter.api.Test;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.transaction.domain.values.TransactionResult;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawTransactionStrategyTest {
    @Test
    void execute_ValidWithdrawal_ShouldReturnSuccessfulTransaction() {
        // Arrange
        Account account = new Account();
        double initialBalance = 500.0;
        account.setBalance(initialBalance);

        double withdrawalAmount = 100.0;
        WithdrawTransactionStrategy withdrawStrategy = new WithdrawTransactionStrategy();

        // Act
        TransactionResult result = withdrawStrategy.execute(account, withdrawalAmount);

        // Assert
        assertTrue(result.success());
        assertEquals(initialBalance - withdrawalAmount, account.getBalance());
        assertEquals("Withdraw transaction executed", result.message());
    }

    @Test
    void execute_InsufficientFunds_ShouldReturnFailedTransaction() {
        // Arrange
        Account account = new Account();
        double initialBalance = 50.0;
        account.setBalance(initialBalance);

        double withdrawalAmount = 100.0;
        WithdrawTransactionStrategy withdrawStrategy = new WithdrawTransactionStrategy();

        // Act
        TransactionResult result = withdrawStrategy.execute(account, withdrawalAmount);

        // Assert
        assertFalse(result.success());
        assertEquals(initialBalance, account.getBalance()); // Balance should remain unchanged
        assertEquals("Insufficient funds", result.message());
    }

    @Test
    void execute_InvalidWithdrawalAmount_ShouldReturnFailedTransaction() {
        // Arrange
        Account account = new Account();
        double initialBalance = 50.0;
        account.setBalance(initialBalance);

        double withdrawalAmount = -100.0;
        WithdrawTransactionStrategy withdrawStrategy = new WithdrawTransactionStrategy();

        // Act
        TransactionResult result = withdrawStrategy.execute(account, withdrawalAmount);

        // Assert
        assertTrue(result.success());
        assertEquals(initialBalance, account.getBalance()); // Balance should remain unchanged
        assertEquals("Withdraw transaction failed, amount must be greater than 0", result.message());
    }
}
