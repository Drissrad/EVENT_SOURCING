package ma.enset.event_sourcing_tp.query.controllers;

import ma.enset.event_sourcing_tp.query.dto.AccountStatementResponseDTO;
import ma.enset.event_sourcing_tp.query.entites.Account;
import ma.enset.event_sourcing_tp.query.entites.AccountOperation;
import ma.enset.event_sourcing_tp.query.queries.GetALLAccountsQuery;
import ma.enset.event_sourcing_tp.query.queries.GetAccountStatementQuery;
import ma.enset.event_sourcing_tp.query.queries.WatchEventQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/query/accounts")
@Controller
public class AccountQueryController {

    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccount(){
        CompletableFuture<List<Account>> response = queryGateway.query(
                new GetALLAccountsQuery(),
                ResponseTypes.multipleInstancesOf(Account.class)
        );
        return response;
    }
    @GetMapping("/accountStatement/{accountId}")
    public CompletableFuture<AccountStatementResponseDTO> getAccountStatement(@PathVariable String accountId){
        return queryGateway.query(new GetAccountStatementQuery(accountId),
                ResponseTypes.instanceOf(AccountStatementResponseDTO.class));
    }
    @GetMapping(value = "/watch/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountOperation> watch(@PathVariable String accountId){
        SubscriptionQueryResult<AccountOperation, AccountOperation> result =
                queryGateway.subscriptionQuery(new WatchEventQuery(accountId),
                        ResponseTypes.instanceOf(AccountOperation.class),
                        ResponseTypes.instanceOf(AccountOperation.class)
                );
        return result.initialResult().concatWith(result.updates());
    }


}

