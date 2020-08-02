package com.rareshop.api.rest.listing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import rareshop.api.common.core.discount.AppliedFor;
import rareshop.api.common.core.discount.DiscountRule;
import rareshop.api.common.core.unit.Unit;

import javax.persistence.*;

@Entity
public class BasicDiscountRule implements DiscountRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private BasicUnit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private BasicProduct basicProduct;

    public BasicDiscountRule() {
    }

    public long getId() {
        return id;
    }

    @Override
    public AppliedFor getAppliedFor() {
        return null;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BasicProduct getBasicProduct() {
        return basicProduct;
    }

    public void setBasicProduct(BasicProduct basicProduct) {
        this.basicProduct = basicProduct;
    }

    @Override
    public double getDiscountPercentage() {
        return 0;
    }

    @Override
    public int getMinimumRequiredQuantityInUnit() {
        return 0;
    }

    @Override
    public int getPriorityScore() {
        return 0;
    }

    @Override
    public Unit getUnit() {
        return null;
    }
}
