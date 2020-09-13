package misrraimsp.technest_rest_api.util.exception;

public class NotEnoughFundsException extends IllegalArgumentException {

    public NotEnoughFundsException(Long accountId) {
        super("Not enough funds in no-treasury account with id=" + accountId + ". The transfer has been aborted.");
    }

}
