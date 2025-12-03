package ma.enset.event_sourcing_tp.commands.dto;

public record DebitAccountRequestDTO(String accountId, double amount, String currency) {
}

