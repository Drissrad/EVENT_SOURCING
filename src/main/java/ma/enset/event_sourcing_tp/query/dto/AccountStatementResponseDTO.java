package ma.enset.event_sourcing_tp.query.dto;

import ma.enset.event_sourcing_tp.query.entites.Account;
import ma.enset.event_sourcing_tp.query.entites.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(
        Account account,
        List<AccountOperation> operations
) {
}
