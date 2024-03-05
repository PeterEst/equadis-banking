package tech.peterestephan.equadisbackend.customer.application.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;
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
    public ResponseEntity<ApiResponse<List<Customer>>> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String queryString
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customers;
        if (queryString != null && !queryString.isEmpty()){
            customers = customerService.searchByName(queryString, pageable);
        } else {
            customers = customerService.getAll(pageable);
        }

        return ApiResponse.<List<Customer>>builder()
                .pagination(customers)
                .success(customers.getContent(), "Customers Retrieved Successfully")
                .build();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Customer>> getCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.findById(customerId);

        return ApiResponse.<Customer>builder()
                .success(customer, "Customer Retrieved Successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.save(customer);

        return ApiResponse.<Customer>builder()
                .success(createdCustomer, "Customer Created Successfully", HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{customerId}/accounts")
    public ResponseEntity<ApiResponse<List<Account>>> getCustomerAccounts(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());

        Page<Account> accounts = customerService.getCustomerAccounts(customerId, pageable);

        return ApiResponse.<List<Account>>builder()
                .pagination(accounts)
                .success(accounts.getContent(), "Customer Accounts Retrieved Successfully")
                .build();
    }
}
