package tech.peterestephan.equadisbackend.transaction.domain.services;

import org.springframework.stereotype.Service;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.transaction.domain.values.TransactionResult;

@Service
public class WithdrawTransactionStrategy implements TransactionStrategy {
    @Override
    public TransactionResult execute(Account account, double amount) {
        if (amount <= 0) {
            return new TransactionResult(false, account, "Withdraw transaction failed, amount must be greater than 0");
        }

        double accountAmount = account.getBalance();

        if (accountAmount < amount) {
            return new TransactionResult(false, account, "Insufficient funds");
        }
        account.setBalance(accountAmount - amount);

        return new TransactionResult(true, account, "Withdraw transaction executed");
    }
}
