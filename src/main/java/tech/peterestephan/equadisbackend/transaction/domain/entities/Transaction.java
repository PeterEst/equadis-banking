package tech.peterestephan.equadisbackend.transaction.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.transaction.domain.enums.TransactionType;

import java.util.Date;

@Entity
@Table(name = "account_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @Min(value = 0, message = "Transaction Account Balance is required")
    @NotNull(message = "Transaction amount is required")
    private Double balance = 0.0;

    @Min(value = 1, message = "Transaction Amount must be greater or equal than 1")
    @NotNull(message = "Transaction amount is required")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private boolean success;
    private String message;
    private final Date created = new Date();

    public Transaction() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }
}
