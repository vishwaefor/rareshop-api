package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.discount.AppliedFor;
import rareshop.api.common.core.discount.DiscountRule;

import java.util.Objects;

public class BasicDiscountRule implements DiscountRule<BasicUnit> {

    private long id;
    private AppliedFor appliedFor;
    private double discountPercentage;
    private int minimumRequiredQuantityInUnit;
    private BasicUnit unit;
    private int priorityScore;

    public BasicDiscountRule() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public AppliedFor getAppliedFor() {
        return appliedFor;
    }

    @Override
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    @Override
    public int getMinimumRequiredQuantityInUnit() {
        return minimumRequiredQuantityInUnit;
    }

    @Override
    public int getPriorityScore() {
        return priorityScore;
    }

    @Override
    public BasicUnit getUnit() {
        return unit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAppliedFor(AppliedFor appliedFor) {
        this.appliedFor = appliedFor;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setMinimumRequiredQuantityInUnit(int minimumRequiredQuantityInUnit) {
        this.minimumRequiredQuantityInUnit = minimumRequiredQuantityInUnit;
    }

    public void setUnit(BasicUnit unit) {
        this.unit = unit;
    }

    public void setPriorityScore(int priorityScore) {
        this.priorityScore = priorityScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicDiscountRule that = (BasicDiscountRule) o;
        return id == that.id &&
                Double.compare(that.discountPercentage, discountPercentage) == 0 &&
                minimumRequiredQuantityInUnit == that.minimumRequiredQuantityInUnit &&
                priorityScore == that.priorityScore &&
                appliedFor == that.appliedFor &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appliedFor, discountPercentage, minimumRequiredQuantityInUnit, unit, priorityScore);
    }

    @Override
    public String toString() {
        return "BasicDiscountRule{" +
                "id=" + id +
                ", appliedFor=" + appliedFor +
                ", discountPercentage=" + discountPercentage +
                ", minimumRequiredQuantityInUnit=" + minimumRequiredQuantityInUnit +
                ", unit=" + unit +
                ", priorityScore=" + priorityScore +
                '}';
    }
}
