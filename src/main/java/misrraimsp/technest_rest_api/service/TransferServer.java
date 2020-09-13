package misrraimsp.technest_rest_api.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.data.TransferRepository;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.model.Transfer;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransferServer {

    private final TransferRepository transferRepository;
    private final AccountServer accountServer;

    public Transfer doTransfer(Transfer transfer) {
        Account accountFrom = accountServer.findById(transfer.getFrom().getId());
        Account accountTo = accountServer.findById(transfer.getTo().getId());
        accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
        accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));
        accountServer.save(accountFrom);
        accountServer.save(accountTo);
        return transferRepository.save(transfer);
    }
}
