/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.discount;

import rareshop.api.common.core.product.Product;

import java.util.List;

/**
 * Discount Schema is defined globally and contains discount rules
 * If a product is not containing any discount rule, schema is used
 * For the time being, this is not implemented
 */

public interface DiscountSchema<P extends Product, S extends DiscountRule> {

    List<S> getApplicableRule(P product);
}
