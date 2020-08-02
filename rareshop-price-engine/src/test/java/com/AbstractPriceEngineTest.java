package com;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import rareshop.api.common.core.bucket.Bucket;
import rareshop.api.common.core.bucket.BucketItem;
import rareshop.api.common.core.discount.AppliedFor;
import rareshop.api.common.core.discount.DiscountCalculationEntry;
import rareshop.api.common.core.discount.DiscountRule;
import rareshop.api.common.core.pricing.PriceCalculationEntry;
import rareshop.api.common.core.pricing.PricingRule;
import rareshop.api.common.core.product.Product;
import rareshop.api.common.core.unit.Unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class AbstractPriceEngineTest {

    @Spy
    private AbstractPriceEngine abstractPriceEngine;

    @Spy
    private Bucket bucket;

    @Spy
    private BucketItem bucketItemOne;

    @Spy
    private BucketItem bucketItemTwo;

    @Spy
    private Product productOne;

    @Spy
    private Product productTwo;

    @Spy
    private Unit singleUnitOne;

    @Spy
    private Unit cartonUnitOne;

    @Spy
    private Unit singleUnitTwo;

    @Spy
    private Unit cartonUnitTwo;

    @Spy
    private PricingRule singlePricingRuleOne;

    @Spy
    private PricingRule cartonPricingRuleOne;

    @Spy
    private PricingRule singlePricingRuleTwo;

    @Spy
    private PricingRule cartonPricingRuleTwo;

    @Spy
    private DiscountRule singleDiscountRuleOne;

    @Spy
    private DiscountRule cartonDiscountRuleOne;

    @Spy
    private DiscountRule singleDiscountRuleTwo;

    @Spy
    private DiscountRule cartonDiscountRuleTwo;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

    }


    private void setupUnits() {

        List<Unit> unitsOne = new ArrayList<>();

        when(singleUnitOne.getId()).thenReturn(1L);
        when(singleUnitOne.getName()).thenReturn("Single");
        when(singleUnitOne.getQuantityInPrimaryUnit()).thenReturn(1);

        when(cartonUnitOne.getId()).thenReturn(2L);
        when(cartonUnitOne.getName()).thenReturn("Carton");
        when(cartonUnitOne.getQuantityInPrimaryUnit()).thenReturn(100);

        unitsOne.add(singleUnitOne);
        unitsOne.add(cartonUnitOne);

        List<Unit> unitsTwo = new ArrayList<>();

        when(singleUnitTwo.getId()).thenReturn(10L);
        when(singleUnitTwo.getName()).thenReturn("Single");
        when(singleUnitTwo.getQuantityInPrimaryUnit()).thenReturn(1);

        when(cartonUnitTwo.getId()).thenReturn(20L);
        when(cartonUnitTwo.getName()).thenReturn("Carton");
        when(cartonUnitTwo.getQuantityInPrimaryUnit()).thenReturn(200);

        unitsTwo.add(singleUnitTwo);
        unitsTwo.add(cartonUnitTwo);

        when(productOne.getUnits()).thenReturn(unitsOne);
        when(productTwo.getUnits()).thenReturn(unitsTwo);
    }

    private void setupPricingRules() {

        List<PricingRule> pricingRulesOne = new ArrayList<>();

        when(singlePricingRuleOne.getUnit()).thenReturn(singleUnitOne);
        when(singlePricingRuleOne.getPriorityScore()).thenReturn(100);
        when(singlePricingRuleOne.getUnitPrice()).thenReturn(10D);
        when(singlePricingRuleOne.isAdditionalPricingFactorUsed()).thenReturn(true);
        when(singlePricingRuleOne.getAdditionalPricingFactor()).thenReturn(1.5D);

        when(cartonPricingRuleOne.getUnit()).thenReturn(cartonUnitOne);
        when(cartonPricingRuleOne.getPriorityScore()).thenReturn(200);
        when(cartonPricingRuleOne.getUnitPrice()).thenReturn(1000D);
        when(cartonPricingRuleOne.isAdditionalPricingFactorUsed()).thenReturn(false);

        pricingRulesOne.add(singlePricingRuleOne);
        pricingRulesOne.add(cartonPricingRuleOne);

        List<PricingRule> pricingRulesTwo = new ArrayList<>();

        when(singlePricingRuleTwo.getUnit()).thenReturn(singleUnitTwo);
        when(singlePricingRuleTwo.getPriorityScore()).thenReturn(100);
        when(singlePricingRuleTwo.getUnitPrice()).thenReturn(20D);
        when(singlePricingRuleTwo.isAdditionalPricingFactorUsed()).thenReturn(true);
        when(singlePricingRuleTwo.getAdditionalPricingFactor()).thenReturn(2.5D);

        when(cartonPricingRuleTwo.getUnit()).thenReturn(cartonUnitTwo);
        when(cartonPricingRuleTwo.getPriorityScore()).thenReturn(200);
        when(cartonPricingRuleTwo.getUnitPrice()).thenReturn(4000D);
        when(cartonPricingRuleTwo.isAdditionalPricingFactorUsed()).thenReturn(false);

        pricingRulesTwo.add(singlePricingRuleTwo);
        pricingRulesTwo.add(cartonPricingRuleTwo);

        when(productOne.getPricingRules()).thenReturn(pricingRulesOne);
        when(productTwo.getPricingRules()).thenReturn(pricingRulesTwo);
    }

    private void setupDiscountRules() {

        List<DiscountRule> discountRulesOne = new ArrayList<>();

        when(singleDiscountRuleOne.getUnit()).thenReturn(singleUnitOne);
        when(singleDiscountRuleOne.getAppliedFor()).thenReturn(AppliedFor.TOTAL_GROSS_PRICE);
        when(singleDiscountRuleOne.getMinimumRequiredQuantityInUnit()).thenReturn(3);
        when(singleDiscountRuleOne.getDiscountPercentage()).thenReturn(10D);
        when(singleDiscountRuleOne.getPriorityScore()).thenReturn(100);

        when(cartonDiscountRuleOne.getUnit()).thenReturn(cartonUnitOne);
        when(cartonDiscountRuleOne.getAppliedFor()).thenReturn(AppliedFor.PRICE_OF_MAXIMUM_UNIT_COUNT);
        when(cartonDiscountRuleOne.getMinimumRequiredQuantityInUnit()).thenReturn(2);
        when(cartonDiscountRuleOne.getDiscountPercentage()).thenReturn(20D);
        when(cartonDiscountRuleOne.getPriorityScore()).thenReturn(200);

        discountRulesOne.add(singleDiscountRuleOne);
        discountRulesOne.add(cartonDiscountRuleOne);

        List<DiscountRule> discountRulesTwo = new ArrayList<>();

        when(singleDiscountRuleTwo.getUnit()).thenReturn(singleUnitTwo);
        when(singleDiscountRuleTwo.getAppliedFor()).thenReturn(AppliedFor.TOTAL_GROSS_PRICE);
        when(singleDiscountRuleTwo.getMinimumRequiredQuantityInUnit()).thenReturn(3);
        when(singleDiscountRuleTwo.getDiscountPercentage()).thenReturn(10D);
        when(singleDiscountRuleTwo.getPriorityScore()).thenReturn(100);

        when(cartonDiscountRuleTwo.getUnit()).thenReturn(cartonUnitTwo);
        when(cartonDiscountRuleTwo.getAppliedFor()).thenReturn(AppliedFor.PRICE_OF_MINIMUM_UNIT_COUNT);
        when(cartonDiscountRuleTwo.getMinimumRequiredQuantityInUnit()).thenReturn(2);
        when(cartonDiscountRuleTwo.getDiscountPercentage()).thenReturn(20D);
        when(cartonDiscountRuleTwo.getPriorityScore()).thenReturn(200);

        discountRulesTwo.add(singleDiscountRuleTwo);
        discountRulesTwo.add(cartonDiscountRuleTwo);

        when(productOne.getDiscountRules()).thenReturn(discountRulesOne);
        when(productTwo.getDiscountRules()).thenReturn(discountRulesTwo);

    }

    private void setupBucketItems(Unit unitOne, int quantityOne, Unit unitTwo, int quantityTwo) {

        Set<BucketItem> bucketItemList = new HashSet<>();

        when(bucketItemOne.getProduct()).thenReturn(productOne);
        when(bucketItemOne.getUnit()).thenReturn(unitOne);
        when(bucketItemOne.getPurchasedQuantityInUnit()).thenReturn(quantityOne);

        List<PriceCalculationEntry> priceCalculationEntriesOne = new ArrayList<>();
        doAnswer(args -> {
            priceCalculationEntriesOne.add((PriceCalculationEntry) args.getArguments()[0]);
            return null;
        }).when(bucketItemOne).addPriceCalculationEntry(any(PriceCalculationEntry.class));
        when(bucketItemOne.getPriceCalculationEntries()).thenReturn(priceCalculationEntriesOne);

        List<DiscountCalculationEntry> discountCalculationEntriesOne = new ArrayList<>();
        doAnswer(args -> {
            discountCalculationEntriesOne.add((DiscountCalculationEntry) args.getArguments()[0]);
            return null;
        }).when(bucketItemOne).addDiscountCalculationEntry(any(DiscountCalculationEntry.class));

        when(bucketItemOne.getDiscountCalculationEntries()).thenReturn(discountCalculationEntriesOne);
        when(bucketItemOne.isFinalPriceCalculated()).thenReturn(true);


        when(bucketItemTwo.getProduct()).thenReturn(productTwo);
        when(bucketItemTwo.getUnit()).thenReturn(unitTwo);
        when(bucketItemTwo.getPurchasedQuantityInUnit()).thenReturn(quantityTwo);

        List<PriceCalculationEntry> priceCalculationEntriesTwo = new ArrayList<>();
        doAnswer(args -> {
            priceCalculationEntriesTwo.add((PriceCalculationEntry) args.getArguments()[0]);
            return null;
        }).when(bucketItemTwo).addPriceCalculationEntry(any(PriceCalculationEntry.class));
        when(bucketItemTwo.getPriceCalculationEntries()).thenReturn(priceCalculationEntriesTwo);

        List<DiscountCalculationEntry> discountCalculationEntriesTwo = new ArrayList<>();
        doAnswer(args -> {
            discountCalculationEntriesTwo.add((DiscountCalculationEntry) args.getArguments()[0]);
            return null;
        }).when(bucketItemTwo).addDiscountCalculationEntry(any(DiscountCalculationEntry.class));

        when(bucketItemTwo.getDiscountCalculationEntries()).thenReturn(discountCalculationEntriesTwo);
        when(bucketItemTwo.isFinalPriceCalculated()).thenReturn(true);


        bucketItemList.add(bucketItemOne);
        bucketItemList.add(bucketItemTwo);

        when(bucket.getItems()).thenReturn(bucketItemList);

    }

    private void setupBucket(Unit unitOne, int quantityOne, Unit unitTwo, int quantityTwo) {

        setupUnits();

        setupPricingRules();

        setupDiscountRules();

        setupBucketItems(unitOne, quantityOne, unitTwo, quantityTwo);
    }

    private void testForBucketPrice(
            double expected,Unit unitOne, int quantityOne, Unit unitTwo, int quantityTwo,boolean explained){

        setupBucket(unitOne, quantityOne, unitTwo, quantityTwo);

        abstractPriceEngine.applyBucketPrice(bucket);

        double calculatedPrice = bucket.getFinalPrice();

        if(explained){
            Logger.getAnonymousLogger().log(Level.INFO, "\n\n### Final Calculated Price : {0}\n",calculatedPrice );

            bucket.getItems()
                    .stream().forEach(i -> {
                Logger.getAnonymousLogger().log(Level.INFO, ((BucketItem) i).explain());
            });
        }

        Assertions.assertEquals(expected, calculatedPrice);
    }

    @Test
    @DisplayName("Testing bucket price for various scenarios")
    void applyBucketPrice() {


        Assertions.assertAll(
                () -> {
                    testForBucketPrice(11210D,singleUnitOne, 60, cartonUnitTwo, 3,true);
                }
                , () -> {
                    testForBucketPrice(15300D,cartonUnitOne, 3, singleUnitTwo, 650,true);
                }
                , () -> {
                    testForBucketPrice(8550,singleUnitOne, 350, singleUnitTwo, 120,true);
                }
                , () -> {
                    testForBucketPrice(23200D,cartonUnitOne, 6, cartonUnitTwo, 5,true);
                }
        );

    }


}