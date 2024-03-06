package tech.peterestephan.equadisbackend.transaction.application.dtos;

import tech.peterestephan.equadisbackend.account.application.dtos.AccountDto;
import tech.peterestephan.equadisbackend.transaction.domain.enums.TransactionType;

import java.util.Date;

public class TransactionDto {
    private long id;
    private TransactionType type;
    private AccountDto account;
    private double balance;
    private double amount;
    private boolean success;
    private String message;
    private Date created;

    public TransactionDto() {
    }

    public TransactionDto(long id, TransactionType type, double balance, double amount, boolean success, String message, Date created) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.amount = amount;
        this.success = success;
        this.message = message;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
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

    public void setCreated(Date created) {
        this.created = created;
    }
}
