package misrraimsp.technest_rest_api.data;

import misrraimsp.technest_rest_api.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
}
