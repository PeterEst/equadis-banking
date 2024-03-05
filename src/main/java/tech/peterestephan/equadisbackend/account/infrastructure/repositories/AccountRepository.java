package tech.peterestephan.equadisbackend.account.infrastructure.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(Long customerId);
    Page<Account> findByCustomerId(Long customerId, Pageable pageable);
}
