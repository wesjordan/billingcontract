package com.wesjordan.billingcontract.validation.errors;

import org.springframework.stereotype.Component;
import org.springframework.validation.MessageCodesResolver;

@Component
public class SimpleMessageCodeResolver implements MessageCodesResolver{

    @Override
    public String[] resolveMessageCodes(String s, String s1) {
        return new String[]{s};
    }

    @Override
    public String[] resolveMessageCodes(String s, String s1, String s2, Class<?> aClass) {
        return new String[]{s};
    }
}
