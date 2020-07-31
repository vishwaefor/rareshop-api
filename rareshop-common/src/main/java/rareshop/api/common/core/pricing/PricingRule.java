/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.pricing;

import rareshop.api.common.core.exception.InvalidArgumentException;

/**
 * Pricing rule is used to price an item.
 * Multiple rules can be applied with priorities
 * The rule can be set by any unit
 * The price for unit is then converted to primary unit
 * using the quantity of items in the unit in primary units
 * ex: Carton contains 20 primary units and carton is $100.00
 * The primary unit price becomes $100.00/20
 * There is a factor for multiplying the price if required
 * This is useful when primary units are sold instead of cartons
 * A rule can be defined with unit = primary unit and unit price = $100/20
 * But with an additional factor = 1.5
 * which would make final unit price = 1.5 * $100/20
 */
public interface PricingRule {

    long getId();

    default String getDescription() {
        return getUnit() + " of " + getQuantityInPrimaryUnit()
                + " - primary units @ price : " + getUnitPrice()
                + " / primary units price : " + getPrimaryUnitPrice();

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
