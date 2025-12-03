package ma.enset.event_sourcing_tp.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.enset.event_sourcing_tp.commands.commands.AddAccountCommand;
import ma.enset.event_sourcing_tp.commands.commands.CreditAccountCommand;
import ma.enset.event_sourcing_tp.enums.AccountStatus;
import ma.enset.event_sourcing_tp.event.AccountActivatedEvent;
import ma.enset.event_sourcing_tp.event.AccountCreatedEvent;
import ma.enset.event_sourcing_tp.event.AccountCreditedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    // Required by Axon
    public AccountAggregate() {}


    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info(" AddAccountCom recevied" );
        if (command.getInitialBalance() <= 0)
            throw new IllegalArgumentException("Balance must be positive");

        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));

    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info(" ACountE EVENT OCURED " );
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand command) {

        log.info("########### CreditAccountCommand Received ###########");

        if (!status.equals(AccountStatus.ACTIVATED)) {
            throw new RuntimeException("The account " + command.getId() + " is not activated");
        }

        if (command.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                status,
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        log.info("############### AccountCreditedEvent occurred ###############");

        this.accountId = event.getAccountId();
        this.balance = this.balance + event.getAmount();
    }



}
