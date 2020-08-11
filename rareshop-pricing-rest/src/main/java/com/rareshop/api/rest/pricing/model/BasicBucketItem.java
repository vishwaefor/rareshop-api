package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.bucket.BucketItem;
import rareshop.api.common.core.discount.DiscountCalculationEntry;
import rareshop.api.common.core.pricing.PriceCalculationEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if (grossPrice < 0) {
            grossPrice =  BucketItem.super.getGrossPrice();
        }
        return grossPrice;
    }

    @Override
    public double getDiscountedAMount() {
        if (discountAmount < 0) {
            discountAmount =  BucketItem.super.getDiscountedAMount();
        }
        return discountAmount;
    }

    @Override
    public double getFinalPrice() {
        if (netPrice < 0){
            netPrice =  BucketItem.super.getFinalPrice();
        }
        return netPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicBucketItem that = (BasicBucketItem) o;
        return quantity == that.quantity &&
                pricingRulesApplied == that.pricingRulesApplied &&
                discountRulesApplied == that.discountRulesApplied &&
                Double.compare(that.grossPrice, grossPrice) == 0 &&
                Double.compare(that.discountAmount, discountAmount) == 0 &&
                Double.compare(that.netPrice, netPrice) == 0 &&
                Objects.equals(product, that.product) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(priceCalculationEntries, that.priceCalculationEntries) &&
                Objects.equals(discountCalculationEntries, that.discountCalculationEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, unit, pricingRulesApplied, discountRulesApplied, priceCalculationEntries, discountCalculationEntries, grossPrice, discountAmount, netPrice);
    }

    @Override
    public String toString() {
        return "BasicBucketItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", pricingRulesApplied=" + pricingRulesApplied +
                ", discountRulesApplied=" + discountRulesApplied +
                ", priceCalculationEntries=" + priceCalculationEntries +
                ", discountCalculationEntries=" + discountCalculationEntries +
                ", grossPrice=" + grossPrice +
                ", discountAmount=" + discountAmount +
                ", netPrice=" + netPrice +
                '}';
    }
}
