package misrraimsp.technest_rest_api.controller;


import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.service.AccountServer;
import misrraimsp.technest_rest_api.util.AccountModelAssembler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountModelAssembler accountModelAssembler;

    @MockBean
    private AccountServer accountServer;

    @Test
    public void allAccounts_whenValidMethodAndUrlAndPathVariable_thenReturns200() throws Exception {
        
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk());
    }

    @Test
    public void allAccounts_whenValidInput_thenCallsAccountServerFindAll() throws Exception {
        mockMvc.perform(get("/accounts"));
        verify(accountServer, times(1)).findAll();
    }

    @Test
    public void oneAccount_whenValidMethodAndUrlAndPathVariable_thenReturns200() throws Exception {
        Long accountId = 1L;
        mockMvc.perform(get("/accounts/{accountId}",accountId))
                .andExpect(status().isOk());
    }

    @Test
    public void oneAccount_whenValidInput_thenCallsAccountServerFindById() throws Exception {
        // given
        Long accountId = 1L;
        // when
        mockMvc.perform(get("/accounts/{accountId}", accountId));
        // then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(accountServer, times(1)).findById(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(accountId);
    }

    @Test
    void oneAccount_whenValidInput_thenReturnsRequestedAccountResource() throws Exception {
        // given
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setName("testAccount");
        account.setCurrency(Currency.getInstance("EUR"));
        account.setBalance(BigDecimal.ONE);
        account.setTreasury(false);
        EntityModel<Account> accountEntityModel = EntityModel.of(
                account,
                linkTo(methodOn(AccountController.class).oneAccount(account.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).allAccounts()).withRel("accounts")
        );
        given(accountServer.findById(any(Long.class))).willReturn(account);
        given(accountModelAssembler.toModel(account)).willReturn(accountEntityModel);
        // when
        ResultActions resultActions = mockMvc.perform(get("/accounts/{accountId}", accountId));
        // then
        resultActions.andExpect(jsonPath("id").value(accountEntityModel.getContent().getId()));
        resultActions.andExpect(jsonPath("name").value(accountEntityModel.getContent().getName()));
        resultActions.andExpect(jsonPath("currency").value(accountEntityModel.getContent().getCurrency().toString()));
        resultActions.andExpect(jsonPath("balance").value(accountEntityModel.getContent().getBalance()));
        resultActions.andExpect(jsonPath("treasury").value(accountEntityModel.getContent().isTreasury()));
        resultActions.andExpect(jsonPath("_links.self.href").value(accountEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri().toString()));
        resultActions.andExpect(jsonPath("_links.accounts.href").value(accountEntityModel.getRequiredLink("accounts").toUri().toString()));
    }

}