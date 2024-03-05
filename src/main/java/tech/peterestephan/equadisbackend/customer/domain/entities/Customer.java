package tech.peterestephan.equadisbackend.customer.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;

import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "Customer name is required")
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
