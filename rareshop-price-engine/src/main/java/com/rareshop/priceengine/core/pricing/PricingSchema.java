/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core.pricing;

import com.rareshop.priceengine.core.product.Product;

import java.util.List;

public interface PricingSchema<P extends Product, R extends PricingRule> {

    List<R> getApplicableRule(P product);

}
