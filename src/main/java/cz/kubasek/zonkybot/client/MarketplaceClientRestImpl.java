package cz.kubasek.zonkybot.client;

import cz.kubasek.zonkybot.model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class MarketplaceClientRestImpl implements MarketplaceClient {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;

    private String marketplaceApiUri;
    private String userAgent;

    public MarketplaceClientRestImpl(RestTemplateBuilder restTemplateBuilder,
                                     @Value("${app.marketplace-api.uri}") String marketplaceApiUri,
                                     @Value("${app.marketplace-api.user-agent}") String userAgent) {
        this.restTemplate = restTemplateBuilder.build();
        this.marketplaceApiUri = marketplaceApiUri;
        this.userAgent = userAgent;
    }

    @Override
    public List<Loan> findLatestPublishedLoans(int loanCount) {
        log.debug("Reading {} latest published loans on Marketplace", loanCount);

        HttpHeaders httpHeaders = buildHttpHeaders();
        httpHeaders.set("X-Size", valueOf(loanCount));
        URI uri = fromHttpUrl(marketplaceApiUri).build(true).toUri();

        return doExchange(uri, httpHeaders);
    }

    @Override
    public List<Loan> findNewlyPublishedLoansSince(OffsetDateTime sinceTimestamp) {
        log.debug("Reading newly published loans on Marketplace since {}", sinceTimestamp);

        HttpHeaders httpHeaders = buildHttpHeaders();
        URI uriWithFilterParams = fromHttpUrl(marketplaceApiUri)
                .queryParam("datePublished__gt", UriUtils.encode(sinceTimestamp.toString(), "UTF-8"))
                .build(true).toUri();

        return doExchange(uriWithFilterParams, httpHeaders);
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", userAgent);
        headers.set("X-Order", "-datePublished");
        return headers;
    }

    private List<Loan> doExchange(URI uri, HttpHeaders httpHeaders) {
        ResponseEntity<Loan[]> responseEntity = restTemplate.exchange(uri, GET, new HttpEntity<String>(httpHeaders), Loan[].class);
        Loan[] returnedLoans = responseEntity.getBody();
        return returnedLoans != null ? asList(returnedLoans) : Collections.emptyList();
    }
}
