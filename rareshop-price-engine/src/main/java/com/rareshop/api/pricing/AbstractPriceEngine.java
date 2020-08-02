/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.api.pricing;


import rareshop.api.common.core.bucket.Bucket;
import rareshop.api.common.core.bucket.BucketItem;
import rareshop.api.common.core.discount.DiscountCalculationEntry;
import rareshop.api.common.core.discount.DiscountRule;
import rareshop.api.common.core.discount.DiscountSchema;
import rareshop.api.common.core.exception.InvalidArgumentException;
import rareshop.api.common.core.pricing.PriceCalculationEntry;
import rareshop.api.common.core.pricing.PricingRule;
import rareshop.api.common.core.pricing.PricingSchema;
import rareshop.api.common.core.product.Product;
import rareshop.api.common.core.unit.Unit;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Price Engine is capable of calculating the prices of products
 * based on pricing rules and discount rules
 * Bucket is expected for calculations
 */
public abstract class AbstractPriceEngine<
        P extends Product,
        U extends Unit,
        I extends BucketItem<P, U>, B extends Bucket<I>,
        R extends PricingRule, S extends DiscountRule,
        Z extends PricingSchema<P, R>, D extends DiscountSchema<P, S>> {

    private static final int THREAD_COUNT = 4;

    private final ExecutorService pricingService;

    protected AbstractPriceEngine() {
        this.pricingService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    protected abstract Z searchPricingSchema(P product);

    protected abstract D searchDiscountSchema(P product);

    protected List<R> getApplicablePricingRules(P product) {

        List<R> pricingRules = (List<R>) Optional.ofNullable(
                Optional.ofNullable(product)
                        .orElseThrow(InvalidArgumentException::new)
                        .getPricingRules()).orElse(Collections.emptyList())
                .stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (pricingRules.isEmpty()) {
            PricingSchema pricingSchema = searchPricingSchema(product);
            if (pricingSchema != null) {
                pricingRules.addAll(
                        Optional.ofNullable(pricingSchema.getApplicableRule(product))
                                .orElse(Collections.emptyList())
                );
            }
        }

        return pricingRules;
    }

    protected List<S> getApplicableDiscountRules(P product) {
        List<S> discountRules = (List<S>) Optional.ofNullable(
                Optional.ofNullable(product)
                        .orElseThrow(InvalidArgumentException::new)
                        .getDiscountRules()).orElse(Collections.emptyList())
                .stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (discountRules.isEmpty()) {
            DiscountSchema discountSchema = searchDiscountSchema(product);
            if (discountSchema != null) {
                discountRules.addAll(
                        Optional.ofNullable(discountSchema.getApplicableRule(product))
                                .orElse(Collections.emptyList())
                );
            }
        }
        return discountRules;
    }

    protected void applyBucketPrice(final B bucket) {

        List<CompletableFuture<Void>> futures = Optional.ofNullable(
                Optional.ofNullable(bucket)
                        .orElseThrow(InvalidArgumentException::new)
                        .getItems()).orElse(Collections.emptySet())
                .stream().filter(Objects::nonNull)
                .map(item -> CompletableFuture
                        .runAsync(() -> setBucketItemPrice(item), pricingService))
                .collect(Collectors.toList());

        try {
            CompletableFuture.allOf(
                    futures.toArray(new CompletableFuture[futures.size()])
            ).get();
        } catch (InterruptedException e) {
            //TODO Logger
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            //TODO Logger
        }


    }

    protected void setBucketItemPrice(I item) {
        applyPricingRules(item);
        applyDiscountRules(item);
    }

    private void applyPricingRules(I item) {

        List<R> pricingRules = getApplicablePricingRules(item.getProduct());

        Collections.sort(pricingRules,
                Comparator.comparingInt(PricingRule::getPriorityScore));

//        Collections.reverse(pricingRules);

        Stack<R> entriesStack = new Stack<>();

        pricingRules.stream().forEachOrdered(entriesStack::push);

        int notPricedQuantity = item.getPurchasedQuantityInPrimaryUnit();

        while (notPricedQuantity > 0 && !entriesStack.empty()) {

            PricingRule rule = entriesStack.pop();

            if (rule.getUnitQuantityInPrimaryUnit() <= notPricedQuantity) {

                int pricedQuantity = notPricedQuantity - notPricedQuantity % rule.getUnitQuantityInPrimaryUnit();

                item.addPriceCalculationEntry(
                        new PriceCalculationEntry(rule.getId(), rule.getDescription(), rule.getPrimaryUnitPrice(), pricedQuantity)
                );

                notPricedQuantity = notPricedQuantity - pricedQuantity;
            }
        }

        item.markPricingRulesApplied();
    }


    private void applyDiscountRules(I item) {

        List<S> discountRules = getApplicableDiscountRules(item.getProduct());

        Collections.sort(discountRules,
                Comparator.comparingInt(DiscountRule::getPriorityScore));

        //        Collections.reverse(discountRules);

        Stack<S> entriesStack = new Stack<>();

        discountRules.stream().forEachOrdered(entriesStack::push);

        boolean discountRuleApplied = false;

        while (!discountRuleApplied && !entriesStack.empty()) {

            DiscountRule rule = entriesStack.pop();

            AtomicReference<Double> subjectedAmount = new AtomicReference<>(0D);

            switch (rule.getAppliedFor()) {

                case PRICE_OF_MAXIMUM_UNIT_COUNT:

                    item.getPriceCalculationEntries()
                            .stream()
                            .filter(e -> e.getQuantityInPrimaryUnit() >= rule.getMinimumRequiredQuantityInPrimaryUnit())
                            .forEach(e -> {
                                subjectedAmount.getAndSet(
                                        e.getQuantityInPrimaryUnit() * e.getPrimaryUnitPrice());
                                item.addDiscountCalculationEntry(
                                        new DiscountCalculationEntry(rule.getId(), rule.getDescription(),
                                                subjectedAmount.get(), rule.getDiscountPercentage()));
                            });

                    break;
                case PRICE_OF_MINIMUM_UNIT_COUNT:

                    item.getPriceCalculationEntries()
                            .stream()
                            .filter(e -> e.getQuantityInPrimaryUnit() >= rule.getMinimumRequiredQuantityInPrimaryUnit())
                            .findFirst()
                            .ifPresent(e -> {
                                subjectedAmount.getAndSet(
                                        rule.getMinimumRequiredQuantityInPrimaryUnit() * e.getPrimaryUnitPrice());
                                item.addDiscountCalculationEntry(
                                        new DiscountCalculationEntry(rule.getId(), rule.getDescription(),
                                                subjectedAmount.get(), rule.getDiscountPercentage()));
                            });

                    break;


                default:
                    if (item.getPurchasedQuantityInPrimaryUnit() >= rule.getMinimumRequiredQuantityInPrimaryUnit()) {
                        subjectedAmount.set(item.getGrossPrice());

                        item.addDiscountCalculationEntry(
                                new DiscountCalculationEntry(rule.getId(), rule.getDescription(),
                                        subjectedAmount.get(), rule.getDiscountPercentage()));

                    }

            }
            discountRuleApplied = !item.getDiscountCalculationEntries().isEmpty();

        }

        item.markDiscountRulesApplied();
    }
}
