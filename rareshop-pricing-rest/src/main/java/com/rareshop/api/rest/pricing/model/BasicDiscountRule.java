package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.discount.AppliedFor;
import rareshop.api.common.core.discount.DiscountRule;

public class BasicDiscountRule implements DiscountRule<BasicUnit> {
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public AppliedFor getAppliedFor() {
        return null;
    }

    @Override
    public double getDiscountPercentage() {
        return 0;
    }

    @Override
    public int getMinimumRequiredQuantityInUnit() {
        return 0;
    }

    @Override
    public int getPriorityScore() {
        return 0;
    }

    @Override
    public BasicUnit getUnit() {
        return null;
    }
}
