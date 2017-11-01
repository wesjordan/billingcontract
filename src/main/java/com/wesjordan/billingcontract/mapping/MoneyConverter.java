package com.wesjordan.billingcontract.mapping;

import com.wesjordan.billingcontract.domain.Money;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class MoneyConverter extends CustomConverter<Money, Money> {
    @Override
    public Money convert(Money money, Type<? extends Money> type, MappingContext mappingContext) {
        Money m = new Money();
        m.setCurrency(money.getCurrency());
        m.setValue(money.getValue());
        return m;
    }
}
