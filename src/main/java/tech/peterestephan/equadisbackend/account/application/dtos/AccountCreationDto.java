package tech.peterestephan.equadisbackend.account.application.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AccountCreationDto {
    @NotNull(message = "Account Customer ID Required")
    private Long customer;
    @Min(value = 0, message = "Account Balance must be positive")
    private Double balance = 0.0;

    public AccountCreationDto() {
    }

    public AccountCreationDto(Long customer, double balance) {
        this.customer = customer;
        this.balance = balance;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double amount) {
        this.balance = amount;
    }
}
