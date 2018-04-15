package cz.kubasek.zonkybot.repository;

import cz.kubasek.zonkybot.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {

    void add(Loan loan);

    void addAll(List<Loan> loans);

    List<Loan> findAll();

    Optional<Loan> findTheLatestOne();
}
