/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core;

import com.rareshop.priceengine.core.bucket.Bucket;
import com.rareshop.priceengine.core.bucket.BucketItem;
import com.rareshop.priceengine.core.pricing.PriceCalculationEntry;
import com.rareshop.priceengine.core.pricing.PricingRule;
import com.rareshop.priceengine.core.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbstractPriceEngineTest {

    @Spy
    private AbstractPriceEngine abstractPriceEngine;

    @Spy
    private Bucket bucket;

    @Spy
    private BucketItem bucketItem;

    @Mock
    private Product product;

    private final Set<BucketItem> items = new HashSet<>();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(bucketItem.getProduct()).thenReturn(product);

        List<PriceCalculationEntry> entries = new ArrayList<>();
        Mockito.doAnswer(i -> {
                    PriceCalculationEntry entry = i.getArgument(0);
                    entries.add(entry);
                    return null;
                }
        ).when(bucketItem).addPriceCalculationEntry(Mockito.any(PriceCalculationEntry.class));
        Mockito.when(bucketItem.getPriceCalculationEntries()).thenReturn(entries);

        Mockito.when(bucket.getItems()).thenReturn(items);
    }

    @Test
    void applyBucketPrice() {

        Mockito.when(bucketItem.getQuantityInPrimaryUnits()).thenReturn(210);

        PricingRule pricingRuleCarton = Mockito.spy(PricingRule.class);
        Mockito.when(pricingRuleCarton.getUnit()).thenReturn("Carton");
        Mockito.when(pricingRuleCarton.getUnitPrice()).thenReturn(100D);
        Mockito.when(pricingRuleCarton.getQuantityInPrimaryUnit()).thenReturn(100);
        Mockito.when(pricingRuleCarton.getPriorityScore()).thenReturn(200);

        PricingRule pricingRuleSingle = Mockito.spy(PricingRule.class);
        Mockito.when(pricingRuleSingle.getUnit()).thenReturn("Single");
        Mockito.when(pricingRuleSingle.getUnitPrice()).thenReturn(1D);
        Mockito.when(pricingRuleSingle.getQuantityInPrimaryUnit()).thenReturn(1);
        Mockito.when(pricingRuleSingle.isAdditionalPricingFactorUsed()).thenReturn(true);
        Mockito.when(pricingRuleSingle.getAdditionalPricingFactor()).thenReturn(1.5);
        Mockito.when(pricingRuleSingle.getPriorityScore()).thenReturn(100);

        List<PricingRule> pricingRules = new ArrayList<>();
        pricingRules.add(pricingRuleSingle);
        pricingRules.add(pricingRuleCarton);


        Mockito.when(product.getPricingRules()).thenReturn(pricingRules);

        items.add(bucketItem);
        Mockito.when(bucketItem.isFinalPriceCalculated()).thenReturn(true);

        abstractPriceEngine.applyBucketPrice(bucket);

        Assertions.assertEquals(215D, bucket.getFinalPrice());


    }
}