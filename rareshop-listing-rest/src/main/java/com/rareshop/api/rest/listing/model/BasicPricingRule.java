package com.rareshop.api.rest.listing.model;

import rareshop.api.common.core.pricing.PricingRule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class BasicPricingRule implements PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int priorityScore;
    private String unit;
    private double unitPrice;
    private int quantityInPrimaryUnit;
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
    public String getUnit() {
        return unit;
    }

    @Override
    public double getUnitPrice() {
        return unitPrice;
    }

    @Override
    public int getQuantityInPrimaryUnit() {
        return quantityInPrimaryUnit;
    }

    @Override
    public boolean isAdditionalPricingFactorUsed() {
        return additionalPricingFactorUsed;
    }

    @Override
    public double getAdditionalPricingFactor() {
        return additionalPricingFactor;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPriorityScore(int priorityScore) {
        this.priorityScore = priorityScore;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantityInPrimaryUnit(int quantityInPrimaryUnit) {
        this.quantityInPrimaryUnit = quantityInPrimaryUnit;
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
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BasicPricingRule{" +
                "id=" + id +
                ", priorityScore=" + priorityScore +
                ", unit='" + unit + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantityInPrimaryUnit=" + quantityInPrimaryUnit +
                ", additionalPricingFactorUsed=" + additionalPricingFactorUsed +
                ", additionalPricingFactor=" + additionalPricingFactor +
                '}';
    }
}
