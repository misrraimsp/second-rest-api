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
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServerTest {

    private AccountServer accountServer;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void initializeData(){
        accountServer = new AccountServer(accountRepository);
    }

    @Test
    public void findById_whenExists_returnsTheAccount() {
        // given
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setName("testAccount");
        account.setCurrency(Currency.getInstance("EUR"));
        account.setBalance(BigDecimal.ONE);
        account.setTreasury(false);
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

}