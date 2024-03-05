package tech.peterestephan.equadisbackend.account.application.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.account.domain.services.AccountService;
import tech.peterestephan.equadisbackend.common.utils.ApiResponse;
import tech.peterestephan.equadisbackend.transaction.domain.entities.Transaction;
import tech.peterestephan.equadisbackend.transaction.domain.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> getAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());

        Page<Account> accounts = accountService.getAll(pageable);

        return ApiResponse.<List<Account>>builder()
                .pagination(accounts)
                .success(accounts.getContent(), "Accounts Retrieved Successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Account>> createAccount(@Valid @RequestBody Account account) {
        Account createdAccount = accountService.save(account);

        return ApiResponse.<Account>builder()
                .success(createdAccount, "Account Created Successfully")
                .build();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<Account>> getAccount(@PathVariable Long accountId) {
        Account account = accountService.findById(accountId);

        return ApiResponse.<Account>builder()
                .success(account, "Account Retrieved Successfully")
                .build();
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAccountTransactions(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());

        Page<Transaction> transactions = transactionService.findByAccount(accountId, pageable);

        return ApiResponse.<List<Transaction>>builder()
                .pagination(transactions)
                .success(transactions.getContent(), "Transactions Retrieved Successfully")
                .build();
    }
}
