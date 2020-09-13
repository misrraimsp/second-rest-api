package misrraimsp.technest_rest_api.util;

import misrraimsp.technest_rest_api.data.AccountRepository;
import misrraimsp.technest_rest_api.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Currency;

@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository) {

        return args -> {
            Account a1 = new Account();
            a1.setName("smart account");
            a1.setCurrency(Currency.getInstance("EUR"));
            a1.setBalance(BigDecimal.valueOf(124.78));
            a1.setTreasury(false);
            accountRepository.save(a1);

            Account a2 = new Account();
            a2.setName("little account");
            a2.setCurrency(Currency.getInstance("EUR"));
            a2.setBalance(BigDecimal.valueOf(17.10));
            a2.setTreasury(false);
            accountRepository.save(a2);

            Account a3 = new Account();
            a3.setName("premium account");
            a3.setCurrency(Currency.getInstance("EUR"));
            a3.setBalance(BigDecimal.valueOf(1204.99));
            a3.setTreasury(true);
            accountRepository.save(a3);

            accountRepository.findAll().forEach(account -> log.info("Loaded " + account));


        };
    }
}
