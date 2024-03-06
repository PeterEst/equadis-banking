package tech.peterestephan.equadisbackend.account.application.dtos;

import tech.peterestephan.equadisbackend.customer.application.dtos.CustomerDto;

import java.util.Date;

public class AccountDto {
    private CustomerDto customer;
    private Long id;
    private double balance;
    private Date created;

    public AccountDto() {
    }

    public AccountDto(CustomerDto customer, Long id, double balance, Date created) {
        this.customer = customer;
        this.id = id;
        this.balance = balance;
        this.created = created;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
