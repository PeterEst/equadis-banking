package tech.peterestephan.equadisbackend.transaction.application.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.peterestephan.equadisbackend.common.utils.ApiResponse;
import tech.peterestephan.equadisbackend.transaction.application.dtos.TransactionCreationDto;
import tech.peterestephan.equadisbackend.transaction.application.dtos.TransactionDto;
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
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());

        Page<TransactionDto> transactions = transactionService.getAll(pageable);

        return ApiResponse.<List<TransactionDto>>builder()
                .pagination(transactions)
                .success(transactions.getContent(), "Transactions retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionDto>> createTransaction(@Valid @RequestBody TransactionCreationDto transactionCreationDto) {
        TransactionDto savedTransactionDto = transactionService.save(transactionCreationDto);

        return ApiResponse.<TransactionDto>builder()
                .success(savedTransactionDto, "Transaction saved successfully")
                .build();
    }
}
