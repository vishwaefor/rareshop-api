/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.api.rest.listing.model;

import rareshop.api.common.core.product.ProductInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class BasicProductInfo implements ProductInfo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String name;
    private String imageURI;
    private String description;
    private boolean published;

    public BasicProductInfo() {
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }


    public String getImageURI() {
        return this.imageURI;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicProductInfo that = (BasicProductInfo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BasicProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageURI='" + imageURI + '\'' +
                ", description='" + description + '\'' +
                ", published=" + published +
                '}';
    }
}
