package com.transunion.rise.webservices.pattern.command;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
public class CommandBusHandler {
    private final CommandProvider commandProvider;

    @SuppressWarnings("unchecked")
    @ServiceActivator(inputChannel = "commandChannel")
    public <R> R executeCommand(Command<R> command) throws ExecutionException {
        return (R) commandProvider.get(command.getClass()).handle(command);
    }
}