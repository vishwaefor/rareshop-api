/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.pricing;

/**
 * Price calculation entries are added to the bucket item by the price engine
 * This entries are aggregated to find the final gross price (without discounts)
 */
public final class PriceCalculationEntry {

    private long ruleId;
    private String description;
    private double primaryUnitPrice;
    private int quantityInPrimaryUnit;


    public PriceCalculationEntry(long ruleId, String description,
                                 double primaryUnitPrice, int quantityInPrimaryUnit) {
        this.ruleId = ruleId;
        this.description = description;
        this.primaryUnitPrice = primaryUnitPrice;
        this.quantityInPrimaryUnit = quantityInPrimaryUnit;
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
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
