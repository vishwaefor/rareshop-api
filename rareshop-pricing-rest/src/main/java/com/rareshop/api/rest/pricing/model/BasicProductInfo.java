package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.product.ProductInfo;

import java.util.Objects;

public class BasicProductInfo implements ProductInfo {

    private long id;
    private String name;
    private String imageURI;
    private String description;
    private boolean published;

    public BasicProductInfo() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageURI() {
        return imageURI;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
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
        return id == that.id &&
                published == that.published &&
                Objects.equals(name, that.name) &&
                Objects.equals(imageURI, that.imageURI) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageURI, description, published);
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
