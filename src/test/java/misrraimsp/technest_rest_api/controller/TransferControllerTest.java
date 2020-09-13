package misrraimsp.technest_rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import misrraimsp.technest_rest_api.model.Account;
import misrraimsp.technest_rest_api.model.Transfer;
import misrraimsp.technest_rest_api.service.TransferServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransferController.class)
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransferServer transferServer;

    private static Account from;
    private static Account to;
    private static Transfer transfer;

    @BeforeAll
    static void initializeData() {
        from = new Account();
        from.setId(1L);
        from.setName("from");
        from.setCurrency(Currency.getInstance("USD"));
        from.setTreasury(false);
        to = new Account();
        to.setId(2L);
        to.setName("to");
        to.setCurrency(Currency.getInstance("USD"));
        to.setTreasury(false);
        transfer = new Transfer();
        transfer.setId(1L);
        transfer.setFrom(from);
        transfer.setTo(to);
        transfer.setAmount(BigDecimal.valueOf(100.00));
    }

    @BeforeEach
    public void reset() {
        from.setBalance(BigDecimal.valueOf(100.00));
        to.setBalance(BigDecimal.valueOf(50.00));
    }

    @Test
    void newTransfer_whenValidMethodAndUrlAndContentTypeAndRequestBody_thenReturns200() throws Exception {
        // given
        // ...
        // when
        mockMvc.perform(post("/transfers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transfer)))
        // then
                .andExpect(status().isOk());
    }

    @Test
    void newTransfer_whenValidInput_thenCallsDoTransfer() throws Exception {
        // given
        // ...
        // when
        mockMvc.perform(post("/transfers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transfer))
        );
        // then
        ArgumentCaptor<Transfer> transferArgumentCaptor = ArgumentCaptor.forClass(Transfer.class);
        verify(transferServer, times(1)).doTransfer(transferArgumentCaptor.capture());
        assertThat(transferArgumentCaptor.getValue().getAmount()).isEqualTo(BigDecimal.valueOf(100.00));
        assertThat(transferArgumentCaptor.getValue().getId()).isEqualTo(1L);
        assertThat(transferArgumentCaptor.getValue().getFrom().getId()).isEqualTo(1L);
        assertThat(transferArgumentCaptor.getValue().getTo().getId()).isEqualTo(2L);
    }

    @Test
    void newTransfer_whenValidInput_thenReturnsRequestBody() throws Exception {
        // given
        given(transferServer.doTransfer(transfer)).willReturn(transfer);
        // when
        MvcResult mvcResult = mockMvc.perform(post("/transfers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transfer)))
                .andReturn();
        // then
        String expectedResponseBody = objectMapper.writeValueAsString(transfer);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

}