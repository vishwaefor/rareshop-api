/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core.pricing;

import com.rareshop.priceengine.core.exception.InvalidArgumentException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class PricingRuleTest {

    private static final String UNIT = "Carton";
    private static final double UNIT_PRICE = 100D;
    private static final int QUANTITY_IN_PRIMARY_UNITS = 10;
    private static final double PRIMARY_UNIT_PRICE = 10D;
    private static final double ADDITIONAL_FACTOR = 1.5D;
    private static final double ADDITIONAL_FACTOR_LESS_THAN_ONE = 0.5D;
    private static final double PRIMARY_UNIT_PRICE_FACTOR = PRIMARY_UNIT_PRICE * ADDITIONAL_FACTOR;

    @Spy
    private PricingRule pricingRule;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(pricingRule.getUnit()).thenReturn(UNIT);
    }

    @Nested
    @DisplayName("Primary Unit Price without Additional Factor")
    class PrimaryUnitPriceWithoutAdditionalFactor {


        @Test
        @DisplayName("When unit price and quantity in primary unit is negative, InvalidArgument Exception should_be_thrown")
        void when_unit_price_or_quantity_in_primary_unit_is_negative_InvalidArgumentException_should_be_thrown() {

            Assertions.assertAll(

                    () -> {
                        Mockito.when(pricingRule.getUnitPrice()).thenReturn(-UNIT_PRICE);
                        Mockito.when(pricingRule.getQuantityInPrimaryUnit()).thenReturn(QUANTITY_IN_PRIMARY_UNITS);

                        Assertions.assertThrows(InvalidArgumentException.class, () -> pricingRule.getPrimaryUnitPrice());
                    },
                    () -> {
                        Mockito.when(pricingRule.getUnitPrice()).thenReturn(UNIT_PRICE);
                        Mockito.when(pricingRule.getQuantityInPrimaryUnit()).thenReturn(-QUANTITY_IN_PRIMARY_UNITS);

                        Assertions.assertThrows(InvalidArgumentException.class, () -> pricingRule.getPrimaryUnitPrice());
                    },
                    () -> {
                        Mockito.when(pricingRule.getUnitPrice()).thenReturn(-UNIT_PRICE);
                        Mockito.when(pricingRule.getQuantityInPrimaryUnit()).thenReturn(-QUANTITY_IN_PRIMARY_UNITS);

                        Assertions.assertThrows(InvalidArgumentException.class, () -> pricingRule.getPrimaryUnitPrice());
                    }
            );
        }

        @Test
        @DisplayName("When quantity in primary unit is zero, primary unt price should be same as unit price")
        void when_quantity_in_primary_unit_is_zero_primary_unit_price_should_be_same_as_unit_price() {

            Mockito.when(pricingRule.getUnitPrice()).thenReturn(UNIT_PRICE);
            Mockito.when(pricingRule.getQuantityInPrimaryUnit()).thenReturn(0);

            Assertions.assertEquals(UNIT_PRICE, pricingRule.getPrimaryUnitPrice());

        }

        @Test
        @DisplayName("When unit price and quantity in primary unit is set, primary unit price should be calculated")
        void when_unit_price_and_quantity_in_primary_unit_is_set_primary_unit_price_should_be_calculated() {

            Mockito.when(pricingRule.getUnitPrice()).thenReturn(UNIT_PRICE);
            Mockito.when(pricingRule.getQuantityInPrimaryUnit()).thenReturn(QUANTITY_IN_PRIMARY_UNITS);

            Assertions.assertEquals(PRIMARY_UNIT_PRICE, pricingRule.getPrimaryUnitPrice());
        }
    }

    @Nested
    @DisplayName("Primary Unit Price with Additional Factor")
    class PrimaryUnitPriceWithAdditionalFactor {

        @Test
        @DisplayName("When unit price and quantity in primary unit and additional factor is set, primary unit price should be calculated")
        void when_unit_price_and_quantity_in_primary_unit_and_additional_factor_is_set_primary_unit_price_should_be_calculated() {

            Mockito.when(pricingRule.getQuantityInPrimaryUnit()).thenReturn(QUANTITY_IN_PRIMARY_UNITS);
            Mockito.when(pricingRule.getUnitPrice()).thenReturn(UNIT_PRICE);
            Mockito.when(pricingRule.getAdditionalPricingFactor()).thenReturn(ADDITIONAL_FACTOR);

            Assertions.assertAll(() -> {

                        Mockito.when(pricingRule.isAdditionalPricingFactorUsed()).thenReturn(true);

                        Assertions.assertEquals(PRIMARY_UNIT_PRICE_FACTOR, pricingRule.getPrimaryUnitPrice());
                    },
                    () -> {
                        Mockito.when(pricingRule.isAdditionalPricingFactorUsed()).thenReturn(false);

                        Assertions.assertEquals(PRIMARY_UNIT_PRICE, pricingRule.getPrimaryUnitPrice());
                    });
        }


        @Test
        @DisplayName("When additional factor is negative or less than one ,InvalidArgumentException should be thrown")
        void when_additional_factor_is_negative_or_less_than_one_InvalidArgumentException_should_be_thrown() {

            Mockito.when(pricingRule.getQuantityInPrimaryUnit()).thenReturn(QUANTITY_IN_PRIMARY_UNITS);
            Mockito.when(pricingRule.getUnitPrice()).thenReturn(UNIT_PRICE);
            Mockito.when(pricingRule.isAdditionalPricingFactorUsed()).thenReturn(true);

            Assertions.assertAll(() -> {

                        Mockito.when(pricingRule.getAdditionalPricingFactor()).thenReturn(-ADDITIONAL_FACTOR);
                        Assertions.assertThrows(InvalidArgumentException.class, () -> pricingRule.getPrimaryUnitPrice());
                    },
                    () -> {
                        Mockito.when(pricingRule.getAdditionalPricingFactor()).thenReturn(ADDITIONAL_FACTOR_LESS_THAN_ONE);
                        Assertions.assertThrows(InvalidArgumentException.class, () -> pricingRule.getPrimaryUnitPrice());
                    });
        }
    }


}