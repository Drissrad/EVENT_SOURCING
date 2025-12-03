package ma.enset.event_sourcing_tp.commands.dto;

public record CreditAccountRequestDTO(String accountId, double amount, String currency) {
}
