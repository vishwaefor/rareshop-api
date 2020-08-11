package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.pricing.PricingRule;

import java.util.Objects;

public class BasicPricingRule implements PricingRule<BasicUnit> {

    private long id;
    private int priorityScore;
    private double unitPrice;
    private BasicUnit unit;
    private boolean additionalPricingFactorUsed;
    private double additionalPricingFactor;

    public BasicPricingRule() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getPriorityScore() {
        return priorityScore;
    }

    @Override
    public double getUnitPrice() {
        return unitPrice;
    }

    @Override
    public boolean isAdditionalPricingFactorUsed() {
        return additionalPricingFactorUsed;
    }

    @Override
    public double getAdditionalPricingFactor() {
        return additionalPricingFactor;
    }

    @Override
    public BasicUnit getUnit() {
        return unit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPriorityScore(int priorityScore) {
        this.priorityScore = priorityScore;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setUnit(BasicUnit unit) {
        this.unit = unit;
    }

    public void setAdditionalPricingFactorUsed(boolean additionalPricingFactorUsed) {
        this.additionalPricingFactorUsed = additionalPricingFactorUsed;
    }

    public void setAdditionalPricingFactor(double additionalPricingFactor) {
        this.additionalPricingFactor = additionalPricingFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicPricingRule that = (BasicPricingRule) o;
        return id == that.id &&
                priorityScore == that.priorityScore &&
                Double.compare(that.unitPrice, unitPrice) == 0 &&
                additionalPricingFactorUsed == that.additionalPricingFactorUsed &&
                Double.compare(that.additionalPricingFactor, additionalPricingFactor) == 0 &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priorityScore, unitPrice, unit, additionalPricingFactorUsed, additionalPricingFactor);
    }

    @Override
    public String toString() {
        return "BasicPricingRule{" +
                "id=" + id +
                ", priorityScore=" + priorityScore +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                ", additionalPricingFactorUsed=" + additionalPricingFactorUsed +
                ", additionalPricingFactor=" + additionalPricingFactor +
                '}';
    }
}
