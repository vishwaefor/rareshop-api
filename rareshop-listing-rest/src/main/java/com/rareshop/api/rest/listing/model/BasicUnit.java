package com.rareshop.api.rest.listing.model;

import rareshop.api.common.core.unit.Unit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class BasicUnit implements Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int quantityInPrimaryUnits;

    public BasicUnit() {
    }

    public BasicUnit(String name, int quantityInPrimaryUnit) {
        this.name = name;
        this.quantityInPrimaryUnits = quantityInPrimaryUnit;
    }

    public BasicUnit(long unitId) {
        this();
        this.id = unitId;
    }

    public BasicUnit(long unitId, String name, int quantityInPrimaryUnit) {
        this(name, quantityInPrimaryUnit);
        this.id = unitId;
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
    public int getQuantityInPrimaryUnit() {
        return quantityInPrimaryUnits;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantityInPrimaryUnits(int quantityInPrimaryUnits) {
        this.quantityInPrimaryUnits = quantityInPrimaryUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicUnit basicUnit = (BasicUnit) o;
        return id == basicUnit.id &&
                quantityInPrimaryUnits == basicUnit.quantityInPrimaryUnits &&
                Objects.equals(name, basicUnit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantityInPrimaryUnits);
    }

    @Override
    public String toString() {
        return "BasicUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantityInPrimaryUnits=" + quantityInPrimaryUnits +
                '}';
    }
}
