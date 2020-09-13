package misrraimsp.technest_rest_api.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.service.AccountServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountServer accountServer;

    @GetMapping("/accounts")
    public List<Account> allAccounts() {
        return accountServer.findAll();
    }
}
