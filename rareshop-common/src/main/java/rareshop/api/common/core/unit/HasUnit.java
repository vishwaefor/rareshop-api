package rareshop.api.common.core.unit;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasUnit {

    Unit getUnit();

    @JsonIgnore
    default int getUnitQuantityInPrimaryUnit() {

        if (getUnit() == null || getUnit().getQuantityInPrimaryUnit() == 0) {
            return 1;
        } else {
            return getUnit().getQuantityInPrimaryUnit();
        }

    }

    @JsonIgnore
    default String getUnitName() {
        if (getUnit() == null || getUnit().getName() == null || getUnit().getName().isBlank()) {
            return "Primary";
        } else {
            return getUnit().getName();
        }
    }
}
