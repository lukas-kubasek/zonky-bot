package cz.kubasek.zonkybot.service;

import cz.kubasek.zonkybot.client.MarketplaceClient;
import cz.kubasek.zonkybot.model.Loan;
import cz.kubasek.zonkybot.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private MarketplaceClient marketplaceClient;
    private LoanRepository loanRepository;

    private int initialLoansToLoadCount;

    public LoanServiceImpl(MarketplaceClient marketplaceClient,
                           LoanRepository loanRepository,
                           @Value("${app.loans.initial-loans-to-load-count}") int initialLoansToLoadCount) {
        this.marketplaceClient = marketplaceClient;
        this.loanRepository = loanRepository;
        this.initialLoansToLoadCount = initialLoansToLoadCount;
    }

    @PostConstruct
    void loadInitialLatestPublishedLoans() {
        List<Loan> latestLoans = marketplaceClient.findLatestPublishedLoans(initialLoansToLoadCount);
        loanRepository.addAll(latestLoans);
        log.info("Loaded initial set of latest loans, count = {}", latestLoans.size());
    }

    @Override
    public List<Loan> getLatestPublishedLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> checkForNewlyPublishedLoans() {
        Optional<Loan> latestPublishedLoan = loanRepository.findTheLatestOne();
        if (!latestPublishedLoan.isPresent()) {
            log.warn("Initial set of latest loans has not been loaded yet");
            return Collections.emptyList();
        }
        return findNewLoansSinceTheLatestOneKnown(latestPublishedLoan.get());
    }

    private List<Loan> findNewLoansSinceTheLatestOneKnown(Loan latestKnownLoan) {
        OffsetDateTime latestKnownLoanPublished = latestKnownLoan.getDatePublished();
        List<Loan> newlyPublishedLoans = marketplaceClient.findNewlyPublishedLoansSince(latestKnownLoanPublished);
        if (newlyPublishedLoans.isEmpty()) {
            log.debug("No newly published loans have been found since {}", latestKnownLoanPublished);
        } else {
            log.info("Newly published loans found: {}", newlyPublishedLoans);
            loanRepository.addAll(newlyPublishedLoans);
        }
        return newlyPublishedLoans;
    }
}
