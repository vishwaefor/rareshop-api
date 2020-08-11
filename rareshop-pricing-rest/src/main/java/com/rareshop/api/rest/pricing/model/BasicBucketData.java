package com.rareshop.api.rest.pricing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicBucketData that = (BasicBucketData) o;
        return bucketId == that.bucketId &&
                Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucketId, items);
    }

    @Override
    public String toString() {
        return "BasicBucketData{" +
                "bucketId=" + bucketId +
                ", items=" + items +
                '}';
    }
}
