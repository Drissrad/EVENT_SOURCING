package ma.enset.event_sourcing_tp.commands.dto;

import ma.enset.event_sourcing_tp.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(
        String accountId,
        AccountStatus status
) {
}

