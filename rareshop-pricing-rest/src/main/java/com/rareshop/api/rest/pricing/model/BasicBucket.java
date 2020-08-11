package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.bucket.Bucket;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BasicBucket implements Bucket<BasicBucketItem> {

    private long id;
    private Set<BasicBucketItem> items;

    public BasicBucket() {
        this.items = new HashSet<>();

    }

    public BasicBucket(long id) {
        this();
        this.id = id;
    }

    @Override
    public Set<BasicBucketItem> getItems() {
        return items;
    }

    public void setItems(Set<BasicBucketItem> items) {
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicBucket that = (BasicBucket) o;
        return id == that.id &&
                Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items);
    }

    @Override
    public String toString() {
        return "BasicBucket{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}
