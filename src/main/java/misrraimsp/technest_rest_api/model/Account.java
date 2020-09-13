package misrraimsp.technest_rest_api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Currency currency;

    private boolean treasury;

    private BigDecimal balance;
}
