package ma.enset.event_sourcing_tp.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.enset.event_sourcing_tp.enums.AccountStatus;

@Getter
@AllArgsConstructor
public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private AccountStatus status;
    private String currency;
}
