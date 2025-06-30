package com.transunion.rise.webservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JwtToken {
    @Builder.Default
    private LocalDateTime issuedAt = LocalDateTime.now();
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private Long expiresIn;

    public LocalDateTime getExpirationTime() {
        return this.issuedAt.plusSeconds((this.expiresIn != null) ? this.expiresIn : 0);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(getExpirationTime());
    }

}
