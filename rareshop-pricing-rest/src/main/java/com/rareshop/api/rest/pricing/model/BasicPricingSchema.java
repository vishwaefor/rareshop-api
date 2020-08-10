package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.pricing.PricingSchema;

import java.util.Collections;
import java.util.List;

public class BasicPricingSchema implements PricingSchema<BasicProduct,BasicPricingRule> {
    @Override
    public List<BasicPricingRule> getApplicableRule(BasicProduct product) {
        return Collections.emptyList();
    }
}
