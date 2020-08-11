package com.rareshop.api.rest.pricing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PricedBucketData {

    private  long bucketId;

    private List<PricedBucketItemData> pricedItems;

    public PricedBucketData() {

        this.pricedItems = new ArrayList<>();
    }

    public PricedBucketData(long id) {

        this();
        this.bucketId = id;
    }

    public List<PricedBucketItemData> getPricedItems() {
        return pricedItems;
    }

    public void setPricedItems(List<PricedBucketItemData> pricedItems) {
        this.pricedItems = pricedItems;
    }

    public void addItem(PricedBucketItemData item) {
        this.pricedItems.add(item);
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricedBucketData that = (PricedBucketData) o;
        return bucketId == that.bucketId &&
                Objects.equals(pricedItems, that.pricedItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucketId, pricedItems);
    }

    @Override
    public String toString() {
        return "PricedBucketData{" +
                "bucketId=" + bucketId +
                ", pricedItems=" + pricedItems +
                '}';
    }
}
