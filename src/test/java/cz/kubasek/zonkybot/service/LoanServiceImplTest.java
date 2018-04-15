package cz.kubasek.zonkybot.service;

import cz.kubasek.zonkybot.client.MarketplaceClient;
import cz.kubasek.zonkybot.model.Loan;
import cz.kubasek.zonkybot.repository.LoanRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static cz.kubasek.zonkybot.model.LoanTest.aTestLoan;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoanServiceImplTest {

    private static final int initialLoansToLoad = 3;

    private MarketplaceClient marketplaceClient;
    private LoanRepository loanRepository;

    private LoanServiceImpl loanService;

    @Before
    public void setUp() {
        marketplaceClient = mock(MarketplaceClient.class);
        loanRepository = mock(LoanRepository.class);

        loanService = new LoanServiceImpl(marketplaceClient, loanRepository, initialLoansToLoad);
    }

    @Test
    public void whenIntialised_thenInitialLoansAreLoadedAndSavedToRepo() {
        List<Loan> initialLoansLoaded = asList(aTestLoan(1), aTestLoan(2), aTestLoan(3));
        given(marketplaceClient.findLatestPublishedLoans(eq(initialLoansToLoad))).willReturn(initialLoansLoaded);

        loanService.loadInitialLatestPublishedLoans();

        verify(marketplaceClient).findLatestPublishedLoans(eq(initialLoansToLoad));
        verify(loanRepository).addAll(eq(initialLoansLoaded));
    }

    @Test
    public void whenNewlyPublishedLoansFound_thenNewLoansGetSavedToRepo() {
        loanService.loadInitialLatestPublishedLoans();
        given(loanRepository.findTheLatestOne()).willReturn(of(aTestLoan(1)));
        List<Loan> newlyPublishedLoans = asList(aTestLoan(3), aTestLoan(2));
        given(marketplaceClient.findNewlyPublishedLoansSince(any())).willReturn(newlyPublishedLoans);

        loanService.checkForNewlyPublishedLoans();

        verify(loanRepository).findTheLatestOne();
        verify(marketplaceClient).findNewlyPublishedLoansSince(any());
        verify(loanRepository).addAll(eq(newlyPublishedLoans));
    }

}