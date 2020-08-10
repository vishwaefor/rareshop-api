package com.rareshop.api.rest.pricing.service.impl;

import com.rareshop.api.rest.pricing.engine.BasicPricingEngine;
import com.rareshop.api.rest.pricing.model.*;
import com.rareshop.api.rest.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicPricingService implements PricingService {

    @Autowired
    private BasicPricingEngine pricingEngine;



    @Override
    public PricedBucketData calculatePrice(BasicBucketData bucketData) {

        return makePricedBucket(loadBucket(bucketData));

    }

    protected PricedBucketData makePricedBucket(BasicBucket bucket) {

        PricedBucketData pricedBucket = new PricedBucketData(bucket.getId());

        pricingEngine.applyBucketPrice(bucket);

        pricedBucket.setPricedItems(
                bucket.getItems()
                        .stream()
                        .map(this::makePricedBucketItem)
                        .collect(Collectors.toList())
        );

        return pricedBucket;

    }

    protected PricedBucketItemData makePricedBucketItem(BasicBucketItem bucketItem){

        PricedBucketItemData pricesItemData =  new PricedBucketItemData();

        pricesItemData.setId(bucketItem.getId());
        pricesItemData.setGrossPrice(bucketItem.getGrossPrice());
        pricesItemData.setDiscountAmount(bucketItem.getDiscountedAMount());
        pricesItemData.setNetPrice(bucketItem.getFinalPrice());

        return pricesItemData;

    }

    protected BasicBucket loadBucket(BasicBucketData bucketData) {

        BasicBucket basicBucket = new BasicBucket(bucketData.getBucketId());

        basicBucket.setItems(
                bucketData.getItems()
                .stream()
                .map(this::loadBucketItem)
                .collect(Collectors.toSet())
        );

        return basicBucket;


    }

    protected BasicBucketItem loadBucketItem(BasicBucketItemData bucketItemData){

        BasicUnit unit = loadUnit(bucketItemData.getUnitId());
        BasicProduct product = loadProduct(bucketItemData.getProductId());

        return new BasicBucketItem(product,unit,bucketItemData.getPurchasedQuantity());

    }

    private BasicProduct loadProduct(long productId) {
       BasicProduct product =  new BasicProduct(productId);

       product.setName("product:"+productId);
       product.setUnits(loadUnits(productId));
       product.setPublished(true);
       product.setPricingRules(loadPricingRules(productId));
       product.setDiscountRules(loadDiscountRules(productId));

       return product;
    }



    private BasicUnit loadUnit(long unitId) {
        // TODO load from repository
        return new BasicUnit();
    }

    private List<BasicUnit> loadUnits(long productId) {

        // TODO repository
        return Collections.emptyList();

    }


    protected List<BasicPricingRule> loadPricingRules(long productId){

        // TODO repository
        return Collections.emptyList();

    }

    protected List<BasicDiscountRule> loadDiscountRules(long productId){

        // TODO repository
        return Collections.emptyList();

    }
}
