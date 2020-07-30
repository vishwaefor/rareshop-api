/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core.pricing;

public final class PriceCalculationEntry {

    private String ruleId;
    private String description;
    private double primaryUnitPrice;
    private int quantityInPrimaryUnit;


    public PriceCalculationEntry(String ruleId, String description,
                                 double primaryUnitPrice, int quantityInPrimaryUnit) {
        this.ruleId = ruleId;
        this.description = description;
        this.primaryUnitPrice = primaryUnitPrice;
        this.quantityInPrimaryUnit = quantityInPrimaryUnit;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrimaryUnitPrice() {
        return primaryUnitPrice;
    }

    public void setPrimaryUnitPrice(double primaryUnitPrice) {
        this.primaryUnitPrice = primaryUnitPrice;
    }

    public int getQuantityInPrimaryUnit() {
        return quantityInPrimaryUnit;
    }

    public void setQuantityInPrimaryUnit(int quantityInPrimaryUnit) {
        this.quantityInPrimaryUnit = quantityInPrimaryUnit;
    }
}
