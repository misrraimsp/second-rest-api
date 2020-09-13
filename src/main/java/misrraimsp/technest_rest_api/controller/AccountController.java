package misrraimsp.technest_rest_api.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.service.AccountServer;
import misrraimsp.technest_rest_api.util.AccountModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/accounts")
    ResponseEntity<?> newAccount(@RequestBody Account account) {
        EntityModel<Account> accountEntityModel = accountModelAssembler.toModel(accountServer.save(account));
        return ResponseEntity
                .created(accountEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //location header
                .body(accountEntityModel);
    }

    @PutMapping("/accounts/{accountId}")
    ResponseEntity<?> editAccount(@RequestBody Account accountData, @PathVariable Long accountId) {
        Account editedAccount = accountServer.editById(accountId, accountData);
        EntityModel<Account> accountEntityModel = accountModelAssembler.toModel(editedAccount);
        return ResponseEntity
                .created(accountEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(accountEntityModel);
    }
}
