package tech.peterestephan.equadisbackend.transaction.domain.values;

import tech.peterestephan.equadisbackend.account.domain.entities.Account;

public record TransactionResult(boolean success, Account account, String message) {
}
