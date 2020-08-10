package com.rareshop.api.rest.pricing.model;

import java.util.ArrayList;
import java.util.List;

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
}
