package com.transunion.rise.webservices.exceptions;

import com.transunion.rise.webservices.enums.ResponseCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceRequestException extends RuntimeException {
    private ResponseCode responseCode;
    public ServiceRequestException(String message) {
        super(message);
    }

    public ServiceRequestException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public ServiceRequestException(Throwable cause) {
        super(cause);
    }

    public ServiceRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
