package misrraimsp.technest_rest_api.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.data.AccountRepository;
import misrraimsp.technest_rest_api.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServer {

    private final AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
