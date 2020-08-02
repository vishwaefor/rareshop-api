package com.rareshop.api.rest.listing.model;

import java.util.Objects;

public class BasicUnitData {

    private String name;
    private int quantityInPrimaryUnits;

    public BasicUnitData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityInPrimaryUnits() {
        return quantityInPrimaryUnits;
    }

    public void setQuantityInPrimaryUnits(int quantityInPrimaryUnits) {
        this.quantityInPrimaryUnits = quantityInPrimaryUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicUnitData that = (BasicUnitData) o;
        return quantityInPrimaryUnits == that.quantityInPrimaryUnits &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantityInPrimaryUnits);
    }

    @Override
    public String toString() {
        return "BasicUnitData{" +
                "name='" + name + '\'' +
                ", quantityInPrimaryUnits=" + quantityInPrimaryUnits +
                '}';
    }
}
