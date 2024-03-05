package tech.peterestephan.equadisbackend.transaction.application.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.peterestephan.equadisbackend.common.utils.ApiResponse;
import tech.peterestephan.equadisbackend.transaction.domain.entities.Transaction;
import tech.peterestephan.equadisbackend.transaction.domain.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaction>>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());

        Page<Transaction> transactions = transactionService.getAll(pageable);

        return ApiResponse.<List<Transaction>>builder()
                .pagination(transactions)
                .success(transactions.getContent(), "Transactions retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.save(transaction);

        return ApiResponse.<Transaction>builder()
                .success(savedTransaction, "Transaction saved successfully")
                .build();
    }
}
