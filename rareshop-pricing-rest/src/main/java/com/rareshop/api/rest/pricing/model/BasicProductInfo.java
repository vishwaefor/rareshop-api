package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.product.ProductInfo;

public class BasicProductInfo implements ProductInfo {
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getImageURI() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isPublished() {
        return false;
    }
}
