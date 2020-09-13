package misrraimsp.technest_rest_api.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Transfer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Account from;

    @ManyToOne
    private Account to;

    private BigDecimal amount;
}
