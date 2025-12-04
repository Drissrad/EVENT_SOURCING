package ma.enset.event_sourcing_tp.query.handlers;

import ma.enset.event_sourcing_tp.query.dto.AccountStatementResponseDTO;
import ma.enset.event_sourcing_tp.query.entites.Account;
import ma.enset.event_sourcing_tp.query.entites.AccountOperation;
import ma.enset.event_sourcing_tp.query.queries.GetALLAccountsQuery;
import ma.enset.event_sourcing_tp.query.queries.GetAccountStatementQuery;
import ma.enset.event_sourcing_tp.query.queries.WatchEventQuery;
import ma.enset.event_sourcing_tp.query.repository.AccountRepository;
import ma.enset.event_sourcing_tp.query.repository.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }
    @QueryHandler
    public List<Account> on(GetALLAccountsQuery query){
        return accountRepository.findAll();
    }
    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query){
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<AccountOperation> operations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account, operations);
    }
    @QueryHandler
    public AccountOperation on(WatchEventQuery query){
        return AccountOperation.builder().build();
    }


}

