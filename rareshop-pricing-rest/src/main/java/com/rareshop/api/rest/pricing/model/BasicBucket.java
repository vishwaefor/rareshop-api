package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.bucket.Bucket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
}
