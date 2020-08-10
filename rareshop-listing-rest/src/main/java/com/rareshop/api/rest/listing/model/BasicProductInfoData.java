package com.rareshop.api.rest.listing.model;

import java.util.Objects;

public class BasicProductInfoData {

    private String name;
    private String description;
    private String imageURI;

    public BasicProductInfoData() {
        // Do nothing
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicProductInfoData that = (BasicProductInfoData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(imageURI, that.imageURI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, imageURI);
    }

    @Override
    public String toString() {
        return "BasicProductInfoData{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURI='" + imageURI + '\'' +
                '}';
    }
}
