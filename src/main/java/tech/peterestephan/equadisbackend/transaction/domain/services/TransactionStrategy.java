package tech.peterestephan.equadisbackend.transaction.domain.services;

import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.transaction.domain.values.TransactionResult;

public interface TransactionStrategy {
    TransactionResult execute(Account account, double amount);
}
