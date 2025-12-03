package ma.enset.event_sourcing_tp.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.enset.event_sourcing_tp.enums.AccountStatus;

@Getter
@AllArgsConstructor
public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus status;
}

