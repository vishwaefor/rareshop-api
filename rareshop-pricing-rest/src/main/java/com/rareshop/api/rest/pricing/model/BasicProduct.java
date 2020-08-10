package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.product.Product;

import java.util.ArrayList;
import java.util.List;

public class BasicProduct implements Product<BasicProductInfo,BasicUnit,BasicPricingRule,BasicDiscountRule> {

    private long id;
    private BasicProductInfo productInfo;
    private String name;
    private boolean published;
    private List<BasicUnit> units;
    private List<BasicPricingRule> pricingRules;
    private List<BasicDiscountRule> discountRules;

    public BasicProduct() {

        this.units = new ArrayList<>();
        this.pricingRules = new ArrayList<>();
        this.discountRules = new ArrayList<>();

    }

    public BasicProduct(long id) {
        this();
        this.id = id;
     }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BasicProductInfo getProductInfo(){
        return productInfo;
    }

    @Override
    public List<BasicUnit> getUnits() {
        return units;
    }

    @Override
    public List<BasicPricingRule> getPricingRules() {
        return pricingRules;
    }

    @Override
    public List<BasicDiscountRule> getDiscountRules() {
        return discountRules;
    }

    @Override
    public boolean isPublished() {
        return published;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProductInfo(BasicProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setUnits(List<BasicUnit> units) {
        this.units = units;
    }

    public void setPricingRules(List<BasicPricingRule> pricingRules) {
        this.pricingRules = pricingRules;
    }

    public void setDiscountRules(List<BasicDiscountRule> discountRules) {
        this.discountRules = discountRules;
    }
}
