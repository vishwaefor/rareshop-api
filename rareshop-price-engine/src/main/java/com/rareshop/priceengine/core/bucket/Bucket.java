/*
 * Copyright (c) @Vishwa 2020.
 */
/*
 * Bucket is the set of items to be purchased.
 * Price Engine is taking a Bucket for its calculations
 */
package com.rareshop.priceengine.core.bucket;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface Bucket<I extends BucketItem> {

    Set<I> getItems();

    default double getFinalPrice() {
        return Optional.ofNullable(getItems()).orElse(Collections.emptySet())
                .stream()
                .filter(Objects::nonNull)
                .filter(I::isFinalPriceCalculated)
                .map(I::getFinalPrice)
                .reduce(0D, Double::sum);
    }

}
