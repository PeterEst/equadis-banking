package tech.peterestephan.equadisbackend.transaction.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.account.domain.services.AccountService;
import tech.peterestephan.equadisbackend.transaction.application.dtos.TransactionCreationDto;
import tech.peterestephan.equadisbackend.transaction.application.dtos.TransactionDto;
import tech.peterestephan.equadisbackend.transaction.domain.entities.Transaction;
import tech.peterestephan.equadisbackend.transaction.domain.mappers.TransactionMapper;
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
    private final TransactionMapper transactionMapper;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountService accountService,
            Map<TransactionType, TransactionStrategy> transactionStrategyMap,
            TransactionMapper transactionMapper
    ) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionStrategyMap = transactionStrategyMap;
        this.transactionMapper = transactionMapper;
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Page<TransactionDto> getAll(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);

        return transactions.map(transactionMapper::transactionToTransactionDto);
    }

    public TransactionDto save(TransactionCreationDto transactionCreationDto) {
        Account assignedAccount = accountService.findById(transactionCreationDto.getAccount());
        Transaction transaction = transactionMapper.transactionCreationDtoToTransaction(transactionCreationDto, assignedAccount);

        transaction.setAccount(assignedAccount);
        transaction.setBalance(assignedAccount.getBalance());

        TransactionStrategy transactionStrategy = transactionStrategyMap.get(transaction.getType());
        TransactionResult transactionResult = transactionStrategy.execute(assignedAccount, transaction.getAmount());

        Account updatedAccount = transactionResult.account();
        accountService.save(updatedAccount);

        transaction.setSuccess(transactionResult.success());
        transaction.setMessage(transactionResult.message());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.transactionToTransactionDto(savedTransaction);
    }

    public List<Transaction> findByAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public Page<Transaction> findByAccount(Long accountId, Pageable pageable) {
        return transactionRepository.findByAccountId(accountId, pageable);
    }
}
