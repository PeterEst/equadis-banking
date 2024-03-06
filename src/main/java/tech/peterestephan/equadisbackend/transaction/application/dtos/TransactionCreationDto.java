package tech.peterestephan.equadisbackend.transaction.application.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import tech.peterestephan.equadisbackend.transaction.domain.enums.TransactionType;

public class TransactionCreationDto {
    @NotNull(message = "Transaction Account Id is required")
    private Long account;
    @Min(value = 1, message = "Transaction Amount must be greater or equal than 1")
    @NotNull(message = "Transaction amount is required")
    private Double amount;
    @NotNull(message = "Transaction Type is required")
    private TransactionType type;

    public TransactionCreationDto() {
    }

    public TransactionCreationDto(long account, double amount, TransactionType type) {
        this.account = account;
        this.amount = amount;
        this.type = type;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
