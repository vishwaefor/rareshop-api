package rareshop.api.common.core.discount;

import java.io.Serializable;


public enum AppliedFor implements Serializable {

    TOTAL_GROSS_PRICE("total gross price"),
    PRICE_OF_MINIMUM_UNIT_COUNT("price of minimum units"),
    PRICE_OF_MAXIMUM_UNIT_COUNT("price of maximum units");

    private String message;

    AppliedFor(String message) {

        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return name();
    }
}
