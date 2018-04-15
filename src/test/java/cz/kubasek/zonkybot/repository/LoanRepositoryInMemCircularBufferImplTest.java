package cz.kubasek.zonkybot.repository;

import org.junit.Before;
import org.junit.Test;

import static cz.kubasek.zonkybot.model.LoanTest.aTestLoan;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;

public class LoanRepositoryInMemCircularBufferImplTest {

    private LoanRepository loanRepository;

    @Before
    public void setUp() {
        loanRepository = new LoanRepositoryInMemCircularBufferImpl(2);
    }

    @Test
    public void whenLoanAdded_andBufferIsFull_thenEvictsOldestElement() {
        assertThat(loanRepository.findAll()).isEmpty();

        loanRepository.add(aTestLoan(1));
        assertThat(loanRepository.findAll()).containsExactly(aTestLoan(1));

        loanRepository.add(aTestLoan(2));
        assertThat(loanRepository.findAll()).containsExactly(aTestLoan(2), aTestLoan(1));

        loanRepository.add(aTestLoan(3));
        assertThat(loanRepository.findAll()).containsExactly(aTestLoan(3), aTestLoan(2));

        loanRepository.addAll(asList(aTestLoan(5), aTestLoan(4)));
        assertThat(loanRepository.findAll()).containsExactly(aTestLoan(5), aTestLoan(4));
    }

    @Test
    public void whenLoanAdded_thenLatestOneReturned() {
        assertThat(loanRepository.findTheLatestOne()).isEmpty();

        loanRepository.add(aTestLoan(1));
        assertThat(loanRepository.findTheLatestOne()).isEqualTo(of(aTestLoan(1)));

        loanRepository.add(aTestLoan(2));
        assertThat(loanRepository.findTheLatestOne()).isEqualTo(of(aTestLoan(2)));

        loanRepository.add(aTestLoan(3));
        assertThat(loanRepository.findTheLatestOne()).isEqualTo(of(aTestLoan(3)));

        loanRepository.addAll(asList(aTestLoan(5), aTestLoan(4)));
        assertThat(loanRepository.findTheLatestOne()).isEqualTo(of(aTestLoan(5)));
    }
}