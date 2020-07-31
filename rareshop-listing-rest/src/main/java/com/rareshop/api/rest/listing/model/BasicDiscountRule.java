package com.rareshop.api.rest.listing.model;

import rareshop.api.common.core.discount.DiscountRule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BasicDiscountRule implements DiscountRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public BasicDiscountRule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
