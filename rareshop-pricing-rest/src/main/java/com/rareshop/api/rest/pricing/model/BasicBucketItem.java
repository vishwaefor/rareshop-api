package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.bucket.Bucket;
import rareshop.api.common.core.bucket.BucketItem;
import rareshop.api.common.core.discount.DiscountCalculationEntry;
import rareshop.api.common.core.pricing.PriceCalculationEntry;

import java.util.ArrayList;
import java.util.List;

public class BasicBucketItem implements BucketItem<BasicProduct,BasicUnit> {

    private BasicProduct product;
    private int quantity;
    private BasicUnit unit;
    private boolean pricingRulesApplied;
    private boolean discountRulesApplied;
    private List<PriceCalculationEntry> priceCalculationEntries;
    private List<DiscountCalculationEntry> discountCalculationEntries;

    private double grossPrice;
    private double discountAmount;
    private double netPrice;

    public BasicBucketItem() {
        this.priceCalculationEntries = new ArrayList<>();
        this.discountCalculationEntries = new ArrayList<>();
        grossPrice = -1;
        discountAmount = -1;
        netPrice = -1;
    }

    @Override
    public BasicProduct getProduct() {
        return product;
    }

    public BasicBucketItem(BasicProduct product, BasicUnit unit,int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unit = unit;
    }

    @Override
    public int getPurchasedQuantityInUnit() {
        return quantity;
    }

    @Override
    public boolean isPricingRulesApplied() {
        return pricingRulesApplied;
    }

    @Override
    public void markPricingRulesApplied() {
        this.pricingRulesApplied = true;
    }

    @Override
    public boolean isDiscountRulesApplied() {
        return discountRulesApplied;
    }

    @Override
    public void markDiscountRulesApplied() {
        this.discountRulesApplied = true;
    }

    @Override
    public void addPriceCalculationEntry(PriceCalculationEntry entry) {
        this.priceCalculationEntries.add(entry);
    }

    @Override
    public void addDiscountCalculationEntry(DiscountCalculationEntry entry) {
        this.discountCalculationEntries.add(entry);
    }

    @Override
    public List<PriceCalculationEntry> getPriceCalculationEntries() {
        return priceCalculationEntries;
    }

    @Override
    public List<DiscountCalculationEntry> getDiscountCalculationEntries() {
        return discountCalculationEntries;
    }

    @Override
    public BasicUnit getUnit() {
        return unit;
    }

    @Override
    public double getGrossPrice() {
        return grossPrice<0? BucketItem.super.getGrossPrice():grossPrice;
    }

    @Override
    public double getDiscountedAMount() {
        return discountAmount<0? BucketItem.super.getDiscountedAMount():discountAmount;
    }

    @Override
    public double getFinalPrice() {
        return netPrice<0?BucketItem.super.getFinalPrice():netPrice;
    }
}
