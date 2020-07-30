/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.impl;

import com.rareshop.priceengine.core.AbstractPriceEngine;
import com.rareshop.priceengine.core.discount.DiscountSchema;
import com.rareshop.priceengine.core.pricing.PricingSchema;
import com.rareshop.priceengine.core.product.Product;

import java.util.List;

public class BasicPriceEngine extends AbstractPriceEngine {
    @Override
    protected PricingSchema searchPricingSchema(Product product) {
        return null;
    }

    @Override
    protected DiscountSchema searchDiscountSchema(Product product) {
        return null;
    }

    @Override
    protected List getApplicableDiscountRules(Product product) {
        return null;
    }
}
