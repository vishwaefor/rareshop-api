/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core.pricing;

import com.rareshop.priceengine.core.exception.InvalidArgumentException;

import java.util.concurrent.atomic.AtomicReference;

public interface PricingRule {

    String getId();

    default String getDescription(){
        return getUnit()+ " of "+ getQuantityInPrimaryUnit()
                +" - primary units @ price : "+ getUnitPrice()
                +" / primary units price : "+ getPrimaryUnitPrice();

    }

    int getPriorityScore();

    String getUnit();

    double getUnitPrice();

    int getQuantityInPrimaryUnit();

    boolean isAdditionalPricingFactorUsed();

    double getAdditionalPricingFactor();

    default double getPrimaryUnitPrice() {

        if (getUnitPrice() < 0 || getQuantityInPrimaryUnit() < 0) {
            throw new InvalidArgumentException();
        }

        double unitPrice = getUnitPrice() / Math.max(1, getQuantityInPrimaryUnit());

        if (isAdditionalPricingFactorUsed()) {

            if (getAdditionalPricingFactor() < 1) {
                throw new InvalidArgumentException();
            }

            unitPrice *= Math.max(1, getAdditionalPricingFactor());
        }

        return Math.max(0D, unitPrice);
    }

}
