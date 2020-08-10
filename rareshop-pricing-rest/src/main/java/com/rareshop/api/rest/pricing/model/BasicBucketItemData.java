package com.rareshop.api.rest.pricing.model;

public class BasicBucketItemData {
    private long productId;
    private long unitId;
    private int purchasedQuantity;

    public BasicBucketItemData() {
    }

    public BasicBucketItemData(long productId, long unitId, int purchasedQuantity) {
        this.productId = productId;
        this.unitId = unitId;
        this.purchasedQuantity = purchasedQuantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }
}
