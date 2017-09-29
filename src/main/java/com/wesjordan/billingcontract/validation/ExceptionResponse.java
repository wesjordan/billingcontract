package com.wesjordan.billingcontract.validation;

import lombok.Data;

import java.util.List;

@Data
class ExceptionResponse {

    private String errorCode;
    private String errorMessage;
    private List<String> errors;
}
