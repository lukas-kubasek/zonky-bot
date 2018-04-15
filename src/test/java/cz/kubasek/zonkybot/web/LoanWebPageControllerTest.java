package cz.kubasek.zonkybot.web;

import cz.kubasek.zonkybot.service.LoanService;
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
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanWebPageController.class)
public class LoanWebPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Test
    public void whenLoanWebPageIsOpened_thenLatestLoansAreDisplayedAsHtml() throws Exception {
        given(loanService.getLatestPublishedLoans()).willReturn(asList(
                aTestLoan(1), aTestLoan(2)
        ));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("Zonky Loan Bot")))
                .andExpect(content().string(containsString("Loan 1")))
                .andExpect(content().string(containsString("Loan 2")));
    }

}