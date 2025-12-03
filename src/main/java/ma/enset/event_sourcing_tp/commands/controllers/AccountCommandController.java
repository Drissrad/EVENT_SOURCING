package ma.enset.event_sourcing_tp.commands.controllers;

import ma.enset.event_sourcing_tp.commands.commands.AddAccountCommand;
import ma.enset.event_sourcing_tp.commands.commands.CreditAccountCommand;
import ma.enset.event_sourcing_tp.commands.commands.DebitAccountCommand;
import ma.enset.event_sourcing_tp.commands.commands.UpdateAccountStatusCommand;
import ma.enset.event_sourcing_tp.commands.dto.AddNewAccountRequestDTO;
import ma.enset.event_sourcing_tp.commands.dto.CreditAccountRequestDTO;
import ma.enset.event_sourcing_tp.commands.dto.DebitAccountRequestDTO;
import ma.enset.event_sourcing_tp.commands.dto.UpdateAccountStatusRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request) {

        CompletableFuture<String> response = commandGateway.send(
                new AddAccountCommand(
                        UUID.randomUUID().toString(),
                        request.initialBalance(),
                        request.currency()
                )
        );

        return response;
    }
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception) {
        return exception.getMessage();
    }
    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }
    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(
                new CreditAccountCommand(
                        UUID.randomUUID().toString(),
                        request.amount(),
                        request.currency()
                )
        );
        return response;
    }
    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(
                new DebitAccountCommand(
                        request.accountId(),
                        request.amount(),
                        request.currency()
                )
        );
        return response;
    }
    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateStatus(@RequestBody UpdateAccountStatusRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(
                new UpdateAccountStatusCommand(
                        request.accountId(),
                        request.status()
                )
        );
        return response;
    }



}


