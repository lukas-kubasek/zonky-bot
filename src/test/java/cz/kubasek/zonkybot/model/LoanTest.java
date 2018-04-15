package cz.kubasek.zonkybot.model;

import org.junit.Test;

import java.time.OffsetDateTime;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class LoanTest {

    @Test
    public void twoLoansWithSameIdAreConsideredAsEqual() {
        Loan loan1 = aTestLoan(1);
        Loan loan2 = aTestLoan(1);
        assertThat(loan1).isEqualTo(loan2);
    }

    public static Loan aTestLoan(long loanId) {
        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setName(format("Loan %d", loanId));
        loan.setDatePublished(OffsetDateTime.now());
        return loan;
    }

}
