package tech.peterestephan.equadisbackend.transaction.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.peterestephan.equadisbackend.transaction.domain.enums.TransactionType;
import tech.peterestephan.equadisbackend.transaction.domain.services.TransactionStrategy;

import java.util.Map;

@Configuration
public class TransactionStrategyConfiguration {
    @Bean
    public Map<TransactionType, TransactionStrategy> transactionStrategyMap(
            TransactionStrategy depositTransactionStrategy,
            TransactionStrategy withdrawTransactionStrategy
    ) {
        return Map.of(
                TransactionType.DEPOSIT, depositTransactionStrategy,
                TransactionType.WITHDRAW, withdrawTransactionStrategy
        );
    }


}
