package tech.peterestephan.equadisbackend.customer.infrastructure.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Page<Customer> findByNameContaining(String name, Pageable page);
}
