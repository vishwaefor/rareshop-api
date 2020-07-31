/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.bucket;

import rareshop.api.common.core.pricing.PriceCalculationEntry;
import rareshop.api.common.core.product.Product;

import java.util.List;

/**
 * Bucket Item represents a n item of the shopping bucket
 * There is a Product here
 * Quantity of the product is kept
 * Only one BucketItem per product can be exist
 */
public interface BucketItem<P extends Product> {

    default long getId() {
        return getProduct().getId();
    }

    P getProduct();

    int getQuantityInPrimaryUnits(); // Assume full product should be sold and fractions are not allowed.

    boolean isPricingRulesApplied();

    void markPricingRulesApplied();

    boolean isDiscountRulesApplied();

    void markDiscountRulesApplied();

    default boolean isFinalPriceCalculated() {
        return isPricingRulesApplied() && isDiscountRulesApplied();
    }

    void addPriceCalculationEntry(PriceCalculationEntry entry); //used by Price Engine

    List<PriceCalculationEntry> getPriceCalculationEntries();

    default double getGrossPrice() {
        return getPriceCalculationEntries().stream()
                .map(entry -> entry.getPrimaryUnitPrice() * entry.getQuantityInPrimaryUnit())
                .reduce(0D, Double::sum);
    }

    default double getDiscountedAMount() {
        return 0D;

    }

    default double getFinalPrice() {
        return getGrossPrice() - getDiscountedAMount();
    }

}
