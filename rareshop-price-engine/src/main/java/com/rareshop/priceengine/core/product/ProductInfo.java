/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core.product;

/**
 * ProductInfo can describe a product item.
 * Information of the product like name, brand, thumbnail, other properties, etc.
 * There can be more products associated with same info.
 * hence various pricing rules and discount rules can be applied.
 * Prices or discounts are not included here
 */
public interface ProductInfo {

    String getId();

    String getName();

    String getBrand();

    String getImageURI();
}
