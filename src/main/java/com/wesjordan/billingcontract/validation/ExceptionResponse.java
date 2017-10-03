package com.wesjordan.billingcontract.validation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExceptionResponse {

    private String errorCode;
    private String errorMessage;
    private List<String> errors;
}
