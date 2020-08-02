package com.rareshop.api.rest.listing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import rareshop.api.common.core.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class BasicProduct implements
        Product<BasicProductInfo, BasicUnit, BasicPricingRule, BasicDiscountRule> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    private BasicProductInfo basicProductInfo;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    @JsonIgnore
    private List<BasicPricingRule> basicPricingRules;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    @JsonIgnore
    private List<BasicDiscountRule> basicDiscountRules;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    @JsonIgnore
    private List<BasicUnit> basicUnits;

    private boolean published;

    public BasicProduct() {
        this.basicPricingRules = new ArrayList<>();
        this.basicDiscountRules = new ArrayList<>();
        this.basicUnits = new ArrayList<>();
        this.basicUnits.add(new BasicUnit("Primary", 1));
    }

    public BasicProduct(BasicProductData product) {
        this();
        this.name = product.getName();
        this.basicProductInfo = new BasicProductInfo(product.getProductInfoId());
        this.basicUnits.addAll(product.getExtraUnits()
                .stream()
                .filter(Objects::nonNull)
                .filter(u -> !u.getName().isBlank())
                .filter(u -> u.getQuantityInPrimaryUnits() > 0)
                .map(u -> new BasicUnit(u.getName(), u.getQuantityInPrimaryUnits()))
                .collect(Collectors.toList()));
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
    public BasicProductInfo getProductInfo() {
        return basicProductInfo;
    }

    @Override
    public List<BasicUnit> getUnits() {
        return basicUnits;
    }

    public void addUnit(BasicUnit unit) {
        basicUnits.add(unit);
    }

    public void setUnits(List<BasicUnit> basicUnits) {
        this.basicUnits = basicUnits;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setProductInfo(BasicProductInfo basicProductInfo) {
        this.basicProductInfo = basicProductInfo;
    }


    public void setPricingRules(List<BasicPricingRule> basicPricingRules) {
        this.basicPricingRules = basicPricingRules;
    }

    public void setDiscountRules(List<BasicDiscountRule> basicDiscountRules) {
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
