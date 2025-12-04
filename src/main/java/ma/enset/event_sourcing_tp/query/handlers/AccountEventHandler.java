package ma.enset.event_sourcing_tp.query.handlers;

import lombok.extern.slf4j.Slf4j;
import ma.enset.event_sourcing_tp.event.AccountCreatedEvent;
import ma.enset.event_sourcing_tp.event.AccountCreditedEvent;
import ma.enset.event_sourcing_tp.query.entites.Account;
import ma.enset.event_sourcing_tp.query.repository.AccountRepository;
import ma.enset.event_sourcing_tp.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventHandler {

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountEventHandler(OperationRepository operationRepository, AccountRepository accountRepository) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("=============== Query Side AccountCreatedEvent Recived ===============");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .status(event.getStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();

        accountRepository.save(account);
    }


}

