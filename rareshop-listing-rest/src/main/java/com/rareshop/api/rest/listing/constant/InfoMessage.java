package com.rareshop.api.rest.listing.constant;

public final class InfoMessage {

    public static final String PRODUCT_INFO_CREATED = "product info created";
    public static final String PRODUCT_INFO_UPDATED = "product info updated";
    public static final String PRODUCT_INFO_DELETED = "product info deleted";
    public static final String PRODUCT_INFO_PUBLISHED = "product info published";
    public static final String PRODUCT_INFO_UNPUBLISHED = "product info unpublished";

    public static final String PRODUCT_CREATED = "product created";
    public static final String PRODUCT_UPDATED = "product updated";
    public static final String PRODUCT_DELETED = "product deleted";
    public static final String PRODUCT_PUBLISHED = "product published";
    public static final String PRODUCT_UNPUBLISHED = "product unpublished";

    public static final String PRICING_RULE_ADDED = "pricing rule added";
    public static final String PRICING_RULE_UPDATED = "pricing rule updated";
    public static final String PRICING_RULE_DELETED = "pricing rule deleted";

    private InfoMessage() {
        throw new IllegalStateException("Utility class");
    }
}
