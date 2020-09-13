package misrraimsp.technest_rest_api.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.data.TransferRepository;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.model.Transfer;
import misrraimsp.technest_rest_api.util.exception.NotEnoughFundsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TransferServer {

    private final TransferRepository transferRepository;
    private final AccountServer accountServer;

    @Transactional(propagation= Propagation.NESTED, rollbackFor = Exception.class)
    public Transfer doTransfer(Transfer transfer) throws NotEnoughFundsException {
        Account accountFrom = accountServer.findById(transfer.getFrom().getId());
        if (!accountFrom.isTreasury() && accountFrom.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new NotEnoughFundsException(accountFrom.getId());
        }
        Account accountTo = accountServer.findById(transfer.getTo().getId());
        accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
        accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));
        accountServer.save(accountFrom);
        accountServer.save(accountTo);
        return transferRepository.save(transfer);
    }
}
