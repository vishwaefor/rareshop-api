/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.product;

/**
 * ProductInfo can describe a product item.
 * Information of the product like name, thumbnail, other properties, etc.
 * There can be more products associated with same info.
 * hence various pricing rules and discount rules can be applied.
 * Prices or discounts are not included here
 * Used for listing
 */
public interface ProductInfo {

    long getId();

    String getName();

    String getImageURI();

    String getDescription();

    boolean isPublished();
}
