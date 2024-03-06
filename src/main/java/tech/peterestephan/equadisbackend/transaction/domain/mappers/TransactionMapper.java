package tech.peterestephan.equadisbackend.transaction.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.peterestephan.equadisbackend.account.domain.entities.Account;
import tech.peterestephan.equadisbackend.transaction.application.dtos.TransactionCreationDto;
import tech.peterestephan.equadisbackend.transaction.application.dtos.TransactionDto;
import tech.peterestephan.equadisbackend.transaction.domain.entities.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "account", source = "account")
    @Mapping(target = "id", ignore = true)
    Transaction transactionCreationDtoToTransaction(TransactionCreationDto transactionCreationDto, Account account);

    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    @Mapping(target = "account", source = "account")
    TransactionDto transactionToTransactionDto(Transaction transaction);
}
