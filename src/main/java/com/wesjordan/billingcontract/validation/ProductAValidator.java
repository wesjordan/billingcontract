package com.wesjordan.billingcontract.validation;

import com.wesjordan.billingcontract.domain.ProductA;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductAValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductA.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductA productA = (ProductA)o;
        if(productA.getAccountId() == null || productA.getAccountId().equals(0L)){
            errors.rejectValue("accountId", "The supplied accountId is invalid");
        }
    }
}
