package cz.kubasek.zonkybot.client;

import cz.kubasek.zonkybot.model.Loan;

import java.time.OffsetDateTime;
import java.util.List;

public interface MarketplaceClient {

    List<Loan> findLatestPublishedLoans(int count);

    List<Loan> findNewlyPublishedLoansSince(OffsetDateTime sinceTimestamp);
}
