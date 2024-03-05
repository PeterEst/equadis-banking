package tech.peterestephan.equadisbackend.transaction.domain.services;

import org.springframework.stereotype.Service;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.transaction.domain.values.TransactionResult;

@Service
public class DepositTransactionStrategy implements TransactionStrategy {
    @Override
    public TransactionResult execute(Account account, double amount) {
        if (amount <= 0) {
            return new TransactionResult(false, account, "Deposit transaction failed, Withdraw transaction failed, amount must be greater than 0");
        }

        double accountAmount = account.getBalance();
        double newAmount = accountAmount + amount;

        account.setBalance(newAmount);

        return new TransactionResult(true, account, "Deposit transaction executed");
    }
}
