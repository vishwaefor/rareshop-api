package com.rareshop.api.rest.listing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicProductData {

    private long productInfoId;
    private String name;
    private List<BasicUnitData> extraUnits;

    public BasicProductData() {
        this.extraUnits = new ArrayList<>();
    }

    public long getProductInfoId() {
        return productInfoId;
    }

    public void setProductInfoId(long productInfoId) {
        this.productInfoId = productInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BasicUnitData> getExtraUnits() {
        return extraUnits;
    }

    public void setExtraUnits(List<BasicUnitData> extraUnits) {
        this.extraUnits = extraUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicProductData that = (BasicProductData) o;
        return productInfoId == that.productInfoId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productInfoId, name);
    }

    @Override
    public String toString() {
        return "BasicProductData{" +
                "productInfoId=" + productInfoId +
                ", name='" + name + '\'' +
                ", extraUnits=" + extraUnits +
                '}';
    }
}
