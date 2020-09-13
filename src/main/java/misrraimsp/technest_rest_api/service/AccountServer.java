package misrraimsp.technest_rest_api.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.data.AccountRepository;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.util.exception.EntityNotFoundByIdException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServer {

    private final AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long accountId) throws EntityNotFoundByIdException {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundByIdException(accountId, Account.class.getSimpleName()));
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account editById(Long accountId, Account accountData) { // do not modify treasury field
        Account editedAccount = this.findById(accountId);
        if (accountData.getBalance() != null) editedAccount.setBalance(accountData.getBalance());
        if (accountData.getCurrency() != null) editedAccount.setCurrency(accountData.getCurrency());
        if (accountData.getName() != null) editedAccount.setName(accountData.getName());
        return accountRepository.save(editedAccount);
    }
}
