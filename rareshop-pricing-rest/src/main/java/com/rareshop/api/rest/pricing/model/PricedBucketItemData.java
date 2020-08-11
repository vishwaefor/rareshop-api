package com.rareshop.api.rest.pricing.model;

import java.util.Objects;

public class PricedBucketItemData {

    private long id;
    private double grossPrice;
    private double discountAmount;
    private double netPrice;
    private String explanation;

    public PricedBucketItemData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricedBucketItemData itemData = (PricedBucketItemData) o;
        return id == itemData.id &&
                Double.compare(itemData.grossPrice, grossPrice) == 0 &&
                Double.compare(itemData.discountAmount, discountAmount) == 0 &&
                Double.compare(itemData.netPrice, netPrice) == 0 &&
                Objects.equals(explanation, itemData.explanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grossPrice, discountAmount, netPrice, explanation);
    }

    @Override
    public String toString() {
        return "PricedBucketItemData{" +
                "id=" + id +
                ", grossPrice=" + grossPrice +
                ", discountAmount=" + discountAmount +
                ", netPrice=" + netPrice +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
