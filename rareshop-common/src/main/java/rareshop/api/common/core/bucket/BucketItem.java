/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.bucket;

import rareshop.api.common.core.discount.DiscountCalculationEntry;
import rareshop.api.common.core.pricing.PriceCalculationEntry;
import rareshop.api.common.core.product.Product;
import rareshop.api.common.core.unit.HasUnit;
import rareshop.api.common.core.unit.Unit;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bucket Item represents a n item of the shopping bucket
 * There is a Product here
 * Quantity of the product is kept
 * Only one BucketItem per product can be exist
 */
public interface BucketItem<P extends Product, U extends Unit> extends HasUnit {

    default long getId() {
        return getProduct().getId();
    }

    P getProduct();

    int getPurchasedQuantityInUnit();

    default int getPurchasedQuantityInPrimaryUnit() {

        return getPurchasedQuantityInUnit() * getUnitQuantityInPrimaryUnit();
    }

    boolean isPricingRulesApplied();

    void markPricingRulesApplied();

    boolean isDiscountRulesApplied();

    void markDiscountRulesApplied();

    default boolean isFinalPriceCalculated() {
        return isPricingRulesApplied() && isDiscountRulesApplied();
    }

    void addPriceCalculationEntry(PriceCalculationEntry entry); //used by Price Engine

    void addDiscountCalculationEntry(DiscountCalculationEntry entry); //used by Price Engine

    List<PriceCalculationEntry> getPriceCalculationEntries();

    List<DiscountCalculationEntry> getDiscountCalculationEntries();

    default double getGrossPrice() {
        return getPriceCalculationEntries().stream()
                .map(entry -> entry.getPrimaryUnitPrice() * entry.getQuantityInPrimaryUnit())
                .reduce(0D, Double::sum);
    }

    default double getDiscountedAMount() {
        return getDiscountCalculationEntries().stream()
                .map(entry -> entry.getSubjectedAmount() * entry.getDiscountPercentage() / 100)
                .reduce(0D, Double::sum);

    }

    default double getFinalPrice() {
        return getGrossPrice() - getDiscountedAMount();
    }

    default String explain() {
        StringBuilder finalPriceCalculation = new StringBuilder("\n\n-------------------------------------\n");
        finalPriceCalculation.append("\n*** Item Price Calculation *** \n");

        finalPriceCalculation.append("\nItem : ")
                .append(getPurchasedQuantityInUnit()).append(" ").append(getUnitName());

        finalPriceCalculation.append("\n\nGross Price Calculation Entries\n");
        finalPriceCalculation.append(getPriceCalculationEntries().stream().map(e ->
                "\nRule : " + e.getDescription()
                        + "\n= " + e.getQuantityInPrimaryUnit() + " x " + e.getPrimaryUnitPrice())
                .collect(Collectors.joining("\n"))
        );

        finalPriceCalculation.append("\n\nDiscount Calculation Entries\n");
        finalPriceCalculation.append(getDiscountCalculationEntries().stream().map(e ->
                "\nRule : " + e.getDescription()
                        + "\n= " + e.getSubjectedAmount() + " x " + e.getDiscountPercentage() + "%")
                .collect(Collectors.joining("\n"))
        );

        finalPriceCalculation.append("\n\nGross Price : ").append(getGrossPrice());
        finalPriceCalculation.append("\nDiscounted Amount : ").append(getDiscountedAMount());
        finalPriceCalculation.append("\n\nNet Amount :").append(getFinalPrice()).append(" //\n");

        return finalPriceCalculation.toString();
    }
}
