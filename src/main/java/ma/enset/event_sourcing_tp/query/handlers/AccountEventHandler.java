package ma.enset.event_sourcing_tp.query.handlers;

import lombok.extern.slf4j.Slf4j;
import ma.enset.event_sourcing_tp.event.*;
import ma.enset.event_sourcing_tp.query.entites.Account;
import ma.enset.event_sourcing_tp.query.entites.AccountOperation;
import ma.enset.event_sourcing_tp.query.entites.OperationType;
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
                .currency(event.getCurrency())
                .createdAt(eventMessage.getTimestamp())
                .build();

        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("=============== Query Side AccountActivatedEvent Recived ===============");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountStatusUpdatedEvent event){
        log.info("=============== Query Side accountStatusUpdatedEvent Recived ===============");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage){
        log.info("=============== Query Side AccountDebitedEvent Recived ===============");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.DEBIT)
                .currency(event.getCurrency())
                .account(account)
                .build();

        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() - accountOperation.getAmount());
        accountRepository.save(account);
    }
    public void on(AccountCreditedEvent event, EventMessage eventMessage){
        log.info("=============== Query Side AccountCreditedEvent  Recived ===============");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.CREDIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() + accountOperation.getAmount());
        accountRepository.save(account);
    }





}

