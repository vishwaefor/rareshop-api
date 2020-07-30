/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core;

import com.rareshop.priceengine.core.bucket.Bucket;
import com.rareshop.priceengine.core.bucket.BucketItem;
import com.rareshop.priceengine.core.discount.DiscountRule;
import com.rareshop.priceengine.core.discount.DiscountSchema;
import com.rareshop.priceengine.core.exception.InvalidArgumentException;
import com.rareshop.priceengine.core.pricing.PriceCalculationEntry;
import com.rareshop.priceengine.core.pricing.PricingRule;
import com.rareshop.priceengine.core.pricing.PricingSchema;
import com.rareshop.priceengine.core.product.Product;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Price Engine is capable of calculating the prices of products
 * based on pricing rules and discount rules
 * Bucket is expected for calculations
 */
public abstract class AbstractPriceEngine<P extends Product,
        I extends BucketItem<P>, B extends Bucket<I>,
        R extends PricingRule, S extends DiscountRule,
        Z extends PricingSchema<P, R>, D extends DiscountSchema<P, S>> {

    private static final int THREAD_COUNT = 4;

    private ExecutorService pricingService;

    protected AbstractPriceEngine(){
        this.pricingService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    protected abstract Z searchPricingSchema(P product);

    protected abstract  D searchDiscountSchema(P product);

    protected  List<R> getApplicablePricingRules(P product) {

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

    protected abstract List<S> getApplicableDiscountRules(P product);

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
                  futures .toArray(new CompletableFuture[futures.size()])
             ).get();
        } catch (InterruptedException e) {
            //TODO Logger
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            //TODO Logger
        }


    }

    protected void setBucketItemPrice(I item) {

        List<R> pricingRules = getApplicablePricingRules(item.getProduct());

        Collections.sort(pricingRules,
                Comparator.comparingInt(PricingRule::getPriorityScore));

//        Collections.reverse(pricingRules);

        Stack<R> entriesStack = new Stack<>();

        pricingRules.stream().forEachOrdered(entriesStack::push);

        int notPricedQuantity = item.getQuantityInPrimaryUnits();

        while(notPricedQuantity>0 && !entriesStack.empty()){

            PricingRule rule = entriesStack.pop();

            if(rule.getQuantityInPrimaryUnit()<=notPricedQuantity){

                int pricedQuantity =  notPricedQuantity - notPricedQuantity%rule.getQuantityInPrimaryUnit();

                item.addPriceCalculationEntry(
                        new PriceCalculationEntry(rule.getId(),rule.getDescription(),rule.getPrimaryUnitPrice(),pricedQuantity)
                );

                notPricedQuantity = notPricedQuantity - pricedQuantity;
            }
        }

        item.markPricingRulesApplied();

    }

}
