package tech.peterestephan.equadisbackend.customer.application.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CustomerDto {
    private Long id;
    @NotBlank(message = "Customer name is required")
    @Length(min = 3, message = "Customer Name must be at least 3 characters long")
    private String name;

    public CustomerDto() {
    }

    public CustomerDto(Long id, String name) {
        this.id = id;
        this.name = name;
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
