/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.api.rest.listing.constant;

public final class APIMapping {

    //ProductInfo
    public static final String PRODUCT_INFO_ID_PARAM = "product-info-id";
    public static final String PRODUCT_INFO_ID_PARAM_B = "{" + PRODUCT_INFO_ID_PARAM + "}";
    public static final String PRODUCT_INFO_PUBLISHED_PARAM = "published";
    //
    public static final String PRODUCT_INFO_MAPPING = "/product-info";
    public static final String SINGLE_PRODUCT_INFO_MAPPING = "/" + PRODUCT_INFO_ID_PARAM_B;


    //Product
    public static final String PRODUCT_ID_PARAM = "product-id";
    public static final String PRODUCT_ID_PARAM_B = "{" + PRODUCT_ID_PARAM + "}";
    public static final String PRODUCT_PUBLISHED_PARAM = "published";
    //
    public static final String PRODUCT_MAPPING = "/products";
    public static final String SINGLE_PRODUCT_MAPPING = "/" + PRODUCT_ID_PARAM_B;


    //PricingRule
    public static final String PRICING_RULE_ID_PARAM = "pricing-rule-id";
    public static final String PRICING_RULE_ID_PARAM_B = "{" + PRICING_RULE_ID_PARAM + "}";
    //
    public static final String PRICING_RULE_MAPPING = "/pricing-rules";
    public static final String SINGLE_PRICING_RULE_MAPPING = "/" + PRICING_RULE_ID_PARAM_B;
    //Product/PricingRule
    public static final String PRICING_RULE_OF_PRODUCT_MAPPING = PRODUCT_MAPPING + SINGLE_PRODUCT_MAPPING + PRICING_RULE_MAPPING;

    private APIMapping() {
        throw new IllegalStateException("Utility class");
    }
}
