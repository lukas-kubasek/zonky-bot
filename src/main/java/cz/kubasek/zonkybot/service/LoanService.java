package cz.kubasek.zonkybot.service;

import cz.kubasek.zonkybot.model.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> getLatestPublishedLoans();

    List<Loan> checkForNewlyPublishedLoans();
}
