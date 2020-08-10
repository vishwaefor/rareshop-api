package com.rareshop.api.rest.pricing.model;

import java.util.ArrayList;
import java.util.List;

public class BasicBucketData {

    private long bucketId;

    private List<BasicBucketItemData> items;

    public BasicBucketData() {
        this.items = new ArrayList<>();
    }

    public BasicBucketData(long id) {
        this();
        this.bucketId = id;
    }

    public List<BasicBucketItemData> getItems() {
        return items;
    }

    public void setItems(List<BasicBucketItemData> items) {
        this.items = items;
    }

    public void addItem(BasicBucketItemData item) {
        this.items.add(item);
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }
}
