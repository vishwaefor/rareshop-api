/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.discount;

/**
 * Discount calculation entries are added to the bucket item by the price engine
 * This entries are aggregated to find the final discounted amount
 */
public final class DiscountCalculationEntry {

    private long ruleId;
    private String description;
    private double subjectedAmount;
    private double discountPercentage;

    public DiscountCalculationEntry(
            long ruleId, String description, double subjectedAmount, double discountPercentage) {
        this.ruleId = ruleId;
        this.description = description;
        this.subjectedAmount = subjectedAmount;
        this.discountPercentage = discountPercentage;
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

    public double getSubjectedAmount() {
        return subjectedAmount;
    }

    public void setSubjectedAmount(double subjectedAmount) {
        this.subjectedAmount = subjectedAmount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
