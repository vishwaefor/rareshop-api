package com.rareshop.api.rest.listing.model;

import rareshop.api.common.core.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class BasicProduct implements
        Product<BasicProductInfo, BasicPricingRule, BasicDiscountRule> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private BasicProductInfo basicProductInfo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private List<BasicPricingRule> basicPricingRules;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private List<BasicDiscountRule> basicDiscountRules;

    private boolean published;

    public BasicProduct() {
        this.basicPricingRules = new ArrayList<>();
        this.basicDiscountRules = new ArrayList<>();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public BasicProductInfo getProductInfo() {
        return basicProductInfo;
    }

    @Override
    public List<BasicPricingRule> getPricingRules() {
        return basicPricingRules;
    }

    @Override
    public List<BasicDiscountRule> getDiscountRules() {
        return basicDiscountRules;
    }

    @Override
    public boolean isPublished() {
        return published;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BasicProductInfo getBasicProductInfo() {
        return basicProductInfo;
    }

    public void setBasicProductInfo(BasicProductInfo basicProductInfo) {
        this.basicProductInfo = basicProductInfo;
    }

    public List<BasicPricingRule> getBasicPricingRules() {
        return basicPricingRules;
    }

    public void setBasicPricingRules(List<BasicPricingRule> basicPricingRules) {
        this.basicPricingRules = basicPricingRules;
    }

    public List<BasicDiscountRule> getBasicDiscountRules() {
        return basicDiscountRules;
    }

    public void setBasicDiscountRules(List<BasicDiscountRule> basicDiscountRules) {
        this.basicDiscountRules = basicDiscountRules;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicProduct that = (BasicProduct) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BasicProduct{" +
                "id=" + id +
                ", basicProductInfo=" + basicProductInfo +
                ", basicPricingRules=" + basicPricingRules +
                ", basicDiscountRules=" + basicDiscountRules +
                ", published=" + published +
                '}';
    }
}
