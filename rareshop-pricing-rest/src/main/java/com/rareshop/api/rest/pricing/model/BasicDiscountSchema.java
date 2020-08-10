package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.discount.DiscountSchema;

import java.util.Collections;
import java.util.List;

public class BasicDiscountSchema implements DiscountSchema<BasicProduct,BasicDiscountRule> {
    @Override
    public List<BasicDiscountRule> getApplicableRule(BasicProduct product) {
        return Collections.emptyList();
    }
}
