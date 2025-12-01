package ma.enset.event_sourcing_tp.commands.controllers;

import ma.enset.event_sourcing_tp.commands.commands.AddAccountCommand;
import ma.enset.event_sourcing_tp.commands.dto.AddNewAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {

    private CommandGateway commandGateway;

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
}


