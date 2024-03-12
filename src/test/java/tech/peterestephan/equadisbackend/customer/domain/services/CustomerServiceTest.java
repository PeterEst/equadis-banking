package tech.peterestephan.equadisbackend.customer.domain.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tech.peterestephan.equadisbackend.account.domain.mappers.AccountMapper;
import tech.peterestephan.equadisbackend.account.infrastructure.repositories.AccountRepository;
import tech.peterestephan.equadisbackend.customer.application.dtos.CustomerDto;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;
import tech.peterestephan.equadisbackend.customer.domain.mappers.CustomerMapper;
import tech.peterestephan.equadisbackend.customer.infrastructure.repositories.CustomerRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testSearchByName_whenNameIsProvided_thenReturnPageOfCustomers() {
        // Arrange
        List<Customer> customerList = Arrays.asList(new Customer(), new Customer());
        Page<Customer> customerPage = new PageImpl<>(customerList);
        Pageable pageable = PageRequest.of(0, 10);
        when(customerRepository.findByNameContaining("test", pageable)).thenReturn(customerPage);

        // Act
        Page<CustomerDto> response = customerService.searchByName("test", pageable);

        // Assert
        assertEquals(customerList.size(), response.getContent().size());
    }
}
