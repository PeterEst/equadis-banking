package tech.peterestephan.equadisbackend.account.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.peterestephan.equadisbackend.account.application.dtos.AccountCreationDto;
import tech.peterestephan.equadisbackend.account.application.dtos.AccountDto;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.customer.domain.entities.Customer;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "id", ignore = true)
    Account accountCreationDtoToAccount(AccountCreationDto accountCreationDto, Customer customer);

    AccountDto accountToAccountDto(Account account);

    Account accountDtoToAccount(AccountDto accountDto);
}
