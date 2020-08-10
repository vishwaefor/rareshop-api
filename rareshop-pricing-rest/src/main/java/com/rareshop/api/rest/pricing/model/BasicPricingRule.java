package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.pricing.PricingRule;

public class BasicPricingRule implements PricingRule<BasicUnit> {
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public int getPriorityScore() {
        return 0;
    }

    @Override
    public double getUnitPrice() {
        return 0;
    }

    @Override
    public boolean isAdditionalPricingFactorUsed() {
        return false;
    }

    @Override
    public double getAdditionalPricingFactor() {
        return 0;
    }

    @Override
    public BasicUnit getUnit() {
        return null;
    }
}
