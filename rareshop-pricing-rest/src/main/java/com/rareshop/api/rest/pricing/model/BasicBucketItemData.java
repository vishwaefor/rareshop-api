package com.rareshop.api.rest.pricing.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicBucketItemData that = (BasicBucketItemData) o;
        return productId == that.productId &&
                unitId == that.unitId &&
                purchasedQuantity == that.purchasedQuantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, unitId, purchasedQuantity);
    }

    @Override
    public String toString() {
        return "BasicBucketItemData{" +
                "productId=" + productId +
                ", unitId=" + unitId +
                ", purchasedQuantity=" + purchasedQuantity +
                '}';
    }
}
