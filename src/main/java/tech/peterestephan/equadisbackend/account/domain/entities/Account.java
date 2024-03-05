package tech.peterestephan.equadisbackend.account.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;

import java.util.Date;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Min(0)
    private double balance = 0.0;

    private final Date created = new Date();

    public Account() {
    }

    @JsonCreator
    public Account(@JsonProperty("customer") long customer) {
        Customer relatedCustomer = new Customer();
        relatedCustomer.setId(customer);
        this.customer = relatedCustomer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
}
