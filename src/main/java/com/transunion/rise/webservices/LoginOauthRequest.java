package com.transunion.rise.webservices;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginOauthRequest {
    private String application;
    private String subscriber;
    private String username;
    private String password;
}
