package com.transunion.rise.webservices.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "responseCode")
@XmlEnum
public enum ResponseCode {
    OK,
    ERR_INDIVIDUAL_NOT_FOUND,
    ERR_UNKNOWN_VARIABLE,
    ERR_COMMUNICATIONS_FAILURE,
    ERR_AUTH,
    ERR_INVALID_STRING,
    ERR_INVALID_PARAMS,
    ERR_GENERAL_ERROR,
    ERR_BILLING_PARAMS,
    ERR_INQUIRY_INVALID,
    ERR_IDENTIFICATION_INVALID;

    public String value() {
        return this.name();
    }

    public static ResponseCode fromValue(String v) {
        return valueOf(v);
    }
}
