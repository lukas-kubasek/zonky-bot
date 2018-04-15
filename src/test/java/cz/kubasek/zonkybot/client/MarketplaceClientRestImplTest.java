package cz.kubasek.zonkybot.client;

import cz.kubasek.zonkybot.model.Loan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarketplaceClientRestImplTest {

    @Autowired
    private MarketplaceClient marketplaceClient;

    @Test
    public void whenLatestLoansLoaded_thenAllLoanPropertiesPopulated() {
        List<Loan> latestPublishedLoans = marketplaceClient.findLatestPublishedLoans(5);
        assertThat(latestPublishedLoans).hasSize(5);

        latestPublishedLoans.forEach(loan -> {
            assertThat(loan.getId()).isPositive();
            assertThat(loan.getUrl()).matches(Pattern.compile("https://app.zonky.cz/loan/[0-9]+"));
            assertThat(loan.getName()).isNotBlank();
            assertThat(loan.getTermInMonths()).isPositive();
            assertThat(loan.getInterestRate()).isPositive();
            assertThat(loan.getRating()).isNotBlank();
            assertThat(loan.getAmount()).isPositive();
            assertThat(loan.getDatePublished()).isCloseTo(OffsetDateTime.now(), byLessThan(1, WEEKS));
            assertThat(loan.getDeadline()).isAfter(loan.getDatePublished());
        });
    }

    @Test
    public void whenLatestLoansLoaded_thenResultIsOrderedDescByPublishedTimestamp() {
        List<Loan> loans = marketplaceClient.findLatestPublishedLoans(3);
        assertThat(loans).hasSize(3);

        assertThat(loans.get(0).getDatePublished()).isAfter(loans.get(1).getDatePublished());
        assertThat(loans.get(1).getDatePublished()).isAfter(loans.get(2).getDatePublished());
    }

    @Test
    public void whenNewlyPublishedLoansAvailable_thenClientReturnsThem() {
        OffsetDateTime last24Hours = OffsetDateTime.now().minus(24, HOURS);
        List<Loan> newLoans = marketplaceClient.findNewlyPublishedLoansSince(last24Hours);
        assertThat(newLoans.size()).isPositive();
    }

    @Test
    public void whenTwoNewlyPublishedLoansAvailable_thenTwoNewLoansAreReturned() {
        List<Loan> threeLatestPublishedLoans = marketplaceClient.findLatestPublishedLoans(3);
        assertThat(threeLatestPublishedLoans).hasSize(3);

        Loan thirdLatestLoan = threeLatestPublishedLoans.get(2);
        List<Loan> newLoans = marketplaceClient.findNewlyPublishedLoansSince(thirdLatestLoan.getDatePublished());

        assertThat(newLoans).hasSize(2);
        assertThat(newLoans.get(0).getId()).isEqualTo(threeLatestPublishedLoans.get(0).getId());
        assertThat(newLoans.get(1).getId()).isEqualTo(threeLatestPublishedLoans.get(1).getId());
    }

}