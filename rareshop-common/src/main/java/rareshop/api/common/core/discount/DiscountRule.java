/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.discount;

import rareshop.api.common.core.unit.HasUnit;
import rareshop.api.common.core.unit.Unit;

public interface DiscountRule<U extends Unit> extends HasUnit<U> {

    long getId();

    default String getDescription() {
        return "Purchase minimum " + getMinimumRequiredQuantityInUnit()
                + " " + getUnit().getName() + "[ "
                + getMinimumRequiredQuantityInPrimaryUnit()
                + " primary units ] and get " +
                getDiscountPercentage() + "% off from the "
                + getAppliedFor().getMessage();

    }

    AppliedFor getAppliedFor();

    double getDiscountPercentage();

    int getMinimumRequiredQuantityInUnit();

    default int getMinimumRequiredQuantityInPrimaryUnit() {
        return getMinimumRequiredQuantityInUnit() * getUnitQuantityInPrimaryUnit();
    }

    int getPriorityScore();


}
