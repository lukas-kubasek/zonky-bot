package cz.kubasek.zonkybot.web;

import cz.kubasek.zonkybot.model.Loan;
import cz.kubasek.zonkybot.service.LoanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoanRestController {

    private LoanService loanService;

    public LoanRestController(LoanService loanService) {
        this.loanService = loanService;
    }

    @RequestMapping("/loan/latest")
    public List<Loan> getLatestLoans() {
        return loanService.getLatestPublishedLoans();
    }
}
