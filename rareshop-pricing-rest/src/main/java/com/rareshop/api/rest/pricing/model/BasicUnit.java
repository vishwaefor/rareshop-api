package com.rareshop.api.rest.pricing.model;

import rareshop.api.common.core.unit.Unit;

public class BasicUnit implements Unit {
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getQuantityInPrimaryUnit() {
        return 0;
    }
}
