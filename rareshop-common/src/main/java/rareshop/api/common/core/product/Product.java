/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.product;

import rareshop.api.common.core.discount.DiscountRule;
import rareshop.api.common.core.pricing.PricingRule;

import java.util.List;

/**
 * Product is going to have Product Info, Pricing Rules and Discount Rules
 */
public interface Product<I extends ProductInfo, R extends PricingRule, D extends DiscountRule> {

    long getId();

    I getProductInfo();

    List<R> getPricingRules();

    List<D> getDiscountRules();

    boolean isPublished();
}
