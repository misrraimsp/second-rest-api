package misrraimsp.technest_rest_api.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.service.AccountServer;
import misrraimsp.technest_rest_api.util.AccountModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountServer accountServer;
    private final AccountModelAssembler accountModelAssembler;

    @GetMapping("/accounts")
    public CollectionModel<EntityModel<Account>> allAccounts() {
        List<EntityModel<Account>> entityModels = accountServer.findAll()
                .stream()
                .map(accountModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                entityModels,
                linkTo(methodOn(AccountController.class).allAccounts()).withSelfRel()
        );
    }

    @GetMapping("/accounts/{accountId}")
    public EntityModel<Account> oneAccount(@PathVariable Long accountId) {
        return accountModelAssembler.toModel(accountServer.findById(accountId));
    }
}
