/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core.product;

import com.rareshop.priceengine.core.discount.DiscountRule;
import com.rareshop.priceengine.core.pricing.PricingRule;

import java.util.List;

/**
 * Product is going to have Product Info, Pricing Rules and Discount Rules
 */
public interface Product<I extends ProductInfo, R extends PricingRule, D extends DiscountRule> {

    String getId();

    I getProductInfo();

    List<R> getPricingRules();

    List<D> getDiscountRules();
}
