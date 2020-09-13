package misrraimsp.technest_rest_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import misrraimsp.technest_rest_api.data.AccountRepository;
import misrraimsp.technest_rest_api.data.TransferRepository;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.model.Transfer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TechnestRestApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void doTransfer_whenItIsEnoughFunds_doTheTransfer() throws Exception {
        // given
        Account from = new Account();
        from.setId(1L);
        from.setName("from");
        from.setCurrency(Currency.getInstance("EUR"));
        from.setTreasury(false);
        from.setBalance(BigDecimal.valueOf(2));
        accountRepository.save(from);

        Account to = new Account();
        to.setId(2L);
        to.setName("to");
        to.setCurrency(Currency.getInstance("EUR"));
        to.setTreasury(false);
        to.setBalance(BigDecimal.valueOf(2));
        accountRepository.save(to);

        Transfer transfer = new Transfer();
        transfer.setFrom(from);
        transfer.setTo(to);
        transfer.setAmount(BigDecimal.ONE);

        // when
        mockMvc.perform(post("/transfers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transfer)));

        // then
        assertThat(accountRepository.findById(from.getId()).get().getBalance()).isEqualTo("1.00");
        assertThat(accountRepository.findById(to.getId()).get().getBalance()).isEqualTo("3.00");
    }

    @Test
    void doTransfer_whenItIsEnoughFunds_saveTheTransfer() throws Exception {
        // given
        Account from = new Account();
        from.setId(1L);
        from.setName("from");
        from.setCurrency(Currency.getInstance("EUR"));
        from.setTreasury(false);
        from.setBalance(BigDecimal.valueOf(2));
        accountRepository.save(from);

        Account to = new Account();
        to.setId(2L);
        to.setName("to");
        to.setCurrency(Currency.getInstance("EUR"));
        to.setTreasury(false);
        to.setBalance(BigDecimal.valueOf(2));
        accountRepository.save(to);

        Transfer transfer = new Transfer();
        transfer.setFrom(from);
        transfer.setTo(to);
        transfer.setAmount(BigDecimal.ONE);

        // when
        MvcResult result = mockMvc.perform(post("/transfers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transfer)))
                .andReturn();
        Transfer actualTransfer = objectMapper.readValue(result.getResponse().getContentAsString(), Transfer.class);

        // then
        assertThat(transferRepository.findById(actualTransfer.getId()).get().getTo().getId()).isEqualTo(2L);
        assertThat(transferRepository.findById(actualTransfer.getId()).get().getFrom().getId()).isEqualTo(1L);
        assertThat(transferRepository.findById(actualTransfer.getId()).get().getAmount()).isEqualTo("1.00");
    }

    @Test
    void doTransfer_whenItIsNotEnoughFundsAndNotTreasuryAccount_abortTheTransfer() throws Exception {
        // given
        Account from = new Account();
        from.setId(1L);
        from.setName("from");
        from.setCurrency(Currency.getInstance("EUR"));
        from.setTreasury(false);
        from.setBalance(BigDecimal.ZERO);
        accountRepository.save(from);

        Account to = new Account();
        to.setId(2L);
        to.setName("to");
        to.setCurrency(Currency.getInstance("EUR"));
        to.setTreasury(false);
        to.setBalance(BigDecimal.ONE);
        accountRepository.save(to);

        transferRepository.deleteAll();
        Transfer transfer = new Transfer();
        transfer.setFrom(from);
        transfer.setTo(to);
        transfer.setAmount(BigDecimal.ONE);

        // when
        MvcResult result = mockMvc.perform(post("/transfers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transfer)))
                .andReturn();
        // then
        assertThat(accountRepository.findById(from.getId()).get().getBalance()).isEqualTo("0.00");
        assertThat(accountRepository.findById(to.getId()).get().getBalance()).isEqualTo("1.00");
        assertThat(transferRepository.findAll().isEmpty()).isEqualTo(true);
    }
}
