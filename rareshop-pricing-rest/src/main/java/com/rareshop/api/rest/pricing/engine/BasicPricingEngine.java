package com.rareshop.api.rest.pricing.engine;

import com.rareshop.api.pricing.AbstractPriceEngine;
import com.rareshop.api.rest.pricing.model.*;
import org.springframework.stereotype.Component;

@Component
public class BasicPricingEngine
        extends AbstractPriceEngine<
        BasicProduct,
        BasicUnit,
        BasicBucketItem,
        BasicBucket,
        BasicPricingRule,
        BasicDiscountRule,
        BasicPricingSchema,
        BasicDiscountSchema> {

    @Override
    protected BasicPricingSchema searchPricingSchema(BasicProduct product) {
        return null;
    }

    @Override
    protected BasicDiscountSchema searchDiscountSchema(BasicProduct product) {
        return null;
    }
}
