package tech.peterestephan.equadisbackend.customer.domain.mappers;

import org.mapstruct.Mapper;
import tech.peterestephan.equadisbackend.customer.application.dtos.CustomerDto;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto customerDto);
    CustomerDto customerToCustomerDto(Customer customer);
}
