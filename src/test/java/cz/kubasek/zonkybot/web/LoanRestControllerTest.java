package cz.kubasek.zonkybot.web;

import cz.kubasek.zonkybot.service.LoanService;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static cz.kubasek.zonkybot.model.LoanTest.aTestLoan;
import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanRestController.class)
public class LoanRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Test
    public void whenFindLatest_thenReturnLoansAsJson() throws Exception {
        given(loanService.getLatestPublishedLoans()).willReturn(asList(
                aTestLoan(1), aTestLoan(2), aTestLoan(3)
        ));

        mockMvc.perform(get("/loan/latest")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Loan 1")))
                .andExpect(jsonPath("$[1].name", CoreMatchers.is("Loan 2")))
                .andExpect(jsonPath("$[2].name", CoreMatchers.is("Loan 3")));
    }

}