package ma.enset.event_sourcing_tp.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.enset.event_sourcing_tp.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class UpdateAccountStatusCommand {

    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}
