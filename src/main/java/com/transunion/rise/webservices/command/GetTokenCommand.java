package com.transunion.rise.webservices.command;

import com.transunion.rise.webservices.LoginOauthRequest;
import com.transunion.rise.webservices.pattern.command.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
@Builder
public class GetTokenCommand extends Command<String> {
    private final LoginOauthRequest request;
}
