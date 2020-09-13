package misrraimsp.technest_rest_api.data;

import misrraimsp.technest_rest_api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
