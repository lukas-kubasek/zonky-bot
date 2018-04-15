package cz.kubasek.zonkybot.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import cz.kubasek.zonkybot.model.Loan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Simple thread safe non-persistent Loan Repository implementation for the interview task purposes.
 * Implements an in-memory repository backed by a circular buffer with the given configurable size.
 */
@Repository
public class LoanRepositoryInMemCircularBufferImpl implements LoanRepository {

    private Deque<Loan> ringBuffer = Queues.synchronizedDeque(new LinkedList<>());
    private int bufferMaxSize;

    public LoanRepositoryInMemCircularBufferImpl(@Value("${app.loans.in-mem-repo-size}") int bufferSize) {
        this.bufferMaxSize = bufferSize;
    }

    @Override
    public void add(Loan loan) {
        ringBuffer.addFirst(loan);
        evictLastOneIfBufferReachesThreshold();
    }

    @Override
    public void addAll(List<Loan> loans) {
        Lists.reverse(loans).forEach(this::add);
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(ringBuffer);
    }

    @Override
    public Optional<Loan> findTheLatestOne() {
        Loan latestLoan = ringBuffer.peekFirst();
        return latestLoan != null ? Optional.of(latestLoan) : Optional.empty();
    }

    private void evictLastOneIfBufferReachesThreshold() {
        if (ringBuffer.size() > bufferMaxSize) {
            ringBuffer.removeLast();
        }
    }
}
