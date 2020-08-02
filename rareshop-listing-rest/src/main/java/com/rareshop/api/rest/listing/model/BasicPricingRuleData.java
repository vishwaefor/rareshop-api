package com.rareshop.api.rest.listing.model;

import java.util.Objects;

public class BasicPricingRuleData {

    private int priorityScore;
    private long unitId;
    private double unitPrice;
    private boolean additionalPricingFactorUsed;
    private double additionalPricingFactor;

    public BasicPricingRuleData() {
    }

    public int getPriorityScore() {
        return priorityScore;
    }

    public void setPriorityScore(int priorityScore) {
        this.priorityScore = priorityScore;
    }


    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public boolean isAdditionalPricingFactorUsed() {
        return additionalPricingFactorUsed;
    }

    public void setAdditionalPricingFactorUsed(boolean additionalPricingFactorUsed) {
        this.additionalPricingFactorUsed = additionalPricingFactorUsed;
    }

    public double getAdditionalPricingFactor() {
        return additionalPricingFactor;
    }

    public void setAdditionalPricingFactor(double additionalPricingFactor) {
        this.additionalPricingFactor = additionalPricingFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicPricingRuleData that = (BasicPricingRuleData) o;
        return priorityScore == that.priorityScore &&
                Double.compare(that.unitPrice, unitPrice) == 0 &&
                additionalPricingFactorUsed == that.additionalPricingFactorUsed &&
                Double.compare(that.additionalPricingFactor, additionalPricingFactor) == 0;

    }

    @Override
    public int hashCode() {
        return Objects.hash(priorityScore, unitId, unitPrice, additionalPricingFactorUsed, additionalPricingFactor);
    }

    @Override
    public String toString() {
        return "BasicPricingRuleData{" +
                ", priorityScore=" + priorityScore +
                ", unitId='" + unitId + '\'' +
                ", unitPrice=" + unitPrice +
                ", additionalPricingFactorUsed=" + additionalPricingFactorUsed +
                ", additionalPricingFactor=" + additionalPricingFactor +
                '}';
    }
}
