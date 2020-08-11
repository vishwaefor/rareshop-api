package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.unit.Unit;

import java.util.Objects;

public class BasicUnit implements Unit {

    private long id;
    private String name;
    private int quantityInPrimaryUnit;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getQuantityInPrimaryUnit() {
        return quantityInPrimaryUnit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantityInPrimaryUnit(int quantityInPrimaryUnit) {
        this.quantityInPrimaryUnit = quantityInPrimaryUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicUnit basicUnit = (BasicUnit) o;
        return id == basicUnit.id &&
                quantityInPrimaryUnit == basicUnit.quantityInPrimaryUnit &&
                Objects.equals(name, basicUnit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantityInPrimaryUnit);
    }

    @Override
    public String toString() {
        return "BasicUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantityInPrimaryUnit=" + quantityInPrimaryUnit +
                '}';
    }
}
