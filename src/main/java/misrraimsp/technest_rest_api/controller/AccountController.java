package misrraimsp.technest_rest_api.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.service.AccountServer;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountServer accountServer;

    @GetMapping("/accounts")
    public List<Account> allAccounts() {
        return accountServer.findAll();
    }

    @GetMapping("/accounts/{accountId}")
    public EntityModel<Account> oneAccount(@PathVariable Long accountId) {
        Account account = accountServer.findById(accountId);
        return EntityModel.of(
                account,
                linkTo(methodOn(AccountController.class).oneAccount(account.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).allAccounts()).withRel("accounts")
        );
    }
}
