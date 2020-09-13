package misrraimsp.technest_rest_api.controller;

import lombok.RequiredArgsConstructor;
import misrraimsp.technest_rest_api.model.Transfer;
import misrraimsp.technest_rest_api.service.TransferServer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TransferController {

    private final TransferServer transferServer;

    @PostMapping("/transfers")
    ResponseEntity<?> newTransfer(@RequestBody Transfer transfer) {
        Transfer newTransfer = transferServer.doTransfer(transfer);
        return ResponseEntity.ok(newTransfer);
    }
}
