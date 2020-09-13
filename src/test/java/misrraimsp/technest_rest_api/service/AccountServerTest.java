package misrraimsp.technest_rest_api.service;


import misrraimsp.technest_rest_api.data.AccountRepository;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.util.exception.EntityNotFoundByIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServerTest {

    private AccountServer accountServer;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void initializeData(){
        accountServer = new AccountServer(accountRepository);
        account = new Account();
    }

    private void setAccount(Long id, String name, Currency currency, BigDecimal balance, boolean treasury) {
        account.setId(id);
        account.setName(name);
        account.setCurrency(currency);
        account.setBalance(balance);
        account.setTreasury(treasury);
    }

    @Test
    public void findById_whenExists_returnsTheAccount() {
        // given
        Long accountId = 1L;
        setAccount(accountId,"testAccount",Currency.getInstance("EUR"),BigDecimal.ONE,false);
        given(accountRepository.findById(accountId)).willReturn(Optional.of(account));
        // when
        Account actualAccount = accountServer.findById(accountId);
        // then
        assertThat(actualAccount).isEqualTo(account);
    }

    @Test
    public void findById_whenNotExists_throwsEntityNotFoundByIdException() {
        // given
        Long accountId = 1L;
        given(accountRepository.findById(any(Long.class))).willReturn(Optional.empty());
        // when
        try {
            accountServer.findById(accountId);
        }
        // then
        catch (EntityNotFoundByIdException e) {
            assertThat(e.getMessage()).isEqualTo("Entity of class " + e.getClassName() + " not found by id=" + accountId);
        }
    }

    @Test
    public void save_whenValidInput_returnsTheAccount() {
        // given
        Long accountId = 1L;
        setAccount(accountId,"anotherTestAccount",Currency.getInstance("USD"),BigDecimal.ZERO,true);
        given(accountRepository.save(account)).willReturn(account);
        // when
        Account actualAccount = accountServer.save(account);
        // then
        assertThat(actualAccount).isEqualTo(account);
    }

    @Test
    public void findAll_whenCalled_returnsAccountRepositoryOutput() {
        // given
        List<Account> accounts = new ArrayList<>();
        given(accountRepository.findAll()).willReturn(accounts);
        // when
        List<Account> actualAccounts = accountServer.findAll();
        // then
        assertThat(actualAccounts).isEqualTo(accounts);
    }

    @Test
    public void editById_whenCalledWithoutTreasuryData_editTheAccountAndReturnsIt() {
        // given
        Long accountId = 1L;
        setAccount(accountId,"testEditAccount",Currency.getInstance("USD"),BigDecimal.ZERO,false);
        Account accountData = new Account();
        accountData.setName("editedNameForTestAccount");
        accountData.setCurrency(Currency.getInstance("EUR"));
        accountData.setBalance(BigDecimal.ONE);
        given(accountRepository.findById(accountId)).willReturn(Optional.of(account));
        given(accountRepository.save(account)).willReturn(account);
        // when
        Account actualAccount = accountServer.editById(accountId, accountData);
        // then
        assertThat(actualAccount.getCurrency()).isEqualTo(accountData.getCurrency());
        assertThat(actualAccount.getBalance()).isEqualTo(accountData.getBalance());
        assertThat(actualAccount.getName()).isEqualTo(accountData.getName());
        assertThat(actualAccount.isTreasury()).isEqualTo(false);
        assertThat(actualAccount.getId()).isEqualTo(accountId);
    }

    @Test
    public void editById_whenCalledWithTreasuryData_doNotEditTreasury() {
        // given
        Long accountId = 1L;
        setAccount(accountId,null,null,null,false);
        Account accountData = new Account();
        accountData.setTreasury(true);
        given(accountRepository.findById(accountId)).willReturn(Optional.of(account));
        given(accountRepository.save(account)).willReturn(account);
        // when
        Account actualAccount = accountServer.editById(accountId, accountData);
        // then
        assertThat(actualAccount.isTreasury()).isEqualTo(false);
    }

}