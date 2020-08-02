package com.rareshop.api.rest.pricing.engine;

import com.rareshop.api.pricing.AbstractPriceEngine;
import rareshop.api.common.core.discount.DiscountSchema;
import rareshop.api.common.core.pricing.PricingSchema;
import rareshop.api.common.core.product.Product;

public class BasicPricingEngine extends AbstractPriceEngine {
    @Override
    protected PricingSchema searchPricingSchema(Product product) {
        //TODO Connect with database to find price schema
        return null;
    }

    @Override
    protected DiscountSchema searchDiscountSchema(Product product) {
        //TODO Connect with database to find price schema
        return null;
    }
}
