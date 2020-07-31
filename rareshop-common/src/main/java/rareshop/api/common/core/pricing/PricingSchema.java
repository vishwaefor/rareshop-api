/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.pricing;

import rareshop.api.common.core.product.Product;

import java.util.List;

/**
 * Pricing Schema is defined globally and contains pricing rules
 * If a product is not containing any pricing rule, schema is used
 * For the time being, this is not implemented
 */

public interface PricingSchema<P extends Product, R extends PricingRule> {

    List<R> getApplicableRule(P product);

}
