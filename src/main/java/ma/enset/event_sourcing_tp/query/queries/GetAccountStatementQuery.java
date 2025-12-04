package ma.enset.event_sourcing_tp.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAccountStatementQuery {
    private String accountId;
}

