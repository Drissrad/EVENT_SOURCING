package ma.enset.event_sourcing_tp.commands.dto;

public record AddNewAccountRequestDTO(double initialBalance, String currency) {
}
