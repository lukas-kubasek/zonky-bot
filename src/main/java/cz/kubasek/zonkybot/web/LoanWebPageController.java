package cz.kubasek.zonkybot.web;

import cz.kubasek.zonkybot.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoanWebPageController {

    private LoanService loanService;

    public LoanWebPageController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/")
    public String loanListWebPage(Model model) {
        model.addAttribute("loans", loanService.getLatestPublishedLoans());
        return "loans";
    }
}
