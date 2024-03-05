package tech.peterestephan.equadisbackend.transaction.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.account.domain.services.AccountService;
import tech.peterestephan.equadisbackend.common.exceptions.RequiredFieldException;
import tech.peterestephan.equadisbackend.transaction.domain.entities.Transaction;
import tech.peterestephan.equadisbackend.transaction.domain.values.TransactionResult;
import tech.peterestephan.equadisbackend.transaction.domain.enums.TransactionType;
import tech.peterestephan.equadisbackend.transaction.infrastructure.repositories.TransactionRepository;

import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final Map<TransactionType, TransactionStrategy> transactionStrategyMap;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountService accountService,
            Map<TransactionType, TransactionStrategy> transactionStrategyMap
    ) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionStrategyMap = transactionStrategyMap;
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Page<Transaction> getAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    public Transaction save(Transaction transaction) {
        if (transaction.getAccount().getId() == 0) {
            throw new RequiredFieldException("Account ID is required");
        }

        Account assignedAccount = accountService.findById(transaction.getAccount().getId());

        transaction.setAccount(assignedAccount);
        transaction.setBalance(assignedAccount.getBalance());

        TransactionStrategy transactionStrategy = transactionStrategyMap.get(transaction.getType());
        TransactionResult transactionResult = transactionStrategy.execute(assignedAccount, transaction.getAmount());

        Account updatedAccount = transactionResult.account();
        accountService.save(updatedAccount);

        transaction.setSuccess(transactionResult.success());
        transaction.setMessage(transactionResult.message());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public Page<Transaction> findByAccount(Long accountId, Pageable pageable) {
        return transactionRepository.findByAccountId(accountId, pageable);
    }
}
