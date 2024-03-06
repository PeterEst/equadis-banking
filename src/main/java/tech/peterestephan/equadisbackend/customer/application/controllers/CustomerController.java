package tech.peterestephan.equadisbackend.customer.application.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.peterestephan.equadisbackend.account.application.dtos.AccountDto;
import tech.peterestephan.equadisbackend.customer.application.dtos.CustomerDto;
import tech.peterestephan.equadisbackend.customer.domain.services.CustomerService;
import tech.peterestephan.equadisbackend.common.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String queryString
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<CustomerDto> customers;
        if (queryString != null && !queryString.isEmpty()) {
            customers = customerService.searchByName(queryString, pageable);
        } else {
            customers = customerService.getAll(pageable);
        }

        return ApiResponse.<List<CustomerDto>>builder()
                .pagination(customers)
                .success(customers.getContent(), "Customers Retrieved Successfully")
                .build();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerDto>> getCustomer(@PathVariable Long customerId) {
        CustomerDto customer = customerService.findDtoById(customerId);

        return ApiResponse.<CustomerDto>builder()
                .success(customer, "Customer Retrieved Successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDto>> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto createdCustomer = customerService.saveDto(customerDto);

        return ApiResponse.<CustomerDto>builder()
                .success(createdCustomer, "Customer Created Successfully", HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{customerId}/accounts")
    public ResponseEntity<ApiResponse<List<AccountDto>>> getCustomerAccounts(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());

        Page<AccountDto> accounts = customerService.getCustomerAccounts(customerId, pageable);

        return ApiResponse.<List<AccountDto>>builder()
                .pagination(accounts)
                .success(accounts.getContent(), "Customer Accounts Retrieved Successfully")
                .build();
    }
}
