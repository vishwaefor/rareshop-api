package com.rareshop.api.rest.listing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import rareshop.api.common.core.pricing.PricingRule;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class BasicPricingRule implements PricingRule<BasicUnit> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int priorityScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private BasicUnit unit;

    private double unitPrice;
    private boolean additionalPricingFactorUsed;
    private double additionalPricingFactor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private BasicProduct basicProduct;

    public BasicPricingRule() {
    }

    public BasicPricingRule(BasicPricingRuleData basicPricingRule) {
        this();
        this.priorityScore = basicPricingRule.getPriorityScore();
        this.unit = new BasicUnit(basicPricingRule.getUnitId());
        this.unitPrice = basicPricingRule.getUnitPrice();
        this.additionalPricingFactorUsed = basicPricingRule.isAdditionalPricingFactorUsed();
        this.additionalPricingFactor = basicPricingRule.getAdditionalPricingFactor();
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
    public BasicUnit getUnit() {
        return unit;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setPriorityScore(int priorityScore) {
        this.priorityScore = priorityScore;
    }

    public void setUnit(BasicUnit unit) {
        this.unit = unit;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public void setAdditionalPricingFactorUsed(boolean additionalPricingFactorUsed) {
        this.additionalPricingFactorUsed = additionalPricingFactorUsed;
    }

    public void setAdditionalPricingFactor(double additionalPricingFactor) {
        this.additionalPricingFactor = additionalPricingFactor;
    }

    public BasicProduct getBasicProduct() {
        return basicProduct;
    }

    public void setBasicProduct(BasicProduct basicProduct) {
        this.basicProduct = basicProduct;
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
                ", additionalPricingFactorUsed=" + additionalPricingFactorUsed +
                ", additionalPricingFactor=" + additionalPricingFactor +
                ", basicProduct=" + basicProduct +
                '}';
    }
}
