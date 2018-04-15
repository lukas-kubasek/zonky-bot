package cz.kubasek.zonkybot.scheduler;

import cz.kubasek.zonkybot.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class NewlyPublishedLoanScheduledTask {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private LoanService loanService;

    public NewlyPublishedLoanScheduledTask(LoanService loanService) {
        this.loanService = loanService;
    }

    @Scheduled(fixedDelayString = "${app.loans.new-loan-check-interval-millis}")
    public void checkForNewLoans() {
        log.debug("Scheduler executed at {} to check for newly published loans", Instant.now());
        loanService.checkForNewlyPublishedLoans();
    }
}
